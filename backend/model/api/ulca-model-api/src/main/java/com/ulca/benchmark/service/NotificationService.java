package com.ulca.benchmark.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	
	
	@Value("${kafka.ulca.notifier.consumer.ip.topic}")
	private String notifierTopic;
	
	
	public void notifyNodelHeartBeatFailure( String modelName) {
		JSONObject msg = new JSONObject();
		msg.put("event", "inference-check-failed");
		//msg.put("entityID", modelId);
		//msg.put("userID", userId);
		
		JSONObject details = new JSONObject();
		details.put("modelName", modelName);
		msg.put("details", details);
		
		kafkaTemplate.send(notifierTopic, msg.toString());
		
	}
	
}
