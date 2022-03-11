package com.ulca.model.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ulca.benchmark.service.NotificationService;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;

import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ModelHeartBeatCheckService {
	
	@Autowired
	ModelDao modelDao;
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	ModelInferenceEndPointService modelInferenceEndPointService;
	
	@Scheduled(cron = "0 0 */2 * * ?")
	public void notifyFailedModelHeartbeatCheck() {
		
		log.info("*******  start ModelHeartBeatCheckService ::notifyFailedModelHeartbeatCheck ****** ");
		
		List<String> checkedUrl = new ArrayList<String>();
		
		
		List<ModelExtended> list = modelDao.findAll();
		
		List<ModelExtended> heartBeatFailedModelList  = new ArrayList<ModelExtended>();
		
		for(ModelExtended model : list) {
			
			try {
				InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();
				
				if(inferenceAPIEndPoint != null && inferenceAPIEndPoint.getCallbackUrl() != null) {
					
					if(!inferenceAPIEndPoint.getCallbackUrl().isBlank() && !checkedUrl.contains(inferenceAPIEndPoint.getCallbackUrl())) {
						checkedUrl.add(inferenceAPIEndPoint.getCallbackUrl());
						String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
						
						try {
							modelInferenceEndPointService.validateCallBackUrl(inferenceAPIEndPoint);
						} catch (Exception e) {
							heartBeatFailedModelList.add(model);
							log.info("heartBeat Failed " + model.getName() + " :: " + callBackUrl);
							e.printStackTrace();
						}
						
					}
				}
				
			}catch(Exception e) {
				
				heartBeatFailedModelList.add(model);
				log.info("heartBeat Failed " + model.getName() + " reason :: " + e.getMessage());
				e.printStackTrace();
			}
			
		}
		if(heartBeatFailedModelList.size() > 0) {
			notificationService.notifyNodelHeartBeatFailure(heartBeatFailedModelList);
		}
		
		log.info("*******  end ModelHeartBeatCheckService ::notifyFailedModelHeartbeatCheck ****** ");
	}

}
