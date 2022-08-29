package com.ulca.dataset.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.ulca.dataset.model.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.TaskTracker.ToolEnum;

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

	public ProcessTracker updateProcessTracker(String serviceRequestNumber, StatusEnum status) {
		ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
		processTracker.setStatus(status.toString());
		if(!(processTracker.getStatus().equals(ProcessTracker.StatusEnum.completed.toString())
				|| processTracker.getStatus().equals(ProcessTracker.StatusEnum.failed.toString()))) {
			processTracker.setStatus(status.toString());
			if(status == StatusEnum.completed || status == StatusEnum.failed) {
				processTracker.setEndTime(Instant.now().toEpochMilli());}
		}
		
		return processTrackerDao.save(processTracker);

	}
	
	
	
	public TaskTracker createTaskTracker(String serviceRequestNumber, ToolEnum tool, TaskTracker.StatusEnum status) {
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.serviceRequestNumber(serviceRequestNumber);
		taskTracker.setTool(tool.toString());
		taskTracker.setStatus(status.toString());
		taskTracker.setStartTime(Instant.now().toEpochMilli());
		return taskTrackerDao.save(taskTracker);}
	public TaskTracker updateTaskTracker(String serviceRequestNumber, ToolEnum tool, TaskTracker.StatusEnum status) {
		
		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
		TaskTracker taskTracker1;
		if(!taskTrackerList.isEmpty()) {
			TaskTracker taskTracker = taskTrackerList.get(0);
			if(status == TaskTracker.StatusEnum.completed || status == TaskTracker.StatusEnum.failed) {
				taskTracker.setEndTime(Instant.now().toEpochMilli());
			}
			taskTracker.setStatus(status.toString());
			taskTracker1 = taskTrackerDao.save(taskTracker);
			return taskTracker1;
		}
		return null;
	}
	
public TaskTracker updateTaskTrackerWithDetails(String serviceRequestNumber, ToolEnum tool, TaskTracker.StatusEnum status, String details) {
		
		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
		TaskTracker taskTracker1 = null;

		if(!taskTrackerList.isEmpty()) {
			
			TaskTracker taskTracker = taskTrackerList.get(0);
			taskTracker.setLastModified(Instant.now().toEpochMilli());
			taskTracker.setDetails(details);
			
			if(!(taskTracker.getStatus().equals(TaskTracker.StatusEnum.completed.toString())
					|| taskTracker.getStatus().equals(TaskTracker.StatusEnum.failed.toString())
					)) {
				taskTracker.setStatus(status.toString());
				
			}
		taskTracker1 =	taskTrackerDao.save(taskTracker);
			
			
		}else {
			TaskTracker taskTracker = new TaskTracker();
			taskTracker.setServiceRequestNumber(serviceRequestNumber);
			taskTracker.setTool(tool.toString());
			taskTracker.setStartTime(Instant.now().toEpochMilli());
			taskTracker.setLastModified(Instant.now().toEpochMilli());
			taskTracker.setStatus(status.toString());
			taskTracker.setDetails(details);
		taskTracker1 = taskTrackerDao.save(taskTracker);
		}
	return taskTracker1;
}

public TaskTracker updateTaskTrackerWithDetailsAndEndTime(String serviceRequestNumber, ToolEnum tool, TaskTracker.StatusEnum status, String details) {
	
	List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
	TaskTracker taskTracker1 = null;
	if(!taskTrackerList.isEmpty()) {
		TaskTracker taskTracker = taskTrackerList.get(0);
		if(taskTracker.getEndTime() == 0 ) {
			
			taskTracker.setEndTime(Instant.now().toEpochMilli());
			taskTracker.setLastModified(Instant.now().toEpochMilli());
			
			if(!(taskTracker.getStatus().equals(TaskTracker.StatusEnum.completed.toString())
					|| taskTracker.getStatus().equals(TaskTracker.StatusEnum.failed.toString())
					)) {
				taskTracker.setStatus(status.toString());
				
			}
			
			taskTracker.setDetails(details);
		taskTracker1 =	taskTrackerDao.save(taskTracker);
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
		taskTracker1 = taskTrackerDao.save(taskTracker);
	}
	return taskTracker1;
}

public TaskTracker updateTaskTrackerWithError(String serviceRequestNumber, ToolEnum tool, TaskTracker.StatusEnum status, Error error) {
	
	List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
	TaskTracker taskTracker1 = null;
	if(!taskTrackerList.isEmpty()) {
		TaskTracker taskTracker = taskTrackerList.get(0);
		taskTracker.setLastModified(Instant.now().toEpochMilli());
		taskTracker.setStatus(status.toString());
		taskTracker.setError(error);
		taskTracker1 = taskTrackerDao.save(taskTracker);
		
	}else {
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.setServiceRequestNumber(serviceRequestNumber);
		taskTracker.setTool(tool.toString());
		taskTracker.setStartTime(Instant.now().toEpochMilli());
		taskTracker.setLastModified(Instant.now().toEpochMilli());
		taskTracker.setStatus(status.toString());
		taskTracker.setError(error);
		taskTracker1 = taskTrackerDao.save(taskTracker);
	}
	return taskTracker1;
}

public TaskTracker updateTaskTrackerWithErrorAndEndTime(String serviceRequestNumber, ToolEnum tool, TaskTracker.StatusEnum status, Error error) {
	
	List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
	TaskTracker taskTracker1 = null;
	if(!taskTrackerList.isEmpty()) {
		TaskTracker taskTracker = taskTrackerList.get(0);
		taskTracker.setEndTime(Instant.now().toEpochMilli());
		taskTracker.setLastModified(Instant.now().toEpochMilli());
		taskTracker.setStatus(status.toString());
		taskTracker.setError(error);
		taskTracker1 = taskTrackerDao.save(taskTracker);
		
	}else {
		TaskTracker taskTracker = new TaskTracker();
		taskTracker.setServiceRequestNumber(serviceRequestNumber);
		taskTracker.setTool(tool.toString());
		taskTracker.setStartTime(Instant.now().toEpochMilli());
		taskTracker.setEndTime(Instant.now().toEpochMilli());
		taskTracker.setLastModified(Instant.now().toEpochMilli());
		taskTracker.setStatus(status.toString());
		taskTracker.setError(error);
		taskTracker1 =taskTrackerDao.save(taskTracker);
	}
	return taskTracker1;
}
}
