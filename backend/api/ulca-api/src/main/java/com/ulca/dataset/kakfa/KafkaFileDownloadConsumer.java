package com.ulca.dataset.kakfa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.service.ProcessTaskTrackerService;
import com.ulca.dataset.util.UnzipUtility;

import io.swagger.model.DatasetType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaFileDownloadConsumer {

	@Autowired
	UnzipUtility unzipUtility;

	@Autowired
	ParamsSchemaValidator paramsSchemaValidator;
	
	
	
	@Autowired
	ProcessTaskTrackerService processTaskTrackerService;
	
	@Autowired
	TaskTrackerDao taskTrackerDao;
	
	@Autowired
	DatasetErrorPublishService datasetErrorPublishService;

	@Value(value = "${FILE_DOWNLOAD_FOLDER}")
    private String downloadFolder;
	
	@Autowired
	DatasetAsrValidateIngest datasetAsrValidateIngest;
	
	@Autowired
	DatasetParallelCorpusValidateIngest datasetParallelCorpusValidateIngest;
	
	@KafkaListener(groupId = "${KAFKA_ULCA_DS_INGEST_IP_TOPIC_GROUP_ID}", topics = "${KAFKA_ULCA_DS_INGEST_IP_TOPIC}" , containerFactory = "filedownloadKafkaListenerContainerFactory")
	public void downloadFile(FileDownload file) {

		TaskTracker taskTrackerDownload = null;
		TaskTracker taskTrackerIngest = null;
		
		String datasetId = file.getDatasetId();
		String fileUrl = file.getFileUrl();
		String serviceRequestNumber = file.getServiceRequestNumber();
		Map<String,String> fileMap = null;
		
		try {
			log.info("************ Entry KafkaFileDownloadConsumer :: downloadFile *********");
			
			
			log.info(" datasetId :: " + datasetId);
			log.info("fileUrl :: " + fileUrl);
			log.info("serviceRequestNumber :: " + serviceRequestNumber);
			
			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.inprogress);
			processTaskTrackerService.createTaskTracker(serviceRequestNumber, ToolEnum.download, com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress);
			
			
			
			try {
				
				String fileName = serviceRequestNumber+".zip";
				
				String filePath = downloadUsingNIO(fileUrl, downloadFolder,fileName);
				//String filePath = 
				log.info("file download complete");
				log.info("file path in downloadFile servide ::" + filePath);
				
				fileMap = unzipUtility.unzip(filePath, downloadFolder, serviceRequestNumber);
				
				Set<String> keys = fileMap.keySet();
				log.info("logging the fileMap keys");
				for(String key : keys) {
					log.info("key :: "+key);
					log.info("value :: " + fileMap.get(key));
				}
				
				log.info("file unzip complete");
				
				processTaskTrackerService.updateTaskTracker(serviceRequestNumber, ToolEnum.download, com.ulca.dataset.model.TaskTracker.StatusEnum.successful);
				
				
				
				

			} catch (IOException e) {
				//update error
				
				
				Error error = new Error();
				error.setCause(e.getMessage());
				error.setMessage("file download failed");
				error.setCode("01_00000000");
				
				
				processTaskTrackerService.updateTaskTrackerWithError(serviceRequestNumber, ToolEnum.download, com.ulca.dataset.model.TaskTracker.StatusEnum.failed, error);
				
				
				processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);
				
				//send error event for download failure
				JSONObject errorMessage = new JSONObject();
				errorMessage.put("eventType", "dataset-training");
				errorMessage.put("messageType", "error");
				errorMessage.put("code", "1000_FILE_DOWNLOAD_FAILURE");
				errorMessage.put("eventId", "serviceRequestNumber|"+serviceRequestNumber);
				Calendar cal = Calendar.getInstance();
			    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
			    Date date = cal.getTime();
				//errorMessage.put("timestamp", df2.format(date));
			    errorMessage.put("timestamp", new Date().toString());
				errorMessage.put("serviceRequestNumber", serviceRequestNumber);
				errorMessage.put("stage", "ingest");
				errorMessage.put("datasetType", DatasetType.PARALLEL_CORPUS.toString());
				errorMessage.put("message", e.getMessage());
				datasetErrorPublishService.publishDatasetError(errorMessage);
				
				e.printStackTrace();
				
				return;
			}
			
			processTaskTrackerService.createTaskTracker(serviceRequestNumber, ToolEnum.ingest, com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress);
			
			
			if(file.getDatasetType() == DatasetType.ASR_CORPUS) {
				log.info("calling the asr validate service");
				datasetAsrValidateIngest.validateIngest(fileMap,file);
			} else if(file.getDatasetType() == DatasetType.PARALLEL_CORPUS) {
				log.info("calling the parallel-corpus validate service");
				datasetParallelCorpusValidateIngest.validateIngest(fileMap,file,taskTrackerIngest);
			}
			
			
			
			log.info("************ Exit KafkaFileDownloadConsumer :: downloadFile *********");
		}catch (Exception e) {
			
			
			
			e.printStackTrace();
			
			
		}
		
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
