package com.ulca.benchmark.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ulca.benchmark.model.ModelInferenceResponse;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelInferenceResponseDao;

import io.swagger.model.Benchmark;
import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.model.Sentence;
import io.swagger.model.SentenceList;
import io.swagger.model.Sentences;
import io.swagger.model.SentencesList;
import io.swagger.model.TransliterationRequest;
import io.swagger.model.TransliterationResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Service
public class TransliterationBenchmark {

	private final int chunkSize = 5;
	
	@Autowired
	private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;
	
	@Value("${kafka.ulca.bm.metric.ip.topic}")
	private String mbMetricTopic;
	
	@Autowired
	WebClient.Builder builder;
	
	@Autowired
	ModelInferenceResponseDao modelInferenceResponseDao;
	

	public TransliterationResponse computeSync(InferenceAPIEndPoint inferenceAPIEndPoint,
			List<String> sourceSentences)
			throws URISyntaxException, IOException {
		TransliterationResponse transliteration = null;
		
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();

		if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TransliterationInference")) {
			io.swagger.model.TransliterationInference transliterationInference = (io.swagger.model.TransliterationInference) schema;
			TransliterationRequest request = transliterationInference.getRequest();

			request.getConfig().setNumSuggestions(1);//numSuggestions set to 1
			
			Sentences sentences = new Sentences();
			for (String ip : sourceSentences) {
				Sentence sentense = new Sentence();
				sentense.setSource(ip);
				sentences.add(sentense);
			}
			request.setInput(sentences);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String requestJson = objectMapper.writeValueAsString(request);
			
			
			//OkHttpClient client = new OkHttpClient();
			
			OkHttpClient client = new OkHttpClient.Builder()
				      .readTimeout(60, TimeUnit.SECONDS)
				      .build();
			 
			RequestBody body = RequestBody.create(requestJson,MediaType.parse("application/json"));
			Request httpRequest = new Request.Builder()
			        .url(callBackUrl)
			        .post(body)
			        .build();
			        
			Response httpResponse = client.newCall(httpRequest).execute();
			//objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String responseJsonStr = httpResponse.body().string();
			
			transliteration = objectMapper.readValue(responseJsonStr, TransliterationResponse.class);
			return transliteration;
		}
		return transliteration;
	}
	
	
	public int prepareAndPushToMetric(ModelExtended model, Benchmark benchmark, Map<String,String> fileMap, String metric, String benchmarkingProcessId) throws IOException, URISyntaxException {
		
		InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();
		Boolean isSyncApi = inferenceAPIEndPoint.isIsSyncApi();
		
		String dataFilePath = fileMap.get("baseLocation")  + File.separator + "data.json";
		log.info("data.json file path :: " + dataFilePath);
		
		InputStream inputStream = Files.newInputStream(Path.of(dataFilePath));
		JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
		String userId = model.getUserId();
		
		reader.beginArray();
		
		List<String> ip = new ArrayList<String>();
		List<String> tgtList = new ArrayList<String>();
		log.info("started processing of data.json file");
		while (reader.hasNext()) {
			
			Object rowObj = new Gson().fromJson(reader, Object.class);
			ObjectMapper mapper = new ObjectMapper();
			String dataRow = mapper.writeValueAsString(rowObj);
			JSONObject inputJson =  new JSONObject(dataRow);
			String input = inputJson.getString("sourceText");
			String targetText = inputJson.getString("targetText");
			ip.add(input);
			tgtList.add(targetText);
			
		}
		reader.endArray();
		reader.close();
		inputStream.close();
		
		log.info("end processing of data.json file");
		
		JSONArray corpus = new JSONArray();
	
		List<List<String>> ipChunks = partition(ip, chunkSize);
		List<List<String>> tgtChunks = partition(tgtList, chunkSize);
				
		int ipChunksSize = ipChunks.size();
		log.info("started calling the inference end point");
		log.info("inference end point url : " + inferenceAPIEndPoint.getCallbackUrl());
		for(int k=0; k<ipChunksSize; k++ ) {
			
			List<String> input = ipChunks.get(k);
			List<String> expectedTgt = tgtChunks.get(k);
			TransliterationResponse transliteration = null;
			
			transliteration = computeSync(inferenceAPIEndPoint,input );
			
			
			if(transliteration != null) {
				@NotNull @Valid SentencesList sentenses = transliteration.getOutput();
				
				int size = input.size();
				for(int i = 0; i< size; i++) {
					SentenceList sentense = sentenses.get(i);
					if(sentense.getTarget() != null && sentense.getTarget().size() > 0) {
						JSONObject target =  new JSONObject();
						target.put("tgt", expectedTgt.get(i));
						target.put("mtgt", sentense.getTarget().get(0));
						corpus.put(target);
					}else {
						log.info("inference end point result is null for input : "+ input.get(i) + " and expected target : " + expectedTgt.get(i));
					}
		            
				}
			}
		}
		
		JSONArray benchmarkDatasets = new JSONArray();
		JSONObject benchmarkDataset  = new JSONObject();
		benchmarkDataset.put("datasetId", benchmark.getBenchmarkId());
		benchmarkDataset.put("metric", metric);
		benchmarkDataset.put("corpus", corpus);
		benchmarkDatasets.put(benchmarkDataset);
        	
		JSONObject metricRequest  = new JSONObject();
		metricRequest.put("benchmarkingProcessId", benchmarkingProcessId);
		metricRequest.put("modelId", model.getModelId());
		metricRequest.put("modelName", model.getName());
		if(benchmark.getLanguages() != null && benchmark.getLanguages().getTargetLanguage() != null) {
			String targetLanguage = benchmark.getLanguages().getTargetLanguage().toString();
			metricRequest.put("targetLanguage", targetLanguage);
		}
		
		metricRequest.put("userId", userId);
		metricRequest.put("modelTaskType", model.getTask().getType().toString());
		metricRequest.put("benchmarkDatasets",benchmarkDatasets);
		
		log.info("data sending to metric calculation ");
		log.info(metricRequest.toString());
		
		benchmarkMetricKafkaTemplate.send(mbMetricTopic,metricRequest.toString());
		
		//save the model inference response
		ModelInferenceResponse modelInferenceResponse = new ModelInferenceResponse();
		modelInferenceResponse.setBenchmarkingProcessId(benchmarkingProcessId);
		modelInferenceResponse.setCorpus(corpus.toString());
		modelInferenceResponse.setBenchmarkDatasetId(benchmark.getBenchmarkId());
		modelInferenceResponse.setMetric(metric);
		modelInferenceResponse.setModelId(model.getModelId());
		modelInferenceResponse.setModelName(model.getName());
		modelInferenceResponse.setUserId(userId);
		modelInferenceResponse.setModelTaskType(model.getTask().getType().toString());
		modelInferenceResponseDao.save(modelInferenceResponse);
		
		int datasetCount = corpus.length();
		return datasetCount;
		
	}
	
	private static <T> List<List<T>> partition(Collection<T> members, int maxSize)
	{
	    List<List<T>> res = new ArrayList<>();
	    List<T> internal = new ArrayList<>();

	    for (T member : members)
	    {
	        internal.add(member);
	        if (internal.size() == maxSize)
	        {
	            res.add(internal);
	            internal = new ArrayList<>();
	        }
	    }
	    if (internal.isEmpty() == false)
	    {
	        res.add(internal);
	    }
	    return res;
	}
	
	public Response okHttpClientPostCall(String requestJson, String url) throws IOException, KeyManagementException, NoSuchAlgorithmException{
		
		//OkHttpClient client = new OkHttpClient();
		
		/*
		 OkHttpClient client = new OkHttpClient.Builder()
		 		 .readTimeout(60, TimeUnit.SECONDS)
			      .build();
		*/	      
		
		
		RequestBody body = RequestBody.create(requestJson,MediaType.parse("application/json"));
		Request httpRequest = new Request.Builder()
		        .url(url)
		        .post(body)
		        .build();
		
		OkHttpClient newClient = getTrustAllCertsClient();
		Response httpResponse = newClient.newCall(httpRequest).execute();
		
		
		return httpResponse;
	}
	
	public static OkHttpClient getTrustAllCertsClient() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        
        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        newBuilder.hostnameVerifier((hostname, session) -> true);
        return newBuilder.readTimeout(60, TimeUnit.SECONDS).build();
    }

}