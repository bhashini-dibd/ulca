package com.ulca.model.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.ulca.model.dao.AsrCallBackRequest;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.request.Input;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.response.ModelComputeResponse;

import io.swagger.model.ASRRequest;
import io.swagger.model.ASRResponse;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.model.Sentence;
import io.swagger.model.Sentences;
import io.swagger.model.TranslationRequest;
import io.swagger.model.TranslationResponse;
import reactor.core.publisher.Mono;

import java.net.URL;

@Service
public class ModelInferenceEndPointService {
	
	
	
	public OneOfInferenceAPIEndPointSchema validateCallBackUrl(String callBackUrl, OneOfInferenceAPIEndPointSchema schema) throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {
		

		if(schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
			io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference)schema;
			TranslationRequest request = translationInference.getRequest();
			
			//URL url = new URL(callBackUrl);
			
			
			WebClient.Builder builder = WebClient.builder();
			
			String responseStr = builder.build().post()
			.uri(callBackUrl)
			.body(Mono.just(request), TranslationRequest.class)
			.retrieve().bodyToMono(String.class)
			.block();
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			TranslationResponse response = objectMapper.readValue(responseStr, TranslationResponse.class);
			 translationInference.setResponse(response);
			 schema = translationInference;
					 
		}
		
		if(schema.getClass().getName().equalsIgnoreCase("io.swagger.model.ASRInference")) {
			io.swagger.model.ASRInference asrInference = (io.swagger.model.ASRInference)schema;
			ASRRequest request = asrInference.getRequest();
			
			
			System.out.println(request.toString());
			
			AsrCallBackRequest asrCallBackRequest = new AsrCallBackRequest();
			AsrCallBackRequest.Config config = asrCallBackRequest.getConfig();
			
			System.out.println("audio format");
			System.out.println(request.getConfig().getAudioFormat().toString().toUpperCase());
			
			System.out.println("config");
			System.out.println(config);
			config.setAudioFormat(request.getConfig().getAudioFormat().toString().toUpperCase());
			config.setTranscriptionFormat(request.getConfig().getTranscriptionFormat().getValue().toString().toUpperCase());
			AsrCallBackRequest.Language lang = config.getLanguage();
			lang.setValue(request.getConfig().getLanguage().getSourceLanguage().toString());
			config.setLanguage(lang);
			asrCallBackRequest.setConfig(config);
			AsrCallBackRequest.Audio audio = asrCallBackRequest.getAudio();
			audio.setAudioUri(request.getAudio().getAudioUri());
			asrCallBackRequest.setAudio(audio);
			
			
			
			WebClient.Builder builder = WebClient.builder();
			
			
			String responseStr = builder.build().post()
			.uri(callBackUrl).accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(asrCallBackRequest), AsrCallBackRequest.class)
			.retrieve().bodyToMono(String.class)
			.block();
			
			
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			JsonNode jsonNode = objectMapper.readValue(responseStr, JsonNode.class);
			
			System.out.println("response :: ");
			System.out.println(responseStr);
			ASRResponse asrResponse = new ASRResponse();
			Sentences sentences = new Sentences();
			Sentence sentence = new Sentence();
			sentence.setTarget(jsonNode.get("transcript").asText());
			sentences.add(sentence);
			asrResponse.setOutput(sentences);
			
			asrResponse.setOutput(null);
			asrInference.setResponse(asrResponse);
			schema = asrInference;
					 
		}
		
		return schema;
		
	}
	
	public ModelComputeResponse compute(String callBackUrl, OneOfInferenceAPIEndPointSchema schema, ModelComputeRequest compute ) throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {
		
		ModelComputeResponse response = new ModelComputeResponse();
		
		
		if(schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
			io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference)schema;
			TranslationRequest request = translationInference.getRequest();
			
			 List<Input> input = compute.getInput();
			Sentences sentences = new Sentences();
			for(Input ip : input) {
				Sentence sentense = new Sentence();
				sentense.setSource(ip.getSource());
				sentences.add(sentense);
			}
			request.setInput(sentences);
			
			URL url = new URL(callBackUrl);
			
			System.out.println(url.getHost());
			System.out.println(url.toURI().toString());
			System.out.println(url.getPath());
			
			WebClient.Builder builder = WebClient.builder();
			
			String responseStr = builder.build().post()
			.uri(callBackUrl)
			.body(Mono.just(request), TranslationRequest.class)
			.retrieve().bodyToMono(String.class)
			.block();
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			TranslationResponse translation = objectMapper.readValue(responseStr, TranslationResponse.class);
		
			response.setTranslation(translation);		}
		if(schema.getClass().getName().equalsIgnoreCase("io.swagger.model.ASRInference")) {
			

			io.swagger.model.ASRInference asrInference = (io.swagger.model.ASRInference)schema;
			ASRRequest request = asrInference.getRequest();
			
			
			System.out.println(request.toString());
			
			AsrCallBackRequest asrCallBackRequest = new AsrCallBackRequest();
			AsrCallBackRequest.Config config = asrCallBackRequest.getConfig();
			
			System.out.println("audio format");
			System.out.println(request.getConfig().getAudioFormat().toString().toUpperCase());
			
			System.out.println("config");
			System.out.println(config);
			config.setAudioFormat(request.getConfig().getAudioFormat().toString().toUpperCase());
			config.setTranscriptionFormat(request.getConfig().getTranscriptionFormat().getValue().toString().toUpperCase());
			AsrCallBackRequest.Language lang = config.getLanguage();
			lang.setValue(request.getConfig().getLanguage().getSourceLanguage().toString());
			config.setLanguage(lang);
			asrCallBackRequest.setConfig(config);
			AsrCallBackRequest.Audio audio = asrCallBackRequest.getAudio();
			audio.setAudioUri(request.getAudio().getAudioUri());
			asrCallBackRequest.setAudio(audio);
			
			
			
			WebClient.Builder builder = WebClient.builder();
			
			
			String responseStr = builder.build().post()
			.uri(callBackUrl).accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(asrCallBackRequest), AsrCallBackRequest.class)
			.retrieve().bodyToMono(String.class)
			.block();
			
			
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			JsonNode jsonNode = objectMapper.readValue(responseStr, JsonNode.class);
			
			System.out.println("response :: ");
			System.out.println(responseStr);
			ASRResponse asrResponse = new ASRResponse();
			Sentences sentences = new Sentences();
			Sentence sentence = new Sentence();
			sentence.setTarget(jsonNode.get("transcript").asText());
			sentences.add(sentence);
			asrResponse.setOutput(sentences);
			response.setAsr(asrResponse);
			
		}
		return response;
	}

}
