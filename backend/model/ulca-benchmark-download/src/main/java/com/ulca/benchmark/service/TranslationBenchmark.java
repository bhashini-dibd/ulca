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
import java.util.List;
import java.util.Map;

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
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
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

	@Autowired
	private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;
	
	@Value("${kafka.ulca.bm.metric.ip.topic}")
	private String mbMetricTopic;
	
	
	@Autowired
	WebClient.Builder builder;

	

	public String compute(String callBackUrl, OneOfInferenceAPIEndPointSchema schema,
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
			
			return translation.getOutput().get(0).getTarget();
			
		}
		return null;
		
	}
	
	public void prepareAndPushToMetric(ModelExtended model, Benchmark benchmark, Map<String,String> fileMap) throws IOException, URISyntaxException {
		
		InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
		OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();
		
		String dataFilePath = fileMap.get("baseLocation")  + File.separator + "data.json";
		log.info("data.json file path :: " + dataFilePath);
		
		InputStream inputStream = Files.newInputStream(Path.of(dataFilePath));
		JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
		reader.beginArray();
		while (reader.hasNext()) {
			
			Object rowObj = new Gson().fromJson(reader, Object.class);
			ObjectMapper mapper = new ObjectMapper();
			String dataRow = mapper.writeValueAsString(rowObj);
			
			JSONObject inputJson =  new JSONObject(dataRow);
			
			String input = inputJson.getString("sourceText");
			
			List<String> ip = new ArrayList<String>();
			ip.add(input);
			
			String mtgt = compute(callBackUrl, schema,ip );
	          
			JSONObject target =  new JSONObject();
			target.put("mtgt", mtgt);
			target.put("src", inputJson.getString("sourceText"));
			target.put("tgt", inputJson.getString("targetText"));
			target.put("metric", "BLEU");
			
			benchmarkMetricKafkaTemplate.send(mbMetricTopic,target.toString());
			
			
		}
		reader.endArray();
		reader.close();
		inputStream.close();
		
		
	}

}
