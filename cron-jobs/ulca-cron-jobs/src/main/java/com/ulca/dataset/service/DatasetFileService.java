package com.ulca.dataset.service;

import com.ulca.dataset.request.ObjectStoreFileUploadRequest;
import com.ulca.dataset.request.ObjectStoreFileUploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class DatasetFileService {

	@Value("${file.download.folder}")
	private String downloadFolder;

	@Value("${ulca.file.store.server.url}")
	private String ulcaFileStoreServerUrl;

	@Value("${ulca.file.store.upload}")
	private String ulcaFileStoreUpload;

	@Value("${dataset.upload.objectstore.folder}")
	private String datasetUploadObjectstoreFolder;

	@Autowired
	DatasetService datasetService;

	@Autowired
	WebClient.Builder builder;

	/*
	 * upload the dataset file to object store
	 */
	public String uploadDatasetFile(String serviceRequestNumber) {

		String callBackUrl = ulcaFileStoreServerUrl + ulcaFileStoreUpload;

		ObjectStoreFileUploadRequest request = new ObjectStoreFileUploadRequest();
		request.setFileLocation(downloadFolder);
		request.setFileName(serviceRequestNumber + ".zip");
		request.setStorageFolder(datasetUploadObjectstoreFolder);

		ObjectStoreFileUploadResponse response = builder.build().post().uri(callBackUrl)
				.body(Mono.just(request), ObjectStoreFileUploadRequest.class).retrieve()
				.bodyToMono(ObjectStoreFileUploadResponse.class).block();

		return response.getData();

	}

	public void datasetAfterIngestCleanJob(String serviceRequestNumber) {
		String localUrl = uploadDatasetFile(serviceRequestNumber);
		datasetService.updateDatasetFileLocation(serviceRequestNumber, localUrl);
		deleteUploadedDatasetFile(serviceRequestNumber);

	}

	public void deleteUploadedDatasetFile(String serviceRequestNumber) {
		String fileName = serviceRequestNumber + ".zip";
		String filePath = downloadFolder + "/" + fileName;
		File file = new File(filePath);
		Boolean isZippedFileDeleted = file.delete();
		
		// delete unzipped folder
		String unzippedFolderLocation = downloadFolder + "/" + serviceRequestNumber;
		Path dir = Paths.get(unzippedFolderLocation);
		try {
			boolean result = FileSystemUtils.deleteRecursively(dir);
		} catch (IOException e) {
			
			log.info("could not delete folder :: " + unzippedFolderLocation + " serviceRequestNumber : " + serviceRequestNumber);
			e.printStackTrace();
		}
	    
		log.info("dataset downloaded file is being deleted after storing to object store, serviceRequestNumber : " + serviceRequestNumber);
		log.info("deleted file :: " + filePath );
		log.info("deleted folder :: " + unzippedFolderLocation);
	}

}
