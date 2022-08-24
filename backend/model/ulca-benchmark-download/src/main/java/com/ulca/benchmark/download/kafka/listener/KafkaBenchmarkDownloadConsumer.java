package com.ulca.benchmark.download.kafka.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.dao.BenchmarkTaskTrackerDao;
import com.ulca.benchmark.kafka.model.BmDatasetDownload;
import com.ulca.benchmark.model.BenchmarkError;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.model.BenchmarkTaskTracker;
import com.ulca.benchmark.service.AsrBenchmark;
import com.ulca.benchmark.service.BmProcessTrackerService;
import com.ulca.benchmark.service.NotificationService;
import com.ulca.benchmark.service.OcrBenchmark;
import com.ulca.benchmark.service.TranslationBenchmark;
import com.ulca.benchmark.service.TransliterationBenchmark;
import com.ulca.benchmark.util.UnzipUtility;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;

import io.swagger.model.Benchmark;
import io.swagger.model.ModelTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaBenchmarkDownloadConsumer {

	@Autowired
	UnzipUtility unzipUtility;

	@Value("${ulca.bm.ds.download.folder}")
	private String bmDsDownloadFolder;

	@Autowired
	ModelDao modelDao;

	@Autowired
	BenchmarkDao benchmarkDao;
	
	@Autowired
	BenchmarkTaskTrackerDao benchmarkTaskTrackerDao;
	
	@Autowired
	BmProcessTrackerService bmProcessTrackerService;

	@Autowired
	BenchmarkProcessDao benchmarkProcessDao;

	@Autowired
	TranslationBenchmark translationBenchmark;
	
	@Autowired
	TransliterationBenchmark transliterationBenchmark;
	
	@Autowired
	AsrBenchmark asrBenchmark;
	
	@Autowired
	OcrBenchmark ocrBenchmark;
	
	@Autowired
	NotificationService notificationService;


	@KafkaListener(groupId = "${kafka.ulca.bm.filedownload.ip.topic.group.id}", topics = "${kafka.ulca.bm.filedownload.ip.topic}", containerFactory = "benchmarkDownloadKafkaListenerContainerFactory")
	public void processBenchmark(BmDatasetDownload bmDsDownload) {
		
		log.info("************ Entry KafkaBenchmarkDownloadConsumer :: processBenchmark *********");
		
		try {
			
			Map<String, String> benchmarkProcessIdsMap = bmDsDownload.getBenchmarkProcessIdsMap();
			List<String> benchmarkProcessIdList =  new ArrayList<String>(benchmarkProcessIdsMap.keySet()); 
			
			List<BenchmarkTaskTracker> list = benchmarkTaskTrackerDao.findByBenchmarkProcessIdIn(benchmarkProcessIdList);

			if(list.size() > 0) {
				log.info("Duplicate Benchmark Process. Skipping Benchamrk Processing. benchmarkProcessId :: " + benchmarkProcessIdList);
				return;
			}
			
			bmProcessTrackerService.createTaskTracker(benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum.download, BenchmarkTaskTracker.StatusEnum.inprogress);
			
			String downloadFolder = bmDsDownloadFolder + "/benchmark-dataset";
			
			Path targetLocation = Paths.get(downloadFolder).toAbsolutePath().normalize();

			try {
				Files.createDirectories(targetLocation);
			} catch (Exception ex) {
				BenchmarkError error = new BenchmarkError();
				error.setCause(ex.getMessage());
				error.setMessage("file download failed");
				error.setCode("2000_FILE_DOWNLOAD_FAILURE");
				bmProcessTrackerService.updateTaskTrackerWithErrorAndEndTime(benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum.download, BenchmarkTaskTracker.StatusEnum.failed, error);
				bmProcessTrackerService.updateBmProcess(benchmarkProcessIdList, "Failed");
				
				throw new Exception("Could not create the directory where the benchmark-dataset downloaded files will be stored.", ex);
			}
			
			String modelId = bmDsDownload.getModelId();
			Optional<ModelExtended> modelOpt = modelDao.findById(modelId);
			ModelExtended model = modelOpt.get();

			String bmDatasetId = bmDsDownload.getBenchmarkDatasetId();
			Optional<Benchmark> benchmarkOpt = benchmarkDao.findById(bmDatasetId);
			Benchmark benchmark = benchmarkOpt.get();
			String datasetUrl = benchmark.getDataset();
			
			Map<String, String> fileMap = null;
			
			try {
				
				fileMap = downloadUnzipBmDataset(bmDatasetId,datasetUrl, downloadFolder);
				
				
				
					bmProcessTrackerService.updateTaskTracker(benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum.download, BenchmarkTaskTracker.StatusEnum.completed);
				
			}catch (IOException e) {


					log.info("Benchmark Process Failed. benchmarkProcessIds :: " + benchmarkProcessIdList + " cause :: " + e.getMessage());

					BenchmarkError error = new BenchmarkError();
					error.setCause(e.getMessage());
					error.setMessage("file download failed");
					error.setCode("2000_FILE_DOWNLOAD_FAILURE");
					
					bmProcessTrackerService.updateTaskTrackerWithErrorAndEndTime(benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum.download, BenchmarkTaskTracker.StatusEnum.failed, error);
					bmProcessTrackerService.updateBmProcess(benchmarkProcessIdList, "Failed");
					
					notificationService.notifyBenchmarkFailed(modelId, model.getName(), model.getUserId());

				return;
			}
			
			try {
				
				bmProcessTrackerService.createTaskTracker(benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum.ingest, BenchmarkTaskTracker.StatusEnum.inprogress);
				bmProcessTrackerService.createTaskTracker(benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum.benchmark, BenchmarkTaskTracker.StatusEnum.inprogress);
				
				ModelTask.TypeEnum type = model.getTask().getType();

				switch (type) {
					case TRANSLATION:
						log.info("modelTaskType :: " + ModelTask.TypeEnum.TRANSLATION.toString());

						translationBenchmark.prepareAndPushToMetric(model, benchmark, fileMap,benchmarkProcessIdsMap);
								

						break;
					case ASR:
						log.info("modelTaskType :: " + ModelTask.TypeEnum.ASR.toString());

						asrBenchmark.prepareAndPushToMetric(model, benchmark, fileMap,benchmarkProcessIdsMap);
						break;

					case OCR:

						log.info("modelTaskType :: " + ModelTask.TypeEnum.OCR.toString());

						ocrBenchmark.prepareAndPushToMetric(model, benchmark, fileMap, benchmarkProcessIdsMap);
								
						break;

					case TRANSLITERATION:

						log.info("modelTaskType :: " + ModelTask.TypeEnum.TRANSLITERATION.toString());

						transliterationBenchmark.prepareAndPushToMetric(model, benchmark, fileMap, benchmarkProcessIdsMap);
						break;

					default:

						break;
				}
                
					bmProcessTrackerService.updateTaskTracker(benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum.ingest, BenchmarkTaskTracker.StatusEnum.completed);
				
			} catch (Exception e) {
				
				log.info("Benchmark Process Failed. benchmarkProcessIds :: "  + benchmarkProcessIdList + " cause :: " + e.getMessage());
				
				BenchmarkError error = new BenchmarkError();
				error.setCause(e.getMessage());
				error.setMessage("Benchmark Ingest Failed");
				error.setCode("2000_BENCHMARK_INGEST_FAILURE");
				bmProcessTrackerService.updateTaskTrackerWithErrorAndEndTime(benchmarkProcessIdList, BenchmarkTaskTracker.ToolEnum.ingest, BenchmarkTaskTracker.StatusEnum.failed, error);
				bmProcessTrackerService.updateBmProcess(benchmarkProcessIdList, "Failed");
				notificationService.notifyBenchmarkFailed(modelId,  model.getName(), model.getUserId());
				e.printStackTrace();
				
			}
			
		} catch (Exception ex) {
			log.info("error in listener");
			ex.printStackTrace();
		}
		
		log.info("************ Exit KafkaBenchmarkDownloadConsumer :: processBenchmark *********");
		
	}
	
	public Map<String, String> downloadUnzipBmDataset(String datasetId, String datasetUrl,  String downloadFolder) throws IOException{
		
		Map<String, String> fileMap = null;
		
		String fileName = datasetId+ ".zip";
		String filePath = downloadUsingNIO(datasetUrl, downloadFolder, fileName);

		log.info("filePath :: " + filePath);
		
		String serviceRequestNumber = datasetId ;
		
		log.info("serviceRequestNumber :: " + serviceRequestNumber);
		
		fileMap = unzipUtility.unzip(filePath, downloadFolder, serviceRequestNumber);
		
		
		return fileMap;
		
	}


	private String downloadUsingNIO(String urlStr, String downloadFolder, String fileName) throws IOException {
		log.info("************ Entry KafkaBenchmarkDownloadConsumer :: downloadUsingNIO *********");
		URL url = new URL(urlStr);
		String file = downloadFolder + "/" + fileName;
		log.info("file path in downloadUsingNIO");
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
