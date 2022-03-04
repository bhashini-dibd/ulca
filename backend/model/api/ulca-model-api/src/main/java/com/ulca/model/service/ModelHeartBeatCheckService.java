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
	
	@Scheduled(cron = "0 */2 * * *")
	public void notifyFailedModelHeartbeatCheck() {
		
		log.info("Inside the cronjob");
		
		List<String> checkedUrl = new ArrayList<String>();
		
		
		List<ModelExtended> list = modelDao.findAll();
		for(ModelExtended model : list) {
			
			InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();
			
			if(!inferenceAPIEndPoint.getCallbackUrl().isBlank() && !checkedUrl.contains(inferenceAPIEndPoint.getCallbackUrl())) {
				checkedUrl.add(inferenceAPIEndPoint.getCallbackUrl());
				String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
				OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();
				try {
					 modelInferenceEndPointService.validateCallBackUrl(inferenceAPIEndPoint);
				} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					notificationService.notifyNodelHeartBeatFailure(model.getName());
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					notificationService.notifyNodelHeartBeatFailure(model.getName());
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					notificationService.notifyNodelHeartBeatFailure(model.getName());
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					notificationService.notifyNodelHeartBeatFailure(model.getName());
					e.printStackTrace();
				}catch (Exception e) {
					// TODO Auto-generated catch block
					notificationService.notifyNodelHeartBeatFailure(model.getName());
					e.printStackTrace();
				}
				
			}
		}
		
		
	}

}
