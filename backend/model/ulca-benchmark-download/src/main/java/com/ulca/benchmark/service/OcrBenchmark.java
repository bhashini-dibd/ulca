package com.ulca.benchmark.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64; //java8 base64

//Import the Base64 encoding library.
//import org.apache.commons.codec.binary.Base64;

import javax.sound.sampled.AudioFormat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ulca.benchmark.model.ModelInferenceResponse;
import com.ulca.benchmark.request.AsrComputeRequest;
import com.ulca.benchmark.request.AsrComputeResponse;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelInferenceResponseDao;

import io.swagger.model.ASRInference;
import io.swagger.model.ASRRequest;
import io.swagger.model.ASRResponse;
import io.swagger.model.Benchmark;
import io.swagger.model.ImageFile;
import io.swagger.model.ImageFiles;
import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.OCRInference;
import io.swagger.model.OCRRequest;
import io.swagger.model.OCRResponse;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.model.Sentence;
import io.swagger.model.Sentences;
import io.swagger.model.TranslationRequest;
import io.swagger.model.TranslationResponse;

import lombok.extern.slf4j.Slf4j;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.commons.io.FileUtils;


@Slf4j
@Service
public class OcrBenchmark {

	@Autowired
	private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;
	
	@Value("${kafka.ulca.bm.metric.ip.topic}")
	private String mbMetricTopic;
	
	@Autowired
	ModelInferenceResponseDao modelInferenceResponseDao;
	
	public int prepareAndPushToMetric(ModelExtended model, Benchmark benchmark, Map<String,String> fileMap, String metric, String benchmarkingProcessId) throws IOException, URISyntaxException {
		
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
		
		String baseLocation = fileMap.get("baseLocation")  + File.separator ;
		JSONArray corpus = new JSONArray();
		int totalRecords = 0;
		int failedRecords = 0;
		while (reader.hasNext()) {
			
			Object rowObj = new Gson().fromJson(reader, Object.class);
			ObjectMapper mapper = new ObjectMapper();
			String dataRow = mapper.writeValueAsString(rowObj);
			
			JSONObject inputJson =  new JSONObject(dataRow);
			String imageFilename = inputJson.getString("imageFilename");
			String imagePath = baseLocation + imageFilename;
			
			byte[] bytes = FileUtils.readFileToByteArray(new File(imagePath));
			
			ImageFile imageFile = new ImageFile();
			imageFile.setImageContent(bytes);
			
			ImageFiles imageFiles = new ImageFiles();
			imageFiles.add(imageFile);
			
			log.info("start time for calling the inference end point");
			log.info("dataRow :: " + dataRow);
			OCRInference ocrInference = (OCRInference) schema;
			
			OCRRequest ocrRequest = ocrInference.getRequest();
			ocrRequest.setImage(imageFiles);
			
			String resultText = compute(callBackUrl, ocrRequest);	
			
			log.info("result :: " + resultText);
			log.info("end time for calling the inference end point");
			
			String targetText = inputJson.getString("groundTruth");
			totalRecords++;
			if(resultText != null) {
				JSONObject target =  new JSONObject();
				target.put("tgt", targetText);
				target.put("mtgt", resultText);
				corpus.put(target);
			}else {
				failedRecords++;
			}
			
		}
		reader.endArray();
		reader.close();
		inputStream.close();
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
		log.info("total recoords :: " + totalRecords + " failedRecords :: " + failedRecords);
		log.info("data before sending to metric");
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
	
	public String compute(String callBackUrl, OCRRequest request) throws IOException {
		

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
		String responseJsonStr = httpResponse.body().string();
		log.info("logging ocr inference point response" + responseJsonStr);
		
		OCRResponse response = objectMapper.readValue(responseJsonStr, OCRResponse.class);
		
		if(response != null && response.getOutput() != null && response.getOutput().size() > 0) {
			return response.getOutput().get(0).getSource();
		}
		
		return null;
	}
	
	
}