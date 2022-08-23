package com.ulca.benchmark.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.model.ModelInferenceResponse;
import com.ulca.benchmark.request.AsrComputeRequest;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelInferenceResponseDao;

import io.swagger.model.ASRInference;
import io.swagger.model.Benchmark;
import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsrBenchmark {

	@Autowired
	private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;

	@Value("${kafka.ulca.bm.metric.ip.topic}")
	private String mbMetricTopic;

	@Value("${asrcomputeurl}")
	private String asrcomputeurl;

	@Autowired
	WebClient.Builder builder;

	@Autowired
	ModelInferenceResponseDao modelInferenceResponseDao;
	
	@Autowired
	BenchmarkProcessDao benchmarkProcessDao;

	@Autowired
	OkHttpClientService okHttpClientService;

	public void  prepareAndPushToMetric(ModelExtended model, Benchmark benchmark, Map<String,String> fileMap, String metric, String benchmarkingProcessId) throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {

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
			String audioFilename = inputJson.getString("audioFilename");
			String audioPath = baseLocation + audioFilename;

			byte[] bytes = Files.readAllBytes(Paths.get(audioPath));

			AsrComputeRequest request = new AsrComputeRequest();
			request.setCallbackUrl(callBackUrl);
			request.setFilePath(audioPath);

			log.info("start time for calling the inference end point");
			log.info("dataRow :: " + dataRow);
			ASRInference asrInference = (ASRInference) schema;
			request.setSourceLanguage(asrInference.getRequest().getConfig().getLanguage().getSourceLanguage().toString());

			String resultText = okHttpClientService.asrComputeInternal(request);
			log.info("result :: " + resultText);
			log.info("end time for calling the inference end point");

			String targetText = inputJson.getString("text");
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

		//update the total record count
		int datasetCount = corpus.length();
		BenchmarkProcess bmProcessUpdate = benchmarkProcessDao.findByBenchmarkProcessId(benchmarkingProcessId);
		bmProcessUpdate.setRecordCount(datasetCount);
		bmProcessUpdate.setLastModifiedOn(new Date().toString());
		benchmarkProcessDao.save(bmProcessUpdate);

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
		
	}
	
}