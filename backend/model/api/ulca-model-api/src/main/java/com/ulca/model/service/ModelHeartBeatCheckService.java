package com.ulca.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ulca.model.dao.ModelHealthStatus;
import com.ulca.model.dao.ModelHealthStatusDao;
import io.swagger.model.AsyncApiDetails;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ulca.benchmark.service.NotificationService;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;

import io.swagger.model.InferenceAPIEndPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class ModelHeartBeatCheckService {

	@Autowired
	ModelDao modelDao;

	@Autowired
	NotificationService notificationService;

	@Autowired
	ModelHealthStatusDao modelHealthStatusDao;

	@Autowired
	ModelInferenceEndPointService modelInferenceEndPointService;

	@Scheduled(cron = "0 0 */6 * * ?")
	public void notifyFailedModelHeartbeatCheck() {

		log.info("*******  start ModelHeartBeatCheckService ::notifyFailedModelHeartbeatCheck ****** ");

		List<String> checkedUrl = new ArrayList<String>();

		List<ModelExtended> list = modelDao.findAll();

		List<ModelExtended> heartBeatFailedModelList = new ArrayList<ModelExtended>();

		for (ModelExtended model : list) {

			try {
				InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();

				if (inferenceAPIEndPoint != null && inferenceAPIEndPoint.getCallbackUrl() != null) {

					if (!inferenceAPIEndPoint.getCallbackUrl().isBlank()
							&& !checkedUrl.contains(inferenceAPIEndPoint.getCallbackUrl())) {
						checkedUrl.add(inferenceAPIEndPoint.getCallbackUrl());
						String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();

						try {
							modelInferenceEndPointService.validateCallBackUrl(inferenceAPIEndPoint);
						} catch (Exception e) {

							heartBeatFailedModelList.add(model);
							log.info("heartBeat Failed modelId : " + model.getModelId() + " modelName : "
									+ model.getName() + " :: " + callBackUrl);
							e.printStackTrace();
						}
					}
				}

			} catch (Exception e) {

				heartBeatFailedModelList.add(model);
				log.info("heartBeat Failed " + model.getName() + " reason :: " + e.getMessage());
				e.printStackTrace();
			}

		}
		if (heartBeatFailedModelList.size() > 0) {
			notificationService.notifyNodelHeartBeatFailure(heartBeatFailedModelList);
		}

		log.info("*******  end ModelHeartBeatCheckService ::notifyFailedModelHeartbeatCheck ****** ");
	}

	@Scheduled(cron = "0 0 */1 * * ?")
	public void modelHeathStatusCheck() {

		log.info("*******  start ModelHeartBeatCheckService ::modelHeathStatusCheck ****** ");


		List<ModelExtended> list = modelDao.findByStatus("published");
		List<ModelHealthStatus> list1 = new ArrayList<>();


		for (ModelExtended model : list) {
			ModelHealthStatus modelHealthStatus = new ModelHealthStatus();
			modelHealthStatus.setModelId(model.getModelId());
			modelHealthStatus.setModelName(model.getName());
			modelHealthStatus.setTaskType(model.getTask().getType().toString());
			modelHealthStatus.setLastStatusUpdate(new Date().toString());
			modelHealthStatus.setNextStatusUpdateTiming(DateUtils.addHours(new Date(),1).toString());


			try {
				InferenceAPIEndPoint inferenceAPIEndPoint = model.getInferenceEndPoint();

				if (inferenceAPIEndPoint != null && inferenceAPIEndPoint.getCallbackUrl() != null) {

					if (!inferenceAPIEndPoint.getCallbackUrl().isBlank()) {
						String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
						modelHealthStatus.setCallbackUrl(callBackUrl);
						modelHealthStatus.setIsSyncApi(inferenceAPIEndPoint.isIsSyncApi());

						if(!inferenceAPIEndPoint.isIsSyncApi()){

							if (inferenceAPIEndPoint.getAsyncApiDetails() != null){
								AsyncApiDetails asyncApiDetails = inferenceAPIEndPoint.getAsyncApiDetails();
								if (asyncApiDetails.getPollingUrl() != null && !asyncApiDetails.getPollingUrl().isBlank()){
									modelHealthStatus.setPollingUrl(asyncApiDetails.getPollingUrl());
								}
							}
						}

						try {
							modelInferenceEndPointService.validateCallBackUrl(inferenceAPIEndPoint);

							modelHealthStatus.setStatus("available");
							list1.add(modelHealthStatus);
						} catch (Exception e) {

							modelHealthStatus.setStatus("unavailable");
							list1.add(modelHealthStatus);

							log.info("healthStatusCheck Failed modelId : " + model.getModelId() + " modelName : "
									+ model.getName() + " :: " + callBackUrl);
							e.printStackTrace();
						}
					}
				}

			} catch (Exception e) {
                modelHealthStatus.setStatus("unavailable");
                list1.add(modelHealthStatus);

                log.info("healthStatusCheck Failed " + model.getName() + " reason :: " + e.getMessage());
				e.printStackTrace();
			}

		}

            modelHealthStatusDao.saveAll(list1);
		log.info("*******  end ModelHeartBeatCheckService ::modelHeathStatusCheck ****** ");
	}


}
