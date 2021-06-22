package com.ulca.dataset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.model.TaskTracker;

import java.util.Date;
import java.util.List;

@Service
public class ProcessTaskTrackerService {

	@Autowired
	ProcessTrackerDao processTrackerDao;
	
	
	@Autowired
	TaskTrackerDao taskTrackerDao;

	public ProcessTracker createProcessTracker() {

		ProcessTracker processTracker = new ProcessTracker();

		return processTracker;

	}

	public void updateProcessTracker(String serviceRequestNumber, StatusEnum status) {

		ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
		processTracker.setStatus(status);
		processTrackerDao.save(processTracker);

	}
	
	
	
	public void createTaskTracker(String serviceRequestNumber, ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status) {
		
		TaskTracker taskTracker = new TaskTracker();
		
		taskTracker.serviceRequestNumber(serviceRequestNumber);
		taskTracker.setTool(tool);
		taskTracker.setStatus(status);
		taskTracker.setStartTime(new Date().toString());
		
		taskTrackerDao.save(taskTracker);
		
	}
	public void updateTaskTracker(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status) {
		
		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool);
		if(!taskTrackerList.isEmpty()) {
			TaskTracker taskTracker = taskTrackerList.get(0);
			taskTracker.setEndTime(new Date().toString());
			taskTracker.setStatus(status);
			taskTrackerDao.save(taskTracker);
			
		}
		
	}
	
public void updateTaskTrackerWithDetails(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, String details) {
		
		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool);
		if(!taskTrackerList.isEmpty()) {
			TaskTracker taskTracker = taskTrackerList.get(0);
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status);
			taskTracker.setDetails(details);
			taskTrackerDao.save(taskTracker);
			
		}else {
			TaskTracker taskTracker = new TaskTracker();
			taskTracker.setServiceRequestNumber(serviceRequestNumber);
			taskTracker.setTool(tool);
			taskTracker.setStartTime(new Date().toString());
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status);
			taskTracker.setDetails(details);
			taskTrackerDao.save(taskTracker);
		}
	}

public void updateTaskTrackerWithDetailsAndEndTime(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, String details) {
	
	List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool);
	if(!taskTrackerList.isEmpty()) {
		TaskTracker taskTracker = taskTrackerList.get(0);
		if(taskTracker.getEndTime() == null || taskTracker.getEndTime().isEmpty()) {
			taskTracker.setEndTime(new Date().toString());
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status);
			taskTracker.setDetails(details);
			taskTrackerDao.save(taskTracker);
		}
		
		
	}else {
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.setServiceRequestNumber(serviceRequestNumber);
		taskTracker.setTool(tool);
		taskTracker.setStartTime(new Date().toString());
		taskTracker.setEndTime(new Date().toString());
		taskTracker.setLastModified(new Date().toString());
		taskTracker.setStatus(status);
		taskTracker.setDetails(details);
		taskTrackerDao.save(taskTracker);
	}
}

public void updateTaskTrackerWithError(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, com.ulca.dataset.model.Error error) {
	
	List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool);
	if(!taskTrackerList.isEmpty()) {
		TaskTracker taskTracker = taskTrackerList.get(0);
		taskTracker.setEndTime(new Date().toString());
		taskTracker.setLastModified(new Date().toString());
		taskTracker.setStatus(status);
		taskTracker.setError(error);
		taskTrackerDao.save(taskTracker);
		
	}else {
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.setServiceRequestNumber(serviceRequestNumber);
		taskTracker.setTool(tool);
		taskTracker.setStartTime(new Date().toString());
		taskTracker.setEndTime(new Date().toString());
		taskTracker.setLastModified(new Date().toString());
		taskTracker.setStatus(status);
		taskTracker.setError(error);
		taskTrackerDao.save(taskTracker);
	}
}
}
