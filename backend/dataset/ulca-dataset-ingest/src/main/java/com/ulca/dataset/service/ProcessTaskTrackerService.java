package com.ulca.dataset.service;

import java.util.Date;
import java.util.List;

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
				|| processTracker.getStatus().equals(ProcessTracker.StatusEnum.failed.toString())
		)) {
			processTracker.setStatus(status.toString());

			if(status == StatusEnum.completed || status == StatusEnum.failed) {
				processTracker.setEndTime(new Date().toString());
			}

		}


		return processTrackerDao.save(processTracker);

	}



	public TaskTracker createTaskTracker(String serviceRequestNumber, ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status) {

		TaskTracker taskTracker = new TaskTracker();

		taskTracker.serviceRequestNumber(serviceRequestNumber);
		taskTracker.setTool(tool.toString());
		taskTracker.setStatus(status.toString());
		taskTracker.setStartTime(new Date().toString());

		return taskTrackerDao.save(taskTracker);

	}
	public TaskTracker updateTaskTracker(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status) {
		TaskTracker taskTracker1 = new TaskTracker();
		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
		if(!taskTrackerList.isEmpty()) {
			TaskTracker taskTracker = taskTrackerList.get(0);
			if(status == TaskTracker.StatusEnum.completed || status == TaskTracker.StatusEnum.failed) {
				taskTracker.setEndTime(new Date().toString());
			}
			taskTracker.setStatus(status.toString());
			taskTracker1 =	taskTrackerDao.save(taskTracker);

		}
		return taskTracker1;
	}

	public TaskTracker updateTaskTrackerWithDetails(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, String details) {
		TaskTracker taskTracker1 = new TaskTracker();

		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
		if(!taskTrackerList.isEmpty()) {

			TaskTracker taskTracker = taskTrackerList.get(0);
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setDetails(details);

			if(!(taskTracker.getStatus().equals(TaskTracker.StatusEnum.completed.toString())
					|| taskTracker.getStatus().equals(TaskTracker.StatusEnum.failed.toString())
			)) {
				taskTracker.setStatus(status.toString());

			}
			taskTracker1 = taskTrackerDao.save(taskTracker);


		}else {
			TaskTracker taskTracker = new TaskTracker();
			taskTracker.setServiceRequestNumber(serviceRequestNumber);
			taskTracker.setTool(tool.toString());
			taskTracker.setStartTime(new Date().toString());
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status.toString());
			taskTracker.setDetails(details);
			taskTracker1 = taskTrackerDao.save(taskTracker);
		}
		return taskTracker1;
	}

	public TaskTracker updateTaskTrackerWithDetailsAndEndTime(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, String details) {
		TaskTracker taskTracker1 = new TaskTracker();

		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
		if(!taskTrackerList.isEmpty()) {
			TaskTracker taskTracker = taskTrackerList.get(0);
			if(taskTracker.getEndTime() == null || taskTracker.getEndTime().isEmpty()) {

				taskTracker.setEndTime(new Date().toString());
				taskTracker.setLastModified(new Date().toString());

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
			taskTracker.setStartTime(new Date().toString());
			taskTracker.setEndTime(new Date().toString());
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status.toString());
			taskTracker.setDetails(details);
			taskTracker1 = taskTrackerDao.save(taskTracker);
		}
		return taskTracker1;
	}

	public TaskTracker updateTaskTrackerWithError(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, com.ulca.dataset.model.Error error) {
		TaskTracker taskTracker1 = new TaskTracker();

		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
		if(!taskTrackerList.isEmpty()) {
			TaskTracker taskTracker = taskTrackerList.get(0);
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status.toString());
			taskTracker.setError(error);
			taskTracker1 =	taskTrackerDao.save(taskTracker);

		}else {
			TaskTracker taskTracker = new TaskTracker();
			taskTracker.setServiceRequestNumber(serviceRequestNumber);
			taskTracker.setTool(tool.toString());
			taskTracker.setStartTime(new Date().toString());
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status.toString());
			taskTracker.setError(error);
			taskTracker1 = taskTrackerDao.save(taskTracker);
		}
		return taskTracker1;
	}

	public TaskTracker updateTaskTrackerWithErrorAndEndTime(String serviceRequestNumber, TaskTracker.ToolEnum tool, com.ulca.dataset.model.TaskTracker.StatusEnum status, com.ulca.dataset.model.Error error) {
		TaskTracker taskTracker1 = new TaskTracker();

		List<TaskTracker> taskTrackerList = taskTrackerDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool.toString());
		if(!taskTrackerList.isEmpty()) {
			TaskTracker taskTracker = taskTrackerList.get(0);
			taskTracker.setEndTime(new Date().toString());
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status.toString());
			taskTracker.setError(error);
			taskTracker1 = taskTrackerDao.save(taskTracker);

		}else {
			TaskTracker taskTracker = new TaskTracker();
			taskTracker.setServiceRequestNumber(serviceRequestNumber);
			taskTracker.setTool(tool.toString());
			taskTracker.setStartTime(new Date().toString());
			taskTracker.setEndTime(new Date().toString());
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setStatus(status.toString());
			taskTracker.setError(error);
			taskTracker1 =	taskTrackerDao.save(taskTracker);
		}
		return taskTracker1;
	}
}
