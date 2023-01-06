package com.ulca.model.service;

import com.ulca.model.dao.ModelExtended;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ModelNotificationService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafka.ulca.notifier.consumer.ip.topic}")
	private String notifierTopic;

	public void notifyNodelHeartBeatFailure(List<ModelExtended> list) {
		JSONObject msg = new JSONObject();
		msg.put("event", "inference-check-failed");

		JSONArray detailsArray = new JSONArray();
		for (ModelExtended model : list) {

			if (model.getTask() != null && model.getTask().getType() != null) {
				JSONObject details = new JSONObject();
				details.put("modelName", model.getName());
				details.put("taskType", model.getTask().getType().toString());

				if (model.getInferenceEndPoint() != null) {
					details.put("callBackUrl", model.getInferenceEndPoint().getCallbackUrl());
					detailsArray.put(details);

				}
			}
		}
		msg.put("details", detailsArray);
		kafkaTemplate.send(notifierTopic, msg.toString());

		log.info(" failed model heart beat details :: " + msg.toString());
	}
}
