package com.ulca.dataset.kakfa;

import java.io.FileOutputStream;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.util.DateUtil;
import com.ulca.dataset.util.UnzipUtility;

import io.swagger.model.DatasetType;
import io.swagger.model.ParallelDatasetParamsSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaFileDownloadConsumer {

	@Autowired
	UnzipUtility unzipUtility;

	@Autowired
	ParamsSchemaValidator paramsSchemaValidator;
	
	@Autowired
	DatasetIngestService datasetIngestService;
	
	@Autowired
	ProcessTrackerDao processTrackerDao;
	
	@Autowired
	TaskTrackerDao taskTrackerDao;
	
	@Autowired
	DatasetErrorPublishService datasetErrorPublishService;

	@Value(value = "${FILE_DOWNLOAD_FOLDER}")
    private String downlaodFolder;
	
	@Autowired
	DatasetAsrValidateIngest datasetAsrValidateIngest;
	
	@Autowired
	DatasetParallelCorpusValidateIngest datasetParallelCorpusValidateIngest;
	
	@KafkaListener(groupId = "${KAFKA_ULCA_DS_INGEST_IP_TOPIC_GROUP_ID}", topics = "${KAFKA_ULCA_DS_INGEST_IP_TOPIC}" , containerFactory = "filedownloadKafkaListenerContainerFactory")
	public void downloadFile(FileDownload file) {

		log.info("************ Entry KafkaFileDownloadConsumer :: downloadFile *********");
		String datasetId = file.getDatasetId();
		String fileUrl = file.getFileUrl();
		String serviceRequestNumber = file.getServiceRequestNumber();
		
		log.info(" datasetId :: " + datasetId);
		log.info("fileUrl :: " + fileUrl);
		log.info("serviceRequestNumber :: " + serviceRequestNumber);
		

		
		ProcessTracker processTracker = processTrackerDao.findByServiceRequestNumber(serviceRequestNumber);
		processTracker.setStatus(StatusEnum.inprogress);
		processTrackerDao.save(processTracker);
		
		TaskTracker taskTrackerDownload = new TaskTracker();
		taskTrackerDownload.setStartTime(DateUtil.getCurrentDate());
		taskTrackerDownload.setTool(ToolEnum.download);
		taskTrackerDownload.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress);
		taskTrackerDownload.setServiceRequestNumber(file.getServiceRequestNumber());
		taskTrackerDao.save(taskTrackerDownload);
		
		
		log.info(processTracker.toString());
		
		try {
			
			String fileName = serviceRequestNumber+".zip";
			
			String filePath = downloadUsingNIO(fileUrl, downlaodFolder,fileName);
			//String filePath = 
			log.info("file download complete");
			log.info("file path in downloadFile servide ::" + filePath);
			
			Map<String,String> fileMap = unzipUtility.unzip(filePath, downlaodFolder);
			
			Set<String> keys = fileMap.keySet();
			log.info("logging the fileMap keys");
			for(String key : keys) {
				log.info("key :: "+key);
				log.info("value :: " + fileMap.get(key));
			}
			
			log.info("file unzip complete");
			taskTrackerDownload.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.successful);
			taskTrackerDownload.setEndTime(new Date().toString());
			taskTrackerDao.save(taskTrackerDownload);
			

			
			TaskTracker taskTrackerIngest = new TaskTracker();
			taskTrackerIngest.setLastModified(DateUtil.getCurrentDate());
			taskTrackerIngest.setTool(ToolEnum.ingest);
			taskTrackerIngest.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress);
			taskTrackerIngest.setServiceRequestNumber(file.getServiceRequestNumber());

			taskTrackerDao.save(taskTrackerIngest);
			
			if(file.getDatasetType() == DatasetType.ASR_CORPUS) {
				log.info("calling the asr validate service");
				datasetAsrValidateIngest.validateIngest(fileMap,file, processTracker,taskTrackerIngest);
			} else if(file.getDatasetType() == DatasetType.PARALLEL_CORPUS) {
				log.info("calling the parallel-corpus validate service");
				datasetParallelCorpusValidateIngest.validateIngest(fileMap,file,processTracker,taskTrackerIngest);
			}
			
			
			

		} catch (IOException e) {
			//update error
			
			taskTrackerDownload.setLastModified(new Date().toString());
			taskTrackerDownload.setEndTime(new Date().toString());
			taskTrackerDownload.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.failed);
			Error error = new Error();
			error.setCause(e.getMessage());
			error.setMessage("file download failed");
			error.setCode("01_00000000");
			taskTrackerDownload.setError(error);
			taskTrackerDao.save(taskTrackerDownload);
			
			processTracker.setStatus(StatusEnum.failed);
			processTrackerDao.save(processTracker);
			
			//send error event for download failure
			JSONObject errorMessage = new JSONObject();
			errorMessage.put("eventType", "dataset-training");
			errorMessage.put("messageType", "error");
			errorMessage.put("code", "1000_FILE_DOWNLOAD_FAILURE");
			errorMessage.put("eventId", "serviceRequestNumber|"+serviceRequestNumber);
			Calendar cal = Calendar.getInstance();
		    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		    Date date = cal.getTime();
			errorMessage.put("timestamp", df2.format(date));
			errorMessage.put("serviceRequestNumber", serviceRequestNumber);
			errorMessage.put("stage", "ingest");
			errorMessage.put("datasetType", DatasetType.PARALLEL_CORPUS.toString());
			errorMessage.put("message", e.getMessage());
			datasetErrorPublishService.publishDatasetError(errorMessage);
			
			e.printStackTrace();
		}
		log.info("************ Exit KafkaFileDownloadConsumer :: downloadFile *********");
	}

	private String downloadUsingNIO(String urlStr, String downloadFolder, String fileName) throws IOException {
		log.info("************ Entry KafkaFileDownloadConsumer :: downloadUsingNIO *********");
		URL url = new URL(urlStr);
		String file = downloadFolder +"/"+ fileName;
		log.info("file path indownloadUsingNIO" );
		log.info(file);
		log.info(url.getPath());
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		log.info(url.getContent().toString());
		log.info(rbc.getClass().toString());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();

		log.info("************ Exit KafkaFileDownloadConsumer :: downloadUsingNIO *********");
		return file;
	}
}
