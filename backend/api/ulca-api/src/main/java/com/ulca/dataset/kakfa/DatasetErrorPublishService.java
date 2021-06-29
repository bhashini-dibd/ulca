package com.ulca.dataset.kakfa;

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
	

	
	public void publishDatasetError(String eventType, String code, String message, String serviceRequestNumber, String datasetName,String stage , String datasetType) {
		
		
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
		
		datasetErrorKafkaTemplate.send(errorTopic, errorMessage.toString());
	}
	
	public void publishEofStatus(String serviceRequestNumber) {
		JSONObject errorMessage = new JSONObject();
		errorMessage.put("serviceRequestNumber",  serviceRequestNumber);
		errorMessage.put("eof", true);
		datasetErrorKafkaTemplate.send(errorTopic, errorMessage.toString());
	}

}
