package com.ulca.dataset.kakfa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.dataset.dao.ProcessTrackerDao;
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

	@Value(value = "${FILE_DOWNLOAD_FOLDER}")
    private String downlaodFolder;
	
	
	
	@KafkaListener(groupId = "${KAFKA_ULCA_DS_INGEST_IP_TOPIC_GROUP_ID}", topics = "${KAFKA_ULCA_DS_INGEST_IP_TOPIC}" , containerFactory = "filedownloadKafkaListenerContainerFactory")
	public void downloadFile(FileDownload file) {

		log.info("************ Entry KafkaFileDownloadConsumer :: downloadFile *********");
		log.info(file.getDatasetId());
		log.info(file.getFileUrl());
		log.info(file.getServiceRequestNumber());

		

		try {
			String filePath = downloadUsingNIO(file.getFileUrl(), downlaodFolder);
			ArrayList<String> fileList = unzipUtility.unzip(filePath, downlaodFolder);

			Map<String, String> fileMap = new HashMap<String, String>();
			ParallelDatasetParamsSchema paramsSchema = null;
			for (String filePathUnzipped : fileList) {
				System.out.println("listing unzipped files :: " + filePathUnzipped);
				if (filePathUnzipped.contains("param")) {
					 paramsSchema = paramsSchemaValidator.validateParamsSchema(filePathUnzipped);

					fileMap.put("params", filePathUnzipped);
				}
				if (filePathUnzipped.contains("data")) {

					fileMap.put("data", filePathUnzipped);
				}

			}
			
			datasetIngestService.datasetIngest(paramsSchema,file, fileMap);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
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
