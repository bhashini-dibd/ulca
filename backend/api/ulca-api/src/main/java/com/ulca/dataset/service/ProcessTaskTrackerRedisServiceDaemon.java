package com.ulca.dataset.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ulca.dataset.dao.TaskTrackerRedisDao;
import com.ulca.dataset.dao.TaskTrackerRedisRepository;
import com.ulca.dataset.model.TaskTrackerRedis;
import com.ulca.dataset.model.TaskTracker.ToolEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessTaskTrackerRedisServiceDaemon {
	
	@Autowired
	TaskTrackerRedisRepository taskTrackerRedisRepository;
	
	@Autowired
	ProcessTaskTrackerService processTaskTrackerService;
	
	@Autowired
	TaskTrackerRedisDao taskTrackerRedisDao;

	
	@Scheduled(cron = "*/10 * * * * *")
	public void updateTaskTracker() {
		
		// will give us the current time and date
	    LocalDateTime current = LocalDateTime.now();
	    log.info("current date and time : "+
	                        current);
	    
		// to print in a particular format
	    DateTimeFormatter format = 
	      DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
	    
	    String formatedDateTime = current.format(format);  
	     
	    log.info("in foramatted manner "+
	                        formatedDateTime);
	    
	    
	    
	    List<TaskTrackerRedis> list = (List<TaskTrackerRedis>) taskTrackerRedisRepository.findAll();
	    
	   
	            log.info("size of objects :: " + list.size());
	    
	    for(TaskTrackerRedis taskTrackerRedis : list) {
	    	
	    	log.info("serviceRequestNumber");
	    	
	    	String serviceRequestNumber = taskTrackerRedis.getServiceRequestNumber();
	    	log.info(taskTrackerRedis.getServiceRequestNumber());
	    	
	    	log.info("total count :: " + taskTrackerRedis.getCount()+"");
	    	log.info("ingest success ");
	    	log.info(taskTrackerRedis.getIngestSuccess()+"");
	    	
	    	log.info("ingest error ");
	    	log.info(taskTrackerRedis.getIngestError()+"");
	    	
	    	log.info("validate success");
	    	log.info(taskTrackerRedis.getValidateSuccess()+"");
	    	
	    	log.info("validate error");
	    	log.info(taskTrackerRedis.getValidateError()+"");
	    	
	    	log.info("publish error");
	    	log.info(taskTrackerRedis.getPublishError()+"");
	    	
	    	log.info("publish success");
	    	log.info(taskTrackerRedis.getPublishSuccess()+"");
	    	
	    	
	    	
	    	boolean v1 = false;
	    	boolean v2 = false;
	    	boolean v3 = false;
	    	
	    	
	    	
	    	JSONObject details = new JSONObject();

			JSONArray processedCount = new JSONArray();

			JSONObject proCountSuccess = new JSONObject();
			proCountSuccess.put("type", "success");
			proCountSuccess.put("count", taskTrackerRedis.getIngestSuccess());
			processedCount.put(proCountSuccess);

			JSONObject proCountFailure = new JSONObject();

			proCountFailure.put("type", "failed");
			proCountFailure.put("count", taskTrackerRedis.getIngestError());
			processedCount.put(proCountFailure);
			details.put("processedCount", processedCount);
			details.put("timeStamp", new Date().toString());
			
			System.out.println("************* ingest *********");
			log.info(details.toString());
			if(taskTrackerRedis.getIngestComplete() == 1 && (taskTrackerRedis.getIngestSuccess() + taskTrackerRedis.getIngestError() == taskTrackerRedis.getCount())) {
	    		//update the end time for ingest
	    		v1 = true;
	    		log.info("updating end time");
	    		processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.ingest,
						com.ulca.dataset.model.TaskTracker.StatusEnum.successful, details.toString());
	    	}else {
	    		log.info("not updating end time");
	    		processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.ingest,
						com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
	    	}
			
			System.out.println("************* validated*********");
			proCountSuccess.put("count", taskTrackerRedis.getValidateSuccess());
			proCountFailure.put("count", taskTrackerRedis.getValidateError());
			log.info(details.toString());
			
	    	if(v1 == true && (taskTrackerRedis.getValidateError() + taskTrackerRedis.getValidateSuccess() == taskTrackerRedis.getIngestSuccess())) {
	    		//update the end time for validate
	    		v2 = true;
	    		log.info("updating end time validate");
	    		processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.validate,
						com.ulca.dataset.model.TaskTracker.StatusEnum.successful, details.toString());
	    	}else {
	    		if(taskTrackerRedis.getValidateSuccess() > 0 || taskTrackerRedis.getValidateError() > 0)
	    		processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.validate,
						com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());
	    	}
	    	
	    	log.info("************* published *********");
	    	proCountFailure.put("count", taskTrackerRedis.getPublishError());
			proCountSuccess.put("count", taskTrackerRedis.getPublishSuccess());
			log.info(details.toString());
			
	    	if(v2 == true && (taskTrackerRedis.getPublishError() + taskTrackerRedis.getPublishSuccess() == taskTrackerRedis.getValidateSuccess())) {
	    		//update the end time for publish
	    		v3 = true;
	    		log.info("updating end time for publish");
	    		processTaskTrackerService.updateTaskTrackerWithDetailsAndEndTime(serviceRequestNumber, ToolEnum.publish,
						com.ulca.dataset.model.TaskTracker.StatusEnum.successful, details.toString());
	    	}else {
	    		if(taskTrackerRedis.getPublishSuccess() > 0 || taskTrackerRedis.getPublishError() > 0)
	    		processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.publish,
						com.ulca.dataset.model.TaskTracker.StatusEnum.successful, details.toString());
	    	}
	    	
	    	
	    	if(v1 && v2 && v3) {
	    		
	    		
	    		log.info("deleting for serviceRequestNumber :: " + serviceRequestNumber);
	    		
	    		taskTrackerRedisRepository.delete(taskTrackerRedis);
	    	}
	    	
	    	
	    }
	    
	    

	    
	}

}




