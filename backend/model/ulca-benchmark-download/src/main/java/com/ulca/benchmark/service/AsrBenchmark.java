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
import java.util.Base64;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ulca.model.dao.AsrCallBackRequest;
import com.ulca.model.dao.ModelExtended;

import io.swagger.model.ASRRequest;
import io.swagger.model.ASRResponse;
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
public class AsrBenchmark {

	@Autowired
	private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;
	
	@Value("${kafka.ulca.bm.metric.ip.topic}")
	private String mbMetricTopic;
	
	
	@Autowired
	WebClient.Builder builder;

	

	public String compute(String callBackUrl, OneOfInferenceAPIEndPointSchema schema,
			byte[] base64audioContent)
			throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {

		log.info("calling the inference end point");
		if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.ASRInference")) {

			io.swagger.model.ASRInference asrInference = (io.swagger.model.ASRInference) schema;
			ASRRequest request = asrInference.getRequest();

			AsrCallBackRequest asrCallBackRequest = new AsrCallBackRequest();
			AsrCallBackRequest.Config config = asrCallBackRequest.getConfig();

			config.setAudioFormat(request.getConfig().getAudioFormat().toString().toUpperCase());
			config.setTranscriptionFormat(
					request.getConfig().getTranscriptionFormat().getValue().toString().toUpperCase());
			AsrCallBackRequest.Language lang = config.getLanguage();
			lang.setValue(request.getConfig().getLanguage().getSourceLanguage().toString());
			config.setLanguage(lang);
			asrCallBackRequest.setConfig(config);
			AsrCallBackRequest.Audio audio = asrCallBackRequest.getAudio();
			
			audio.setAudioContent(base64audioContent);
			asrCallBackRequest.setAudio(audio);

			// WebClient.Builder builder = WebClient.builder();

			String responseStr = builder.build().post().uri(callBackUrl)
					.body(Mono.just(asrCallBackRequest), AsrCallBackRequest.class).retrieve().bodyToMono(String.class)
					.block();

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readValue(responseStr, JsonNode.class);

			log.info("response CallBackUrl:: ");
			log.info(responseStr);
			ASRResponse asrResponse = new ASRResponse();
			Sentences sentences = new Sentences();
			Sentence sentence = new Sentence();
			sentence.setTarget(jsonNode.get("transcript").asText());
			sentences.add(sentence);
			asrResponse.setOutput(sentences);
			
			return jsonNode.get("transcript").asText() ;
			
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
		reader.beginArray();
		
		List<String> ip = new ArrayList<String>();
		List<String> tgtList = new ArrayList<String>();
		
		String baseLocation = fileMap.get("baseLocation")  + File.separator ;
		JSONArray corpus = new JSONArray();
		while (reader.hasNext()) {
			
			Object rowObj = new Gson().fromJson(reader, Object.class);
			ObjectMapper mapper = new ObjectMapper();
			String dataRow = mapper.writeValueAsString(rowObj);
			log.info("dataRow :: " + dataRow);
			JSONObject inputJson =  new JSONObject(dataRow);
			String audioFilename = inputJson.getString("audioFilename");
			String audioPath = baseLocation + audioFilename;
			byte[] bytes = Files.readAllBytes(Paths.get(audioPath));
			
			String resultText = compute(callBackUrl, schema, Base64.getMimeEncoder().encode(bytes));
			
			String targetText = inputJson.getString("text");
			JSONObject target =  new JSONObject();
			target.put("tgt", targetText);
			target.put("mtgt", resultText);
			corpus.put(target);
		}
		reader.endArray();
		reader.close();
		inputStream.close();
		
		JSONObject benchmarkDatasets  = new JSONObject();
		benchmarkDatasets.put("datasetId", benchmark.getBenchmarkId());
		benchmarkDatasets.put("metric", metric);
		benchmarkDatasets.put("corpus", corpus);

		JSONObject metricRequest  = new JSONObject();
		metricRequest.put("benchmarkingProcessId", benchmarkingProcessId);
		metricRequest.put("modelId", model.getModelId());
		metricRequest.put("modelTaskType", model.getTask().getType().toString());
		metricRequest.put("benchmarkDatasets",benchmarkDatasets);
		log.info("data before sending to metric");
		log.info(metricRequest.toString());
		
		benchmarkMetricKafkaTemplate.send(mbMetricTopic,metricRequest.toString());
		
	}
	
	
}