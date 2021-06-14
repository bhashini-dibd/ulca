package com.ulca.dataset.kakfa;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class DatasetErrorPublishService {
	
	@Autowired
	private KafkaTemplate<String, String> datasetErrorKafkaTemplate;

	@Value(value = "${KAFKA_ULCA_DS_ERROR_IP_TOPIC}")
	private String errorTopic;
	
	public void publishDatasetError(JSONObject message) {
		
		datasetErrorKafkaTemplate.send(errorTopic,0,null, message.toString());
	}

}
