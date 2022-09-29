package com.ulca.dataset.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	@Autowired
	private KafkaTemplate<String, String> datasetNotificationKafkaTemplate;

	
	
	@Value("${kafka.ulca.notifier.consumer.ip.topic}")
	private String notifierTopic;
	
	
	public void notifyDatasetComplete(String serviceRequestNumber, String datasetName, String userId) {
		JSONObject msg = new JSONObject();
		msg.put("event", "dataset-submit-completed");
		msg.put("entityID", serviceRequestNumber);
		msg.put("userID", userId);
		
		JSONObject details = new JSONObject();
		msg.put("details", datasetName);
		msg.put("details", details);
		
		datasetNotificationKafkaTemplate.send(notifierTopic, msg.toString());
		
	}
	
	public void notifyDatasetFailed(String serviceRequestNumber, String datasetName, String userId) {
		JSONObject msg = new JSONObject();
		msg.put("event", "dataset-submit-failed");
		msg.put("entityID", serviceRequestNumber);
		msg.put("userID", userId);
		
		JSONObject details = new JSONObject();
		msg.put("details", datasetName);
		msg.put("details", details);
		
		datasetNotificationKafkaTemplate.send(notifierTopic, msg.toString());
		
	}

}
