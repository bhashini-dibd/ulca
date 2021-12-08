package com.ulca.benchmark.kafka.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkDatasetSubmitStagesDao;
import com.ulca.benchmark.dao.BenchmarkDatasetSubmitStatusDao;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStages;
import com.ulca.benchmark.model.BenchmarkDatasetSubmitStatus;
import com.ulca.benchmark.model.BenchmarkError;
import com.ulca.benchmark.model.BenchmarkSubmissionType;
import com.ulca.benchmark.service.BenchmarkSubmtStatusService;
import com.ulca.benchmark.util.UnzipUtility;

import io.swagger.model.Benchmark;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.ModelTask;
import io.swagger.model.Submitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaBenchmarkIngestConsumer {
	
	@Autowired
	UnzipUtility unzipUtility;
			
	@Value("${ulca.bm.ds.ingest.folder}")
	private String bmIngestDownloadFolder;
	
	@Autowired
	BenchmarkDao benchmarkDao;
	
	@Autowired
	BenchmarkSubmtStatusService bmSubmtStatusService;
	
	@Autowired
	BenchmarkDatasetSubmitStagesDao bmsubmtStagesDao;
	
	@KafkaListener(groupId = "${kafka.ulca.bm.ingest.ip.topic.group.id}", topics = "${kafka.ulca.bm.ingest.ip.topic}", containerFactory = "benchmarkIngestafkaListenerContainerFactory")
	public void ingestBenchmark(BenchmarkIngest bmIngest) {

		log.info("************ Entry KafkaBenchmarkIngestConsumer :: ingestBenchmark *********");

		try {

			String benchmarkId = bmIngest.getBenchmarkId();
			String serviceRequestNumber = bmIngest.getServiceRequestNumber();
			
			List<BenchmarkDatasetSubmitStages> list = bmsubmtStagesDao.findAllByServiceRequestNumber(serviceRequestNumber);
			if(list != null && list.size() > 0) {
				log.info("Benchmark Dataset Submit already in progress. serviceRequestNumber :: " + serviceRequestNumber);
				return;
			}
			bmSubmtStatusService.updateStatus(serviceRequestNumber, BenchmarkDatasetSubmitStatus.StatusEnum.inprogress);
			bmSubmtStatusService.createStages(serviceRequestNumber, BenchmarkDatasetSubmitStages.ToolEnum.download, BenchmarkDatasetSubmitStages.StatusEnum.inprogress);
			
			Map<String,String> fileMap = null;
			Benchmark benchmark = null;
			
			try {
				
				String downloadFolder = bmIngestDownloadFolder + "/benchmark";
				Path targetLocation = Paths.get(downloadFolder).toAbsolutePath().normalize();

				try {
					Files.createDirectories(targetLocation);
				} catch (Exception ex) {
					throw new IOException("Could not create the directory where the benchmark-dataset downloaded files will be stored.", ex);
				}
				
				Optional<Benchmark> benchmarkOpt = benchmarkDao.findById(benchmarkId);
				benchmark = benchmarkOpt.get();
				String datasetUrl = benchmark.getDataset();
				
				String fileName =  benchmarkId + ".zip";
				
				String filePath = downloadUsingNIO(datasetUrl, downloadFolder, fileName);
				log.info("filePath :: " + filePath);
				
				log.info("serviceRequestNumber :: " + serviceRequestNumber);
				fileMap = unzipUtility.unzip(filePath, downloadFolder, serviceRequestNumber);
				
			}catch (IOException ex) {
				
				BenchmarkError error = new BenchmarkError();
				error.setCause(ex.getMessage());
				error.setMessage("file download failed");
				error.setCode("1000_FILE_DOWNLOAD_FAILURE");
				bmSubmtStatusService.updateStatus(serviceRequestNumber, BenchmarkDatasetSubmitStatus.StatusEnum.failed);
				bmSubmtStatusService.updateStagesWithErrorAndEndTime(serviceRequestNumber, BenchmarkDatasetSubmitStages.ToolEnum.download,BenchmarkDatasetSubmitStages.StatusEnum.failed, error);
				
				ex.printStackTrace();
				
				//update the benchmark dataset status 
				benchmark.setStatus(BenchmarkSubmissionType.FAILED.toString());
				benchmarkDao.save(benchmark);
				
				return;
			}
			bmSubmtStatusService.updateStages(serviceRequestNumber, BenchmarkDatasetSubmitStages.ToolEnum.download, BenchmarkDatasetSubmitStages.StatusEnum.completed);
			
			bmSubmtStatusService.createStages(serviceRequestNumber, BenchmarkDatasetSubmitStages.ToolEnum.validate, BenchmarkDatasetSubmitStages.StatusEnum.inprogress);
			
			String paramsFilePath = fileMap.get("baseLocation")  + File.separator + "params.json";
			
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File(paramsFilePath);
			Object obj = objectMapper.readValue(file, Object.class);
			
			String benchmarkJsonStr = objectMapper.writeValueAsString(obj);
			
			JSONObject params =  new JSONObject(benchmarkJsonStr);
			if(params.has("description")) {
				benchmark.setDescription(params.getString("description"));
			}
			if(params.has("submitter")) {
				Submitter submitter = objectMapper.readValue(params.get("submitter").toString(), Submitter.class);
				benchmark.setSubmitter(submitter);
			}
			if(params.has("domain")) {
				Domain domain = objectMapper.readValue(params.get("domain").toString(), Domain.class);
				benchmark.setDomain(domain);
			}
			if(params.has("languages")) {
				
				LanguagePair languages = objectMapper.readValue(params.get("languages").toString(), LanguagePair.class);
				benchmark.setLanguages(languages);
			}
			if(params.has("taskType")) {
				
				ModelTask.TypeEnum type = ModelTask.TypeEnum.fromValue(params.getString("taskType"));
				ModelTask task = new ModelTask();
				task.setType(type);
				benchmark.setTask(task);
				
			}
			benchmark.setStatus(BenchmarkSubmissionType.COMPLETED.toString());
			
			benchmarkDao.save(benchmark);
			bmSubmtStatusService.updateStatus(serviceRequestNumber, BenchmarkDatasetSubmitStatus.StatusEnum.completed);
			bmSubmtStatusService.updateStages(serviceRequestNumber, BenchmarkDatasetSubmitStages.ToolEnum.validate, BenchmarkDatasetSubmitStages.StatusEnum.completed);
			

		} catch (Exception ex) {
			log.info("error in listener");
			ex.printStackTrace();
		}
		
	}
	private String downloadUsingNIO(String urlStr, String downloadFolder, String fileName) throws IOException {
		log.info("************ Entry KafkaBenchmarkIngestConsumer :: downloadUsingNIO *********");
		log.info("url :: " + urlStr);
		URL url = new URL(urlStr);
		String file = downloadFolder + "/" + fileName;
		log.info("file path indownloadUsingNIO");
		log.info(file);
		log.info(url.getPath());
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		log.info(url.getContent().toString());
		log.info(rbc.getClass().toString());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();

		log.info("************ Exit KafkaBenchmarkDownloadConsumer :: downloadUsingNIO *********");
		return file;
	}

}
