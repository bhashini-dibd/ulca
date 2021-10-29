package com.ulca.model.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ulca.model.dao.AsrCallBackRequest;
import com.ulca.model.request.Input;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.response.ModelComputeResponse;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.swagger.model.ASRRequest;
import io.swagger.model.ASRResponse;
import io.swagger.model.OCRRequest;
import io.swagger.model.OCRResponse;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.model.Sentence;
import io.swagger.model.Sentences;
import io.swagger.model.TranslationRequest;
import io.swagger.model.TranslationResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Service
public class ModelInferenceEndPointService {

	@Autowired
	WebClient.Builder builder;

	public OneOfInferenceAPIEndPointSchema validateCallBackUrl(String callBackUrl,
			OneOfInferenceAPIEndPointSchema schema)
			throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {

		if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
			io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference) schema;
			TranslationRequest request = translationInference.getRequest();

			String responseStr = builder.build().post().uri(callBackUrl)
					.body(Mono.just(request), TranslationRequest.class).retrieve().bodyToMono(String.class).block();

			ObjectMapper objectMapper = new ObjectMapper();

			TranslationResponse response = objectMapper.readValue(responseStr, TranslationResponse.class);
			translationInference.setResponse(response);
			schema = translationInference;

		}

		if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.ASRInference")) {
			io.swagger.model.ASRInference asrInference = (io.swagger.model.ASRInference) schema;
			ASRRequest request = asrInference.getRequest();

			/*
			AsrCallBackRequest asrCallBackRequest = new AsrCallBackRequest();
			AsrCallBackRequest.Config config = asrCallBackRequest.getConfig();

			config.setAudioFormat(request.getConfig().getAudioFormat().toString().toUpperCase());
			config.setTranscriptionFormat(
					request.getConfig().getTranscriptionFormat().getValue().toString().toUpperCase());
			AsrCallBackRequest.Language lang = config.getLanguage();
			lang.setValue(request.getConfig().getLanguage().getSourceLanguage().toString());
			config.setLanguage(lang);
			asrCallBackRequest.setConfig(config);
			
			asrCallBackRequest.setAudio(asrInference.ge.);
			//AsrCallBackRequest.Audio audio = asrCallBackRequest.getAudio();
			//request.getAudio()
			//audio.setAudioUri(request.getAudio().getAudioUri());
			//asrCallBackRequest.setAudio(audio);

*/
			// WebClient.Builder builder = WebClient.builder();
			
			 ASRResponse response  = null;
			SslContext sslContext;
			try {
				sslContext = SslContextBuilder
				        .forClient()
				        .trustManager(InsecureTrustManagerFactory.INSTANCE)
				        .build();
				
				 HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
				 //builder.clientConnector(new ReactorClientHttpConnector(httpClient)).
				    
				  /*response = builder.build().post().uri(callBackUrl)
							.body(Mono.just(request), ASRRequest.class).retrieve().bodyToMono(ASRResponse.class)
							.block(); 
				 */
				 response = builder.clientConnector(new ReactorClientHttpConnector(httpClient)).build().post().uri(callBackUrl)
							.body(Mono.just(request), ASRRequest.class).retrieve().bodyToMono(ASRResponse.class)
							.block(); 
				 
			} catch (SSLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   

			

			/*
			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode jsonNode = objectMapper.readValue(responseStr, JsonNode.class);

			log.info("response CallBackUrl :: ");
			log.info(responseStr);
			ASRResponse asrResponse = new ASRResponse();
			Sentences sentences = new Sentences();
			Sentence sentence = new Sentence();
			sentence.setTarget(jsonNode.get("transcript").asText());
			sentences.add(sentence);
			asrResponse.setOutput(sentences);

			asrResponse.setOutput(null);
			*/
			ObjectMapper objectMapper = new ObjectMapper();
			log.info("testing the output response" );
			log.info(objectMapper.writeValueAsString(response));
			
			asrInference.setResponse(response);
			schema = asrInference;

		}
		
		if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.OCRInference")) {
			io.swagger.model.OCRInference ocrInference = (io.swagger.model.OCRInference) schema;
			OCRRequest request = ocrInference.getRequest();

			String responseStr = builder.build().post().uri(callBackUrl)
					.body(Mono.just(request), TranslationRequest.class).retrieve().bodyToMono(String.class).block();

			log.info("response test for OCRRRequest" + responseStr);
			ObjectMapper objectMapper = new ObjectMapper();

			OCRResponse response = objectMapper.readValue(responseStr, OCRResponse.class);
			ocrInference.setResponse(response);
			schema = ocrInference;

		}

		return schema;

	}

	public ModelComputeResponse compute(String callBackUrl, OneOfInferenceAPIEndPointSchema schema,
			ModelComputeRequest compute)
			throws MalformedURLException, URISyntaxException, JsonMappingException, JsonProcessingException {

		ModelComputeResponse response = new ModelComputeResponse();

		if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
			io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference) schema;
			TranslationRequest request = translationInference.getRequest();

			List<Input> input = compute.getInput();
			Sentences sentences = new Sentences();
			for (Input ip : input) {
				Sentence sentense = new Sentence();
				sentense.setSource(ip.getSource());
				sentences.add(sentense);
			}
			request.setInput(sentences);

			// WebClient.Builder builder = WebClient.builder();

			String responseStr = builder.build().post().uri(callBackUrl)
					.body(Mono.just(request), TranslationRequest.class).retrieve().bodyToMono(String.class).block();

			ObjectMapper objectMapper = new ObjectMapper();

			TranslationResponse translation = objectMapper.readValue(responseStr, TranslationResponse.class);

			response.setOutputText(translation.getOutput().get(0).getTarget());
			
			return response;
		}
		/*
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
			//AsrCallBackRequest.Audio audio = asrCallBackRequest.getAudio();
			
			if(compute.getAudioUri() != null) {
				log.info("compute audio uri");
				log.info(compute.getAudioUri());
				//audio.setAudioUri(compute.getAudioUri());
			}else if(compute.getAudioContent() != null) {
				log.info("compute audio content");
				//audio.setAudioContent(compute.getAudioContent());
			}
			
			//asrCallBackRequest.setAudio(audio);

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
			
			response.setOutputText(jsonNode.get("transcript").asText());
			
			return response;
		}
		*/
		
		if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.OCRInference")) {
			io.swagger.model.OCRInference ocrInference = (io.swagger.model.OCRInference) schema;
			
			List<String> imageUrlList = new ArrayList<String>();
			imageUrlList.add(compute.getImageUri());
			OCRRequest request = ocrInference.getRequest();
			request.setImageUri(imageUrlList);

			String responseStr = builder.build().post().uri(callBackUrl)
					.body(Mono.just(request), TranslationRequest.class).retrieve().bodyToMono(String.class).block();

			log.info("response test for OCRRRequest" + responseStr);
			ObjectMapper objectMapper = new ObjectMapper();

			OCRResponse ocrResponse = objectMapper.readValue(responseStr, OCRResponse.class);
			
			response.setOutputText(ocrResponse.getOutput().get(0).getSource());
			

		}
		
		return response;
	}

}
