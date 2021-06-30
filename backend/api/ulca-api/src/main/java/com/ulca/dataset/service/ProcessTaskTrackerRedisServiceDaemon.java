package com.ulca.dataset.service;

import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ulca.dataset.dao.TaskTrackerRedisDao;
import com.ulca.dataset.kakfa.DatasetErrorPublishService;
import com.ulca.dataset.model.TaskTracker.ToolEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessTaskTrackerRedisServiceDaemon {

	@Autowired
	ProcessTaskTrackerService processTaskTrackerService;

	@Autowired
	TaskTrackerRedisDao taskTrackerRedisDao;
	
	@Autowired
	DatasetErrorPublishService datasetErrorPublishService;

	@Scheduled(cron = "*/10 * * * * *")
	public void updateTaskTracker() {

		

		Map<String, Map<String, String>> map = taskTrackerRedisDao.findAll();

		for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {


			Map<String, String> val = entry.getValue();

			String serviceRequestNumber = val.containsKey("serviceRequestNumber") ? val.get("serviceRequestNumber") + ""
					: null;
			Integer ingestComplete = val.containsKey("ingestComplete")
					? Integer.parseInt(val.get("ingestComplete") + "")
					: 0;
			Integer count = val.containsKey("count") ? Integer.parseInt(val.get("count") + "") : 0;
			Integer ingestError = val.containsKey("ingestError") ? Integer.parseInt(val.get("ingestError") + "") : 0;
			Integer ingestSuccess = val.containsKey("ingestSuccess") ? Integer.parseInt(val.get("ingestSuccess") + "")
					: 0;
			Integer validateError = val.containsKey("validateError") ? Integer.parseInt(val.get("validateError") + "")
					: 0;
			Integer validateSuccess = val.containsKey("validateSuccess")
					? Integer.parseInt(val.get("validateSuccess") + "")
					: 0;
			Integer publishError = val.containsKey("publishError") ? Integer.parseInt(val.get("publishError") + "") : 0;
			Integer publishSuccess = val.containsKey("publishSuccess")
					? Integer.parseInt(val.get("publishSuccess") + "")
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
				processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.ingest,
						com.ulca.dataset.model.TaskTracker.StatusEnum.completed, details.toString());
			} else {
				
				processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.ingest,
						com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
			}

			proCountSuccess.put("count", validateSuccess);
			proCountFailure.put("count", validateError);

			if (v1 == true && (validateError + validateSuccess >= ingestSuccess)) {
				// update the end time for validate
				v2 = true;
				
				processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber,
						ToolEnum.validate, com.ulca.dataset.model.TaskTracker.StatusEnum.completed,
						details.toString());
			} else {
				if (validateSuccess > 0 || validateError > 0)
					processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.validate,
							com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
			}

			proCountSuccess.put("count", publishSuccess);
			proCountFailure.put("count", publishError);


			if (v2 == true && (publishError + publishSuccess >= validateSuccess)) {
				// update the end time for publish
				v3 = true;
				
				processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.publish,
						com.ulca.dataset.model.TaskTracker.StatusEnum.completed, details.toString());
			} else {
				if (publishSuccess > 0 || publishError > 0)
					processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.publish,
							com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
			}

			if (v1 && v2 && v3) {

				log.info("deleting for serviceRequestNumber :: " + serviceRequestNumber);

				taskTrackerRedisDao.delete(serviceRequestNumber);
				datasetErrorPublishService.publishEofStatus(serviceRequestNumber);
			}

		}

		
	}

}
