package com.ulca.benchmark.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ulca.model.dao.ModelExtended;

import io.swagger.model.ASRRequest;
import io.swagger.model.OCRRequest;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.model.TTSRequest;
import io.swagger.model.TranslationRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	
	
	@Value("${kafka.ulca.notifier.consumer.ip.topic}")
	private String notifierTopic;
	
	
	public void notifyNodelHeartBeatFailure(List<ModelExtended> list) {
		JSONObject msg = new JSONObject();
		msg.put("event", "inference-check-failed");
		
		JSONArray  detailsArray = new JSONArray();
		for(ModelExtended model : list) {
			JSONObject details = new JSONObject();
			details.put("modelName", model.getName());
			if(model.getTask() != null &&  model.getTask().getType() != null ) {
				details.put("taskType", model.getTask().getType().toString());
			}
			
			
			if(model.getInferenceEndPoint() != null) {
				details.put("callBackUrl",model.getInferenceEndPoint().getCallbackUrl());
				
				if(model.getInferenceEndPoint().getSchema() != null) {
					
					OneOfInferenceAPIEndPointSchema schema = model.getInferenceEndPoint().getSchema();
					
					
					if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
						io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference) schema;
						TranslationRequest request = translationInference.getRequest();
						JSONObject jo  = new JSONObject(request);
						details.put("request",jo);
						
					}else if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.ASRInference")) {
						io.swagger.model.ASRInference asrInference = (io.swagger.model.ASRInference) schema;
						ASRRequest request = asrInference.getRequest();
						JSONObject jo  = new JSONObject(request);
						details.put("request",jo);
						
					}else if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.OCRInference")) {
						io.swagger.model.OCRInference ocrInference = (io.swagger.model.OCRInference) schema;
						OCRRequest request = ocrInference.getRequest();
						JSONObject jo  = new JSONObject(request);
						details.put("request",jo);
						
					}if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TTSInference")) {
						io.swagger.model.TTSInference ttsInference = (io.swagger.model.TTSInference) schema;
						TTSRequest request = ttsInference.getRequest();
						JSONObject jo  = new JSONObject(request);
						details.put("request",jo);
						
					}
				}
				
			}
			
			detailsArray.put(details);
			
		}
		
		msg.put("details", detailsArray);
		
		kafkaTemplate.send(notifierTopic, msg.toString());
		
		log.info(" failed model heart beat details :: " + msg.toString());
		
	}
	
}
