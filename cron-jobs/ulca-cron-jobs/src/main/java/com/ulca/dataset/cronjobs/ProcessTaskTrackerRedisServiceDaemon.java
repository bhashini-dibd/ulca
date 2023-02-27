package com.ulca.dataset.cronjobs;

import com.ulca.dataset.constants.DatasetConstants;
import com.ulca.dataset.dao.TaskTrackerRedisDao;
import com.ulca.dataset.kakfa.model.DatasetIngest;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.TaskTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.service.DatasetFileService;
import com.ulca.dataset.service.NotificationService;
import com.ulca.dataset.service.ProcessTaskTrackerService;

import io.swagger.model.DatasetType;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class ProcessTaskTrackerRedisServiceDaemon {

	@Autowired
	ProcessTaskTrackerService processTaskTrackerService;

	@Autowired
	TaskTrackerRedisDao taskTrackerRedisDao;

	@Autowired
	private KafkaTemplate<String, DatasetIngest> datasetIngestKafkaTemplate;

	@Value("${kafka.ulca.ds.ingest.ip.topic}")
	private String datasetIngestTopic;
	
	@Value("${pseudo.ingest.success.percentage.threshold}")
	private Integer successThreshold;
	

	@Autowired
	DatasetFileService datasetFileService;
	
	@Autowired
	NotificationService notificationService;
	
	//@Scheduled(cron = "*/10 * * * * *")
	@Scheduled(cron = "0 */1 * * * *")
	public void updateTaskTracker() {
		
		log.info("******************start dataset cron job for updateTaskTracker*****************************");

		Map<String, Map<String, String>> map = taskTrackerRedisDao.findAll();
				
		for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {

			try {

				Map<String, String> val = entry.getValue();

				String mode = val.containsKey("mode") ? val.get("mode") + "" : null;
				if (mode.equalsIgnoreCase("real")) {
					realIngestUpdate(val);
				} else {
					pseudoIngestUpdate(val);
				}

			} catch (Exception e) {
				log.info("Exception while processing the fetched Redis map data :: " + entry.toString());
				e.printStackTrace();
			}

		}

	}

	public void pseudoIngestUpdate(Map<String, String> val) {
		
		String serviceRequestNumber = val.containsKey("serviceRequestNumber") ? val.get("serviceRequestNumber") + ""
				: null;
		String datasetType = val.containsKey("datasetType") ? val.get("datasetType") + ""
				: null;
		String datasetName = val.containsKey("datasetName") ? val.get("datasetName") + ""
				: null;
		String datasetId = val.containsKey("datasetId") ? val.get("datasetId") + ""
				: null;
		String userId = val.containsKey("userId") ? val.get("userId") + ""
				: null;

		String baseLocation = val.containsKey("baseLocation") ? val.get("baseLocation") + "" : null;
		String md5hash = val.containsKey("md5hash") ? val.get("md5hash") + "" : null;
		Integer ingestComplete = val.containsKey("ingestComplete") ? Integer.parseInt(val.get("ingestComplete") + "")
				: 0;
		Integer count = val.containsKey("count") ? Integer.parseInt(val.get("count") + "") : 0;
		Integer ingestError = val.containsKey("ingestError") ? Integer.parseInt(val.get("ingestError") + "") : 0;
		Integer ingestSuccess = val.containsKey("ingestSuccess") ? Integer.parseInt(val.get("ingestSuccess") + "") : 0;
		Integer validateError = val.containsKey("validateError") ? Integer.parseInt(val.get("validateError") + "") : 0;
		Integer validateSuccess = val.containsKey("validateSuccess") ? Integer.parseInt(val.get("validateSuccess") + "")
				: 0;
		Integer publishError = val.containsKey("publishError") ? Integer.parseInt(val.get("publishError") + "") : 0;
		Integer publishSuccess = val.containsKey("publishSuccess") ? Integer.parseInt(val.get("publishSuccess") + "")
				: 0;

		boolean v1 = false;
		boolean v2 = false;
		boolean v3 = false;
		
		JSONObject details = new JSONObject();

		JSONObject ingestDetails = new JSONObject();
		JSONArray ingestProcessedCount = new JSONArray();
		JSONObject ingestProCountSuccess = new JSONObject();
		ingestProCountSuccess.put("type", "success");
		ingestProCountSuccess.put("count", ingestSuccess);
		ingestProcessedCount.put(ingestProCountSuccess);
		JSONObject ingestProCountFailure = new JSONObject();
		ingestProCountFailure.put("type", "failed");
		ingestProCountFailure.put("count", ingestError);
		ingestProcessedCount.put(ingestProCountFailure);
		ingestDetails.put("processedCount", ingestProcessedCount);

		details.put("timeStamp", new Date().toString());
		details.put("ingest", ingestDetails);

		JSONObject validateDetails = new JSONObject();
		JSONArray validateProcessedCount = new JSONArray();
		JSONObject validateProCountSuccess = new JSONObject();
		validateProCountSuccess.put("type", "success");
		validateProCountSuccess.put("count", validateSuccess);
		validateProcessedCount.put(validateProCountSuccess);
		JSONObject validateProCountFailure = new JSONObject();
		validateProCountFailure.put("type", "failed");
		validateProCountFailure.put("count", validateError);
		validateProcessedCount.put(validateProCountFailure);
		validateDetails.put("processedCount", validateProcessedCount);
		details.put("validate", validateDetails);

		JSONObject publishDetails = new JSONObject();
		JSONArray publishProcessedCount = new JSONArray();
		JSONObject publishProCountSuccess = new JSONObject();
		publishProCountSuccess.put("type", "success");
		publishProCountSuccess.put("count", publishSuccess);
		publishProcessedCount.put(publishProCountSuccess);
		JSONObject publishProCountFailure = new JSONObject();
		publishProCountFailure.put("type", "failed");
		publishProCountFailure.put("count", publishError);
		publishProcessedCount.put(publishProCountFailure);
		publishDetails.put("processedCount", publishProcessedCount);
		details.put("publish", publishDetails);

		if (ingestComplete == 1 && (ingestSuccess + ingestError == count)) {
			// pseudo ingest complete
			v1 = true;
		}

		if (v1 == true && (validateError + validateSuccess >= ingestSuccess)) {
			// pseudo validate complete
			v2 = true;
		}

		if (v2 == true && (publishError + publishSuccess >= validateSuccess)) {
			// pseudo publish complete
			v3 = true;

		}
		
		
		if (v1 && v2 && v3) {

			StatusEnum taskStatus = StatusEnum.completed;
			
			log.info("deleting redis entry for pseudo ingest : serviceRequestNumber :: " + serviceRequestNumber);
			taskTrackerRedisDao.delete(serviceRequestNumber);
			
			double successRate = ((double)publishSuccess/(double)count)*100 ;
			
			log.info("serviceRequestNumber :: " + serviceRequestNumber + " success rate :: " + successRate);
			
			if(successRate <= successThreshold) {
				
				log.info(" pseudo ingest failed for serviceRequestNumber :: " + serviceRequestNumber + " due to success rate less than " + successThreshold);
				taskStatus = com.ulca.dataset.model.TaskTracker.StatusEnum.failed;
				processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.precheck,
						taskStatus, details.toString());
				
			}else {
				log.info("pseudo ingest success for serviceRequestNumber :: " + serviceRequestNumber + ". Real Ingest is being Triggered");
				
				processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.precheck,
						taskStatus, details.toString());
				
				DatasetIngest datasetIngest = new DatasetIngest();
				datasetIngest.setServiceRequestNumber(serviceRequestNumber);
				datasetIngest.setDatasetType(DatasetType.fromValue(datasetType));
				datasetIngest.setDatasetName(datasetName);
				datasetIngest.setDatasetId(datasetId);
				datasetIngest.setUserId(userId);
				datasetIngest.setMode(DatasetConstants.INGEST_REAL_MODE);
				datasetIngest.setBaseLocation(baseLocation);
				datasetIngest.setMd5hash(md5hash);
				
				datasetIngestKafkaTemplate.send(datasetIngestTopic, datasetIngest);
				//datasetIngestKafkaTemplate.send(datasetIngestTopic,0,null, datasetIngest);
			}

		} else {
			processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.precheck,
					com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
		}

	}

	public void realIngestUpdate(Map<String, String> val) {

		String serviceRequestNumber = val.containsKey("serviceRequestNumber") ? val.get("serviceRequestNumber") + ""
				: null;

		Integer ingestComplete = val.containsKey("ingestComplete") ? Integer.parseInt(val.get("ingestComplete") + "")
				: 0;
		Integer count = val.containsKey("count") ? Integer.parseInt(val.get("count") + "") : 0;
		Integer ingestError = val.containsKey("ingestError") ? Integer.parseInt(val.get("ingestError") + "") : 0;
		Integer ingestSuccess = val.containsKey("ingestSuccess") ? Integer.parseInt(val.get("ingestSuccess") + "") : 0;
		Integer validateError = val.containsKey("validateError") ? Integer.parseInt(val.get("validateError") + "") : 0;
		Integer validateSuccess = val.containsKey("validateSuccess") ? Integer.parseInt(val.get("validateSuccess") + "")
				: 0;
		Integer publishError = val.containsKey("publishError") ? Integer.parseInt(val.get("publishError") + "") : 0;
		Integer publishSuccess = val.containsKey("publishSuccess") ? Integer.parseInt(val.get("publishSuccess") + "")
				: 0;

		boolean v1 = false;
		boolean v2 = false;
		boolean v3 = false;

		JSONObject details = new JSONObject();

		JSONArray processedCount = new JSONArray();

		JSONObject proCountSuccess = new JSONObject();
		proCountSuccess.put("type", "success");
		proCountSuccess.put("count", ingestSuccess);
		processedCount.put(proCountSuccess);

		JSONObject proCountFailure = new JSONObject();

		proCountFailure.put("type", "failed");
		proCountFailure.put("count", ingestError);
		processedCount.put(proCountFailure);
		details.put("processedCount", processedCount);
		details.put("timeStamp", new Date().toString());

		if (ingestComplete == 1 && (ingestSuccess + ingestError == count)) {
			// update the end time for ingest
			v1 = true;
			if(ingestSuccess==0) {
			processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed, details.toString());
			}else {
				
				processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.ingest,
						com.ulca.dataset.model.TaskTracker.StatusEnum.completed, details.toString());
			}

			

		} else {

			processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
		}
		
		
		
		if(val.containsKey("validateSuccessSeconds")) {
			proCountSuccess.put("validateSuccessSeconds", val.get("validateSuccessSeconds"));
		}
		if(val.containsKey("validateErrorSeconds")) {
			proCountFailure.put("validateErrorSeconds", val.get("validateErrorSeconds"));
		}
		proCountSuccess.put("count", validateSuccess);
		proCountFailure.put("count", validateError);

		if (v1 == true && (validateError + validateSuccess >= ingestSuccess) && (ingestSuccess > 0) ) {
			// update the end time for validate
			v2 = true;

			processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.validate,
					com.ulca.dataset.model.TaskTracker.StatusEnum.completed, details.toString());
		} else {
			if (validateSuccess > 0 || validateError > 0)
				processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.validate,
						com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
		}

		if(val.containsKey("publishSuccessSeconds")) {
			proCountSuccess.put("publishSuccessSeconds", val.get("publishSuccessSeconds"));
		}
		if(val.containsKey("publishErrorSeconds")) {
			proCountFailure.put("publishErrorSeconds", val.get("publishErrorSeconds"));
		}
		//remove the values for validateSuccessSeconds and validateErrorSeconds
		if(proCountSuccess.has("validateSuccessSeconds")) {
			proCountSuccess.remove("validateSuccessSeconds");
		}
		if(proCountFailure.has("validateErrorSeconds")) {
			proCountFailure.remove("validateErrorSeconds");
		}
		
		proCountSuccess.put("count", publishSuccess);
		proCountFailure.put("count", publishError);

		if (v2 == true && (publishError + publishSuccess >= validateSuccess) && (validateSuccess > 0)) {
			// update the end time for publish
			v3 = true;

			processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.publish,
					com.ulca.dataset.model.TaskTracker.StatusEnum.completed, details.toString());
		} else {
			if (publishSuccess > 0 || publishError > 0)
				processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.publish,
						com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
		}

		if ((v1 && v2 && v3)||(v1 && ingestSuccess==0 )) {

			log.info("deleting redis entry for real ingest : serviceRequestNumber :: " + serviceRequestNumber);
			log.info("serviceRequestNumber :: " + serviceRequestNumber + "ingestSuccess : "+ ingestSuccess + " ingestError : " + ingestError + " validateSuccess : " + validateSuccess + " validateError : "+ validateError + " publishSuccess : " + publishSuccess + " publishError : "+ publishError);
			taskTrackerRedisDao.delete(serviceRequestNumber);
			
			if(v1 && ingestSuccess==0) {
			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, ProcessTracker.StatusEnum.failed);
			}else {
				
				processTaskTrackerService.updateProcessTracker(serviceRequestNumber, ProcessTracker.StatusEnum.completed);
			}
			String datasetName = val.get("datasetName");
			String userId = val.get("userId");
			log.info("sending dataset submit completion notification to User. userId : " + userId + " datasetName : " + datasetName + " serviceRequestNumber : " + serviceRequestNumber);
			
			notificationService.notifyDatasetComplete(serviceRequestNumber, datasetName, userId);
			
			//upload submitted-datasets file to object store and delete the file
			datasetFileService.datasetAfterIngestCleanJob(serviceRequestNumber);
		}

	}

}
