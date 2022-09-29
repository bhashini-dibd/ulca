package com.ulca.benchmark.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.dao.BenchmarkTaskTrackerDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.model.BenchmarkTaskTracker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BmProcessTrackerService {

	@Autowired
	BenchmarkTaskTrackerDao benchmarkTaskTrackerDao;

	@Autowired
	BenchmarkProcessDao benchmarkProcessDao;

	public void createTaskTracker(String benchmarkProcessId, BenchmarkTaskTracker.ToolEnum tool,
			BenchmarkTaskTracker.StatusEnum status) {

		BenchmarkTaskTracker taskTracker = new BenchmarkTaskTracker();
		taskTracker.setBenchmarkProcessId(benchmarkProcessId);
		taskTracker.setTool(tool);
		taskTracker.setStatus(status.toString());
		taskTracker.setStartTime(Instant.now().toEpochMilli());
		benchmarkTaskTrackerDao.save(taskTracker);

	}

	public void createTaskTracker(List<String> benchmarkProcessIdsList, BenchmarkTaskTracker.ToolEnum tool,
			BenchmarkTaskTracker.StatusEnum status) {

		for (String benchmarkProcessId : benchmarkProcessIdsList) {
			BenchmarkTaskTracker taskTracker = new BenchmarkTaskTracker();
			taskTracker.setBenchmarkProcessId(benchmarkProcessId);
			taskTracker.setTool(tool);
			taskTracker.setStatus(status.toString());
			taskTracker.setStartTime(Instant.now().toEpochMilli());
			benchmarkTaskTrackerDao.save(taskTracker);
		}

	}

	public void updateTaskTrackerWithErrorAndEndTime(String benchmarkProcessId, BenchmarkTaskTracker.ToolEnum tool,
			BenchmarkTaskTracker.StatusEnum status, com.ulca.benchmark.model.BenchmarkError error) {

		List<BenchmarkTaskTracker> taskTrackerList = benchmarkTaskTrackerDao
				.findAllByBenchmarkProcessIdAndTool(benchmarkProcessId, tool);

		if (!taskTrackerList.isEmpty()) {
			BenchmarkTaskTracker taskTracker = taskTrackerList.get(0);
			taskTracker.setEndTime(Instant.now().toEpochMilli());
			taskTracker.setLastModified(Instant.now().toEpochMilli());
			taskTracker.setStatus(status.toString());
			taskTracker.setError(error);
			benchmarkTaskTrackerDao.save(taskTracker);

		} else {
			BenchmarkTaskTracker taskTracker = new BenchmarkTaskTracker();
			taskTracker.setBenchmarkProcessId(benchmarkProcessId);
			taskTracker.setTool(tool);
			taskTracker.setStartTime(Instant.now().toEpochMilli());
			taskTracker.setEndTime(Instant.now().toEpochMilli());
			taskTracker.setLastModified(Instant.now().toEpochMilli());
			taskTracker.setStatus(status.toString());
			taskTracker.setError(error);
			benchmarkTaskTrackerDao.save(taskTracker);
		}
	}

	public void updateTaskTrackerWithErrorAndEndTime(List<String> benchmarkProcessIdsList,
			BenchmarkTaskTracker.ToolEnum tool, BenchmarkTaskTracker.StatusEnum status,
			com.ulca.benchmark.model.BenchmarkError error) {

		for (String benchmarkProcessId : benchmarkProcessIdsList) {
			List<BenchmarkTaskTracker> taskTrackerList = benchmarkTaskTrackerDao
					.findAllByBenchmarkProcessIdAndTool(benchmarkProcessId, tool);

			if (!taskTrackerList.isEmpty()) {
				BenchmarkTaskTracker taskTracker = taskTrackerList.get(0);
				taskTracker.setEndTime(Instant.now().toEpochMilli());
				taskTracker.setLastModified(Instant.now().toEpochMilli());
				taskTracker.setStatus(status.toString());
				taskTracker.setError(error);
				benchmarkTaskTrackerDao.save(taskTracker);

			} else {
				BenchmarkTaskTracker taskTracker = new BenchmarkTaskTracker();
				taskTracker.setBenchmarkProcessId(benchmarkProcessId);
				taskTracker.setTool(tool);
				taskTracker.setStartTime(Instant.now().toEpochMilli());
				taskTracker.setEndTime(Instant.now().toEpochMilli());
				taskTracker.setLastModified(Instant.now().toEpochMilli());
				taskTracker.setStatus(status.toString());
				taskTracker.setError(error);
				benchmarkTaskTrackerDao.save(taskTracker);
			}
		}

	}

	public void updateTaskTracker(String benchmarkProcessId, BenchmarkTaskTracker.ToolEnum tool,
			com.ulca.benchmark.model.BenchmarkTaskTracker.StatusEnum status) {

		List<BenchmarkTaskTracker> taskTrackerList = benchmarkTaskTrackerDao
				.findAllByBenchmarkProcessIdAndTool(benchmarkProcessId, tool);
		if (!taskTrackerList.isEmpty()) {
			BenchmarkTaskTracker taskTracker = taskTrackerList.get(0);
			if (status == BenchmarkTaskTracker.StatusEnum.completed
					|| status == BenchmarkTaskTracker.StatusEnum.failed) {
				taskTracker.setEndTime(Instant.now().toEpochMilli());
			}
			taskTracker.setStatus(status.toString());
			benchmarkTaskTrackerDao.save(taskTracker);
		}
	}

	public void updateTaskTracker(List<String> benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum tool,
			com.ulca.benchmark.model.BenchmarkTaskTracker.StatusEnum status) {

		for(String benchmarkProcessId : benchmarkProcessIdList) {
			List<BenchmarkTaskTracker> taskTrackerList = benchmarkTaskTrackerDao
					.findAllByBenchmarkProcessIdAndTool(benchmarkProcessId, tool);
			if (!taskTrackerList.isEmpty()) {
				BenchmarkTaskTracker taskTracker = taskTrackerList.get(0);
				if (status == BenchmarkTaskTracker.StatusEnum.completed
						|| status == BenchmarkTaskTracker.StatusEnum.failed) {
					taskTracker.setEndTime(Instant.now().toEpochMilli());
				}
				taskTracker.setStatus(status.toString());
				benchmarkTaskTrackerDao.save(taskTracker);
			}
		}
		
	}
	
	public void updateBmProcess(String benchmarkProcessId, String status) {

		BenchmarkProcess bmProcess = benchmarkProcessDao.findByBenchmarkProcessId(benchmarkProcessId);
		bmProcess.setStatus(status);
		bmProcess.setLastModifiedOn(Instant.now().toEpochMilli());
		bmProcess.setEndTime(Instant.now().toEpochMilli());
		benchmarkProcessDao.save(bmProcess);

	}

	public void updateBmProcess(List<String> benchmarkProcessIdList, String status) {

		for (String benchmarkProcessId : benchmarkProcessIdList) {
			BenchmarkProcess bmProcess = benchmarkProcessDao.findByBenchmarkProcessId(benchmarkProcessId);
			bmProcess.setStatus(status);
			bmProcess.setLastModifiedOn(Instant.now().toEpochMilli());
			bmProcess.setEndTime(Instant.now().toEpochMilli());
			benchmarkProcessDao.save(bmProcess);
		}

	}

}
