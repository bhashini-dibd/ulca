package com.ulca.benchmark.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.dao.BenchmarkDatasetSubmitStagesDao;
import com.ulca.benchmark.dao.BenchmarkDatasetSubmitStatusDao;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStages;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStatus;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStatus.ServiceRequestActionEnum;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStatus.ServiceRequestTypeEnum;
import com.ulca.benchmark.model.BenchmarkError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class BenchmarkSubmtStatusService {
	
	@Autowired
	BenchmarkDatasetSubmitStagesDao bmsubmtStagesDao;
	
	@Autowired
	BenchmarkDatasetSubmitStatusDao bmsubmtStatusDao;
	
	
	public void createStatus(String serviceRequestNumber, String userId, String benchmarkId) {

		BenchmarkDatasetSubmitStatus bmSubmtStatus = new BenchmarkDatasetSubmitStatus();
		bmSubmtStatus.setUserId(userId);
		bmSubmtStatus.setBenchmarkId(benchmarkId);
		bmSubmtStatus.setServiceRequestNumber(serviceRequestNumber);
		bmSubmtStatus.setServiceRequestAction(ServiceRequestActionEnum.submit);
		bmSubmtStatus.setServiceRequestType(ServiceRequestTypeEnum.benchmark);
		bmSubmtStatus.setStatus(BenchmarkDatasetSubmitStatus.StatusEnum.pending.toString());
		bmSubmtStatus.setStartTime(new Date().toString());
		bmsubmtStatusDao.save(bmSubmtStatus);

	}
	
	public void updateStatus(String serviceRequestNumber, BenchmarkDatasetSubmitStatus.StatusEnum status) {

		BenchmarkDatasetSubmitStatus submtStatus = bmsubmtStatusDao.findByServiceRequestNumber(serviceRequestNumber);
		submtStatus.setStatus(status.toString());
		submtStatus.setLastModified(new Date().toString());
		
		if(!(submtStatus.getStatus().equals(BenchmarkDatasetSubmitStatus.StatusEnum.completed.toString())
				|| submtStatus.getStatus().equals(BenchmarkDatasetSubmitStatus.StatusEnum.failed.toString())
				)) {
			
			if(status == BenchmarkDatasetSubmitStatus.StatusEnum.completed || status == BenchmarkDatasetSubmitStatus.StatusEnum.failed) {
				submtStatus.setEndTime(new Date().toString());
			}
			
		}
		
		bmsubmtStatusDao.save(submtStatus);

	}
	
 public void createStages(String serviceRequestNumber, BenchmarkDatasetSubmitStages.ToolEnum tool, BenchmarkDatasetSubmitStages.StatusEnum status) {
		
	 BenchmarkDatasetSubmitStages stages = new BenchmarkDatasetSubmitStages();
		
	 stages.serviceRequestNumber(serviceRequestNumber);
	 stages.setTool(tool);
	 stages.setStatus(status.toString());
	 stages.setStartTime(new Date().toString());
		
	 bmsubmtStagesDao.save(stages);
		
	}
	public void updateStages(String serviceRequestNumber, BenchmarkDatasetSubmitStages.ToolEnum tool, BenchmarkDatasetSubmitStages.StatusEnum status) {
		
		List<BenchmarkDatasetSubmitStages> stagesList = bmsubmtStagesDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool);
		if(!stagesList.isEmpty()) {
			BenchmarkDatasetSubmitStages stage = stagesList.get(0);
			if(status == BenchmarkDatasetSubmitStages.StatusEnum.completed || status == BenchmarkDatasetSubmitStages.StatusEnum.failed) {
				stage.setEndTime(new Date().toString());
			}
			stage.setStatus(status.toString());
			bmsubmtStagesDao.save(stage);
			
		}
		
	}
	
	public void updateStagesWithErrorAndEndTime(String serviceRequestNumber, BenchmarkDatasetSubmitStages.ToolEnum tool, BenchmarkDatasetSubmitStages.StatusEnum status, BenchmarkError error) {
		
		List<BenchmarkDatasetSubmitStages> stagesList = bmsubmtStagesDao.findAllByServiceRequestNumberAndTool(serviceRequestNumber, tool);
		if(!stagesList.isEmpty()) {
			BenchmarkDatasetSubmitStages stage = stagesList.get(0);
			stage.setEndTime(new Date().toString());
			stage.setLastModified(new Date().toString());
			stage.setStatus(status.toString());
			stage.setError(error);
			bmsubmtStagesDao.save(stage);
			
		}else {
			BenchmarkDatasetSubmitStages stage = new BenchmarkDatasetSubmitStages();
			stage.setServiceRequestNumber(serviceRequestNumber);
			stage.setTool(tool);
			stage.setStartTime(new Date().toString());
			stage.setEndTime(new Date().toString());
			stage.setLastModified(new Date().toString());
			stage.setStatus(status.toString());
			stage.setError(error);
			bmsubmtStagesDao.save(stage);
		}
	}

}
