package com.ulca.benchmark.download.kafka.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.request.BenchmarkMetricRequest;
import com.ulca.benchmark.request.ExecuteBenchmarkRequest;
import com.ulca.benchmark.util.UnzipUtility;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;

import io.swagger.model.Benchmark;
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

	@KafkaListener(groupId = "${kafka.ulca.bm.filedownload.ip.topic.group.id}", topics = "${kafka.ulca.bm.filedownload.ip.topic}" , containerFactory = "benchmarkDownloadKafkaListenerContainerFactory")
	public void downloadBenchmarkDataset(ExecuteBenchmarkRequest benchmarkDownload) {
		
		log.info("************ Entry KafkaBenchmarkDownloadConsumer :: downloadBenchmarkDataset *********");
		
		
		try {
			
			String downloadFolder = bmDsDownloadFolder + "/benchmark-dataset";
			
			String metricId = benchmarkDownload.getModelId();
			
			 List<BenchmarkMetricRequest> benchmarks = benchmarkDownload.getBenchmarks();
			 
			 for(BenchmarkMetricRequest bmMetric : benchmarks) {
				 
				 String benchmarkId = bmMetric.getBenchmarkId();
				 
				 String fileName = metricId + benchmarkId+".zip";
				 
				 Optional<ModelExtended> modelOpt = modelDao.findById(metricId);
				 
				 ModelExtended model = modelOpt.get();
				 
				 Optional<Benchmark> benchmarkOpt = benchmarkDao.findById(benchmarkId);
				 
				 Benchmark benchmark = benchmarkOpt.get();
				 
				 String datasetUrl = benchmark.getDataset();
				 
				try {
					String filePath = downloadUsingNIO(datasetUrl, downloadFolder, fileName);
					
					log.info(filePath);
					
					Map<String,String> fileMap = null;
					
					String serviceRequestNumber = metricId + benchmarkId;
					
					fileMap = unzipUtility.unzip(filePath, downloadFolder, serviceRequestNumber);
					
					log.info(fileMap.toString());
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			 }
			
			 
			
		}catch (Exception ex) {
			log.info("error in listener");
			ex.printStackTrace();
		}
		
		
		
		
		
	}
	
	private String downloadUsingNIO(String urlStr, String downloadFolder, String fileName) throws IOException {
		log.info("************ Entry KafkaBenchmarkDownloadConsumer :: downloadUsingNIO *********");
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

		log.info("************ Exit KafkaBenchmarkDownloadConsumer :: downloadUsingNIO *********");
		return file;
	}
	
}