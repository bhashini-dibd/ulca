package com.ulca.benchmark.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ulca.model.dao.ModelExtended;

import io.swagger.model.Benchmark;
import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.model.Sentence;
import io.swagger.model.Sentences;
import io.swagger.model.TranslationRequest;
import io.swagger.model.TranslationResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TranslationBenchmark {

	private final int chunkSize = 20;
	
	@Autowired
	private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;
	
	@Value("${kafka.ulca.bm.metric.ip.topic}")
	private String mbMetricTopic;
	
	
	@Autowired
	WebClient.Builder builder;

	

	public TranslationResponse compute(String callBackUrl, OneOfInferenceAPIEndPointSchema schema,
			List<String> sourceSentences)
			throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {

		if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
			io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference) schema;
			TranslationRequest request = translationInference.getRequest();

			
			Sentences sentences = new Sentences();
			for (String ip : sourceSentences) {
				Sentence sentense = new Sentence();
				sentense.setSource(ip);
				sentences.add(sentense);
			}
			request.setInput(sentences);

			String responseStr = builder.build().post().uri(callBackUrl)
					.body(Mono.just(request), TranslationRequest.class).retrieve().bodyToMono(String.class).block();

			ObjectMapper objectMapper = new ObjectMapper();

			TranslationResponse translation = objectMapper.readValue(responseStr, TranslationResponse.class);
			
			return translation;
			
		}
		return null;
		
	}
	
	public void prepareAndPushToMetric(ModelExtended model, Benchmark benchmark, Map<String,String> fileMap, String metric, String benchmarkingProcessId) throws IOException, URISyntaxException {
		
		InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();
		
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
		log.info("inference end point url : " + callBackUrl);
		for(int k=0; k<ipChunksSize; k++ ) {
			
			List<String> input = ipChunks.get(k);
			List<String> expectedTgt = tgtChunks.get(k);
			
			TranslationResponse translation = compute(callBackUrl, schema,input );
			Sentences sentenses = translation.getOutput();
			
			int size = input.size();
			for(int i = 0; i< size; i++) {
				Sentence sentense = sentenses.get(i);
	            JSONObject target =  new JSONObject();
				target.put("tgt", expectedTgt.get(i));
				target.put("mtgt", sentense.getTarget());
				corpus.put(target);
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
		metricRequest.put("userId", userId);
		metricRequest.put("modelTaskType", model.getTask().getType().toString());
		metricRequest.put("benchmarkDatasets",benchmarkDatasets);
		
		log.info("data sending to metric calculation ");
		log.info(metricRequest.toString());
		
		benchmarkMetricKafkaTemplate.send(mbMetricTopic,metricRequest.toString());
		
		
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

}