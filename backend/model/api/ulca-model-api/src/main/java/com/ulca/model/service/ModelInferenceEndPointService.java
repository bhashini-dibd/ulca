package com.ulca.model.service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.request.Input;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelSearchRequest;

import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.model.Sentence;
import io.swagger.model.Sentences;
import io.swagger.model.TranslationRequest;
import io.swagger.model.TranslationResponse;
import reactor.core.publisher.Mono;

import java.net.URL;

@Service
public class ModelInferenceEndPointService {
	
	
	/*
	
	public void test(ModelSearchRequest obj) throws MalformedURLException, URISyntaxException {
		String urlStr = "http://localhost:8080/ulca/apis/v0/model/search";
		
		URL url = new URL(urlStr);
		
		System.out.println(url.getHost());
		System.out.println(url.toURI().toString());
		System.out.println(url.getPath());
		
		WebClient.Builder builder = WebClient.builder();
		
		String responseStr = builder.build().post()
		.uri(urlStr)
		.body(Mono.just(obj), ModelSearchRequest.class)
		.retrieve().bodyToMono(String.class)
		.block();
		
		
		System.out.println(responseStr);
		
	}
	*/
	
	
	
	
	public OneOfInferenceAPIEndPointSchema validateCallBackUrl(String callBackUrl, OneOfInferenceAPIEndPointSchema schema) throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {
		

		if(schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
			io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference)schema;
			TranslationRequest request = translationInference.getRequest();
			
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
			
			TranslationResponse response = objectMapper.readValue(responseStr, TranslationResponse.class);
			 translationInference.setResponse(response);
			 schema = translationInference;
					 
		}
		
		return schema;
		
	}
	
	public TranslationResponse compute(String callBackUrl, OneOfInferenceAPIEndPointSchema schema, List<Input> input ) throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {
		
		TranslationResponse response = null;
		if(schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
			io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference)schema;
			TranslationRequest request = translationInference.getRequest();
			
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
			
			response = objectMapper.readValue(responseStr, TranslationResponse.class);
					 
		}
		
		return response;
	}

}
