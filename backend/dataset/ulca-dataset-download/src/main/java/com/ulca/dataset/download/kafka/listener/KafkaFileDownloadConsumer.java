package com.ulca.dataset.download.kafka.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.ulca.dataset.download.constants.DatasetDownloadConstants;
import com.ulca.dataset.dao.FileIdentifierDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.service.DatasetErrorPublishService;
import com.ulca.dataset.kakfa.model.DatasetIngest;
import com.ulca.dataset.kakfa.model.FileDownload;
import com.ulca.dataset.model.Error;
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
	ProcessTaskTrackerService processTaskTrackerService;
	
	
	@Autowired
	DatasetErrorPublishService datasetErrorPublishService;

	@Value("${file.download.folder}")
    private String downloadFolder;
	
	@Autowired
	TaskTrackerDao taskTrackerDao;
	
	@Autowired
	FileIdentifierDao fileIdentifierDao;
	
	@Autowired
	private KafkaTemplate<String, DatasetIngest> datasetIngestKafkaTemplate;

	@Value("${kafka.ulca.ds.ingest.ip.topic}")
	private String datasetIngestTopic;
	
	
																																						
	@KafkaListener(groupId = "${kafka.ulca.ds.filedownload.ip.topic.group.id}", topics = "${kafka.ulca.ds.filedownload.ip.topic}" , containerFactory = "filedownloadKafkaListenerContainerFactory")
	public void downloadFile(FileDownload file) {

		String datasetId = file.getDatasetId();
		String fileUrl = file.getFileUrl();
		String serviceRequestNumber = file.getServiceRequestNumber();
		String datasetName = file.getDatasetName();
		//DatasetType datasetType = file.getDatasetType();
		DatasetType datasetType = null;
		String userId = file.getUserId();
		
		DatasetIngest datasetIngest = new DatasetIngest();
		//disabling pseudo ingest
		//datasetIngest.setMode(DatasetConstants.INGEST_PSEUDO_MODE);
		datasetIngest.setMode(DatasetDownloadConstants.INGEST_REAL_MODE);
		
		
		Map<String,String> fileMap = null;
		
		try {
			log.info("************ Entry KafkaFileDownloadConsumer :: downloadFile *********");
			log.info("datasetId :: " + datasetId);
			log.info("fileUrl :: " + fileUrl);
			log.info("serviceRequestNumber :: " + serviceRequestNumber);
			
			List<TaskTracker> list = taskTrackerDao.findAllByServiceRequestNumber(serviceRequestNumber);
			if(list.size() > 0) {
				log.info("duplicated processing of serviceRequestNumber :: " + serviceRequestNumber);
				return;
			}
			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.inprogress);
			processTaskTrackerService.createTaskTracker(serviceRequestNumber, ToolEnum.download, com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress);
			
			try {
				
				String fileName = serviceRequestNumber+".zip";
				String filePath = downloadUsingNIO(fileUrl, downloadFolder,fileName);
				
				log.info("file download complete");
				log.info("file path in downloadFile servide ::" + filePath);
				
				String md5hash = downloadFileSanityCheck(filePath);
				
				fileMap = unzipUtility.unzip(filePath, downloadFolder, serviceRequestNumber);
				
				datasetType = getDatasetType(fileMap);
				
				log.info("file unzip complete");
				processTaskTrackerService.updateTaskTracker(serviceRequestNumber, ToolEnum.download, com.ulca.dataset.model.TaskTracker.StatusEnum.completed);
				
				datasetIngest.setDatasetId(datasetId);
				datasetIngest.setServiceRequestNumber(serviceRequestNumber);
				datasetIngest.setDatasetName(datasetName);
				datasetIngest.setBaseLocation(fileMap.get("baseLocation"));
				datasetIngest.setMd5hash(md5hash);
				datasetIngest.setDatasetType(datasetType);
				datasetIngest.setUserId(userId);

			} catch (IOException e) {
				
				//update error
				Error error = new Error();
				error.setCause(e.getMessage());
				error.setMessage("file download failed");
				error.setCode("1000_FILE_DOWNLOAD_FAILURE");
				processTaskTrackerService.updateTaskTrackerWithErrorAndEndTime(serviceRequestNumber, ToolEnum.download, com.ulca.dataset.model.TaskTracker.StatusEnum.failed, error);
				processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);
				
				//send error event for download failure
				datasetErrorPublishService.publishDatasetError("dataset-training", "1000_FILE_DOWNLOAD_FAILURE", e.getMessage(), serviceRequestNumber, datasetName,"download" , null, null) ;
				e.printStackTrace();
				
				return;
			}
			datasetIngestKafkaTemplate.send(datasetIngestTopic, datasetIngest);
			//datasetIngestKafkaTemplate.send(datasetIngestTopic,0,null, datasetIngest);
			
			
			log.info("************ Exit KafkaFileDownloadConsumer :: downloadFile *********");
			
		}catch (Exception e) {
			log.info("Unhadled Exception :: " + e.getMessage());
			log.info("cause :: " + e.getClass());
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
	
	private String downloadFileSanityCheck(String filePath) throws IOException {
		File f = new File(filePath);
		
		//check if file is executable
		/*if(f.canExecute()) {
			throw new IOException("Executable File Not allowed");
			
		}*/
		//check if md5hash of file exist
		String md5hash = fileMD5hash(filePath);
		
		
		return md5hash;
	}
	
	private String fileMD5hash(String filePath) throws IOException {
		
		 HashCode hash = null;
		 String myChecksum  = null;
		try {
			hash = com.google.common.io.Files
				      .hash(new File(filePath), Hashing.md5());
			
			
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
		if( hash != null) {
			
			 myChecksum = hash.toString()
				      .toUpperCase();
			 /*
			 Fileidentifier  fileIdentifier = fileIdentifierDao.findByMd5hash(myChecksum);
			 if(fileIdentifier != null) {
				 throw new IOException("Same File Already exists in system");
			 }*/
			 return myChecksum;
		}
		
		return myChecksum;
	}
	
	private DatasetType getDatasetType(Map<String,String> fileMap) throws IOException {
		DatasetType datasetType = null;
		String paramsFilePath = fileMap.get("baseLocation")  + File.separator + "params.json";
		Object rowObj = new Gson().fromJson(new FileReader(paramsFilePath), Object.class);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String dataRow = mapper.writeValueAsString(rowObj);
			JSONObject params =  new JSONObject(dataRow);
			
			if(params.isEmpty() || !params.has("datasetType")) {
				 throw new IOException("params.json does not contain datasetType");
			}
			String type = params.getString("datasetType");
			datasetType = DatasetType.fromValue(type);
			
			return datasetType;
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw new IOException("params.json not valid");
			
		}
		
	}
}
