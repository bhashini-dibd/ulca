package com.ulca.dataset.kakfa;

import java.io.FileOutputStream;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.util.UnzipUtility;

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

	@Value(value = "${FILE_DOWNLOAD_FOLDER}")
    private String downlaodFolder;
	
	
	
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
		
		
		log.info(processTracker.toString());
		
		try {
			processTracker.setStatus(StatusEnum.inprogress);
			processTrackerDao.save(processTracker);
			
			
			String filePath = downloadUsingNIO(fileUrl, downlaodFolder);
			ArrayList<String> fileList = unzipUtility.unzip(filePath, downlaodFolder);
			
			
			TaskTracker taskTracker = new TaskTracker();
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setEndTime(new Date().toString());
			taskTracker.setTool(ToolEnum.download);
			taskTracker.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.successful);
			taskTracker.setServiceRequestNumber(file.getServiceRequestNumber());
			taskTrackerDao.save(taskTracker);
			

			Map<String, String> fileMap = new HashMap<String, String>();
			ParallelDatasetParamsSchema paramsSchema = null;
			
			for (String filePathUnzipped : fileList) {
				System.out.println("listing unzipped files :: " + filePathUnzipped);
				if (filePathUnzipped.contains("param")) {
					try {
						 paramsSchema = paramsSchemaValidator.validateParamsSchema(filePathUnzipped);
						 fileMap.put("params", filePathUnzipped);
						
					} catch(Exception e) {
						 //update error
						taskTracker = new TaskTracker();
						taskTracker.setLastModified(new Date().toString());
						taskTracker.setEndTime(new Date().toString());
						taskTracker.setTool(ToolEnum.validate);
						taskTracker.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.failed);
						Error error = new Error();
						error.setCause("params validation failed");
						error.setMessage("params validation failed");
						error.setCode("01_00000001");
						taskTracker.setError(error);
						taskTracker.setServiceRequestNumber(file.getServiceRequestNumber());

						taskTrackerDao.save(taskTracker);
						processTracker.setStatus(StatusEnum.failed);
						processTrackerDao.save(processTracker);
						 return ;
						
					}
					

					
				}
				if (filePathUnzipped.contains("data")) {

					fileMap.put("data", filePathUnzipped);
				}

			}
			
			datasetIngestService.datasetIngest(paramsSchema,file, fileMap);
			
			taskTracker = new TaskTracker();
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setEndTime(new Date().toString());
			taskTracker.setTool(ToolEnum.ingest);
			taskTracker.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.successful);
			
			taskTracker.setServiceRequestNumber(file.getServiceRequestNumber());

			taskTrackerDao.save(taskTracker);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//update error
			TaskTracker taskTracker = new TaskTracker();
			taskTracker.setLastModified(new Date().toString());
			taskTracker.setEndTime(new Date().toString());
			taskTracker.setTool(ToolEnum.download);
			taskTracker.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.failed);
			Error error = new Error();
			error.setCause("file download failed");
			error.setMessage("file download failed");
			error.setCode("01_00000000");
			taskTracker.setError(error);
			taskTracker.setServiceRequestNumber(file.getServiceRequestNumber());
			taskTrackerDao.save(taskTracker);
			processTracker.setStatus(StatusEnum.failed);
			processTrackerDao.save(processTracker);
			
			e.printStackTrace();
		}
		log.info("************ Exit KafkaFileDownloadConsumer :: downloadFile *********");
	}

	private String downloadUsingNIO(String urlStr, String downloadFolder) throws IOException {
		log.info("************ Entry KafkaFileDownloadConsumer :: downloadUsingNIO *********");
		URL url = new URL(urlStr);
		log.info(url.getFile());

		String file = downloadFolder + "/test.zip";
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		System.out.println(rbc.toString());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();

		log.info("************ Exit KafkaFileDownloadConsumer :: downloadUsingNIO *********");
		return file;
	}
}
