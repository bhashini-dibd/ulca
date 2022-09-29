package com.ulca.dataset.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import io.swagger.model.DatasetType;


@Service
public class DatasetErrorPublishService {
	
	@Autowired
	private KafkaTemplate<String, String> datasetErrorKafkaTemplate;

	@Value("${kafka.ulca.ds.error.ip.topic}")
	private String errorTopic;
	

	
	public void publishDatasetError(String eventType, String code, String message, String serviceRequestNumber, String datasetName,String stage , String datasetType, String record) {
		
		
		JSONObject errorMessage = new JSONObject();
		errorMessage.put("eventType", eventType);
		errorMessage.put("messageType", "error");
		errorMessage.put("code", code);
		errorMessage.put("eventId", "serviceRequestNumber|" + serviceRequestNumber);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		Date date = cal.getTime();
		// errorMessage.put("timestamp", df2.format(date));
		errorMessage.put("timestamp", new Date().toString());
		errorMessage.put("serviceRequestNumber", serviceRequestNumber);
		errorMessage.put("datasetName", datasetName);
		errorMessage.put("stage", stage);
		errorMessage.put("datasetType", datasetType);
		errorMessage.put("message", message);
		errorMessage.put("record", record);
		
		datasetErrorKafkaTemplate.send(errorTopic, errorMessage.toString());
	}
	
	

}
