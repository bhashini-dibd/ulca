package com.ulca.dataset.service;

import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProcessTaskTrackerService {

	@Autowired
	ProcessTrackerDao processTrackerDao;
	
	
	@Autowired
	TaskTrackerDao taskTrackerDao;



	public void updateProcessTracker(String serviceRequestNumber, StatusEnum status) {

		ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
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
		
		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
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
	
	List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
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


}
