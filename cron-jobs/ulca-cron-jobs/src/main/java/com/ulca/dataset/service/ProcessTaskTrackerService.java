package com.ulca.dataset.service;

import com.ulca.dataset.dao.ProcessTrackerDao;

import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.ProcessTrackerDto;
import com.ulca.dataset.model.ProcessTrackerDto.ServiceRequestActionEnum;
import com.ulca.dataset.model.ProcessTrackerDto.ServiceRequestTypeEnum;
import com.ulca.model.cronjobs.ModelHeartBeatCheckService;

import lombok.extern.slf4j.Slf4j;

import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.TaskTrackerDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProcessTaskTrackerService {

	@Autowired
	ProcessTrackerDao processTrackerDao;
	
	
	@Autowired
	TaskTrackerDao taskTrackerDao;



	public void updateProcessTracker(String serviceRequestNumber, StatusEnum status) {
		
		log.info("inside updateProcessTracker for serviceRequestNumber : "+serviceRequestNumber);
		

		ProcessTrackerDto processTrackerDto = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
		ProcessTracker processTracker = new ProcessTracker();
		processTracker.setDatasetId(processTrackerDto.getDatasetId());
		processTracker.setDetails(processTrackerDto.getDetails());
		processTracker.setId(processTrackerDto.getId());
		processTracker.setError(processTrackerDto.getError());
		processTracker.setSearchCriterion(processTrackerDto.getSearchCriterion());
		processTracker.setUserId(processTrackerDto.getUserId());
		processTracker.setStatus(processTrackerDto.getStatus());
		processTracker.setServiceRequestNumber(processTrackerDto.getServiceRequestNumber());
		
		ServiceRequestActionEnum serviceRequestActionEnumDto= processTrackerDto.getServiceRequestAction();
		log.info("serviceRequestActionEnumDto.name() : "+serviceRequestActionEnumDto.name());
		com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum  serviceRequestActionEnum 
		=com.ulca.dataset.model.ProcessTracker.ServiceRequestActionEnum.fromValue(serviceRequestActionEnumDto.name());
		processTracker.setServiceRequestAction(serviceRequestActionEnum);
		
		ServiceRequestTypeEnum serviceRequestTypeEnumDto= processTrackerDto.getServiceRequestType();
		com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum  serviceRequestTypeEnum 
		=com.ulca.dataset.model.ProcessTracker.ServiceRequestTypeEnum.fromValue(serviceRequestTypeEnumDto.name());
		processTracker.setServiceRequestType(serviceRequestTypeEnum);
		
		processTracker.setStartTime(convetTimeToLong(processTrackerDto.getStartTime()));
		processTracker.setEndTime(convetTimeToLong(processTrackerDto.getEndTime()));
		processTracker.setLastModified(convetTimeToLong(processTrackerDto.getLastModified()));
	
		
		
		
		
		
		
		processTracker.setStatus(status.toString());
		
		if(!(processTracker.getStatus().equals(ProcessTracker.StatusEnum.completed.toString())
				|| processTracker.getStatus().equals(ProcessTracker.StatusEnum.failed.toString())
				)) {
			processTracker.setStatus(status.toString());
			
			if(status == StatusEnum.completed || status == StatusEnum.failed) {
				processTracker.setEndTime(Instant.now().toEpochMilli());
			}
			
		}
		
		
		processTrackerDao.save(processTracker);

	}
	
	
	

	
public void updateTaskTrackerWithDetails(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, String details) {
	 log.info("**************************inside updateTaskTrackerWithDetails for serviceRequestNumber"+serviceRequestNumber);
	      
	 
	 
	   List<TaskTrackerDto> taskTrackerDtoList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
	
	   List<TaskTracker> taskTrackerList = mapDtoListToTaskTracker(taskTrackerDtoList);
	   
	 
	   
		if(!taskTrackerList.isEmpty()) {
			
			TaskTracker taskTracker = taskTrackerList.get(0);
			taskTracker.setLastModified(Instant.now().toEpochMilli());
			taskTracker.setDetails(details);
			
			if(!(taskTracker.getStatus().equals(TaskTracker.StatusEnum.completed.toString())
					|| taskTracker.getStatus().equals(TaskTracker.StatusEnum.failed.toString())
					)) {
				taskTracker.setStatus(status.toString());
				
			}
			taskTrackerDao.save(taskTracker);
			
			
		}else {
			TaskTracker taskTracker = new TaskTracker();
			taskTracker.setServiceRequestNumber(serviceRequestNumber);
			taskTracker.setTool(tool.toString());
			taskTracker.setStartTime(Instant.now().toEpochMilli());
			taskTracker.setLastModified(Instant.now().toEpochMilli());
			taskTracker.setStatus(status.toString());
			taskTracker.setDetails(details);
			taskTrackerDao.save(taskTracker);
		}
	}

public void updateTaskTrackerWithDetailsAndEndTime(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, String details) {
	   log.info("**************************inside updateTaskTrackerWithDetailsAndEndTime for serviceRequestNumber : "+serviceRequestNumber);

	   List<TaskTrackerDto> taskTrackerDtoList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
	   List<TaskTracker> taskTrackerList = mapDtoListToTaskTracker(taskTrackerDtoList);

	if(!taskTrackerList.isEmpty()) {
		TaskTracker taskTracker = taskTrackerList.get(0);
		if(taskTracker.getEndTime() == null || taskTracker.getEndTime() == 0 ) {
			
			taskTracker.setEndTime(Instant.now().toEpochMilli());
			taskTracker.setLastModified(Instant.now().toEpochMilli());
			
			if(!(taskTracker.getStatus().equals(TaskTracker.StatusEnum.completed.toString())
					|| taskTracker.getStatus().equals(TaskTracker.StatusEnum.failed.toString())
					)) {
				taskTracker.setStatus(status.toString());
				
			}
			
			taskTracker.setDetails(details);
			taskTrackerDao.save(taskTracker);
		}
		
	}else {
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.setServiceRequestNumber(serviceRequestNumber);
		taskTracker.setTool(tool.toString());
		taskTracker.setStartTime(Instant.now().toEpochMilli());
		taskTracker.setEndTime(Instant.now().toEpochMilli());
		taskTracker.setLastModified(Instant.now().toEpochMilli());
		taskTracker.setStatus(status.toString());
		taskTracker.setDetails(details);
		taskTrackerDao.save(taskTracker);
	}
}


private static Long convetTimeToLong(Object time) {
	 
	 Long convertedTime=null;
	 
     if(time!=null) {
	 
	 if(time instanceof Long){
		  
		 convertedTime = (Long) time;
	  }else if(time instanceof Date){
		  Date date = (Date)time;
		Long epoch = date.getTime();
  	convertedTime = epoch;

	  }else if (time instanceof String){
		  String s = (String)time;
		  boolean result = s.matches("[0-9]+");
		     if(result) {
		    	 Long varLong=Long.parseLong(s);
		    	convertedTime = varLong;

		     }else {
		    	

		    	SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC' yyyy");
	    	Date date = null;
			try {
				date = (Date) df.parse(s);
			} catch (ParseException e) {
				log.info("Format of date is not expected.");
				e.printStackTrace();
			}
	    	Long epoch = date.getTime();
	    	convertedTime = epoch;
		    	 
		     }
		  
		  
		  
	  }}
	 return convertedTime;
}



           public static List<TaskTracker> mapDtoListToTaskTracker(List<TaskTrackerDto> taskTrackerDtoList){
        	   
        	   List<TaskTracker> taskTrackerList = new ArrayList<TaskTracker>();
        	   for(TaskTrackerDto taskTrackerDto:taskTrackerDtoList) {
        		   TaskTracker taskTracker = new TaskTracker();
        		   taskTracker.setServiceRequestNumber(taskTrackerDto.getServiceRequestNumber());
        		   taskTracker.setTool(taskTrackerDto.getTool());
        		   taskTracker.setDetails(taskTrackerDto.getDetails());
        		   taskTracker.setStatus(taskTrackerDto.getStatus());
        		   taskTracker.setError(taskTrackerDto.getError());
        		   taskTracker.setStartTime(convetTimeToLong(taskTrackerDto.getStartTime()));
        		   taskTracker.setEndTime(convetTimeToLong(taskTrackerDto.getEndTime()));
        		   taskTracker.setLastModified(convetTimeToLong(taskTrackerDto.getLastModified()));
        		   taskTrackerList.add(taskTracker);
        	   }
        	   
        	   return taskTrackerList;
        	   
           }





}
