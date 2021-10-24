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
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.model.BenchmarkSubmissionType;
import com.ulca.benchmark.util.UnzipUtility;
import com.ulca.model.dao.ModelExtended;

import io.swagger.model.Benchmark;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.LanguagePairs;
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
	
	
	@KafkaListener(groupId = "${kafka.ulca.bm.ingest.ip.topic.group.id}", topics = "${kafka.ulca.bm.ingest.ip.topic}", containerFactory = "benchmarkIngestafkaListenerContainerFactory")
	public void ingestBenchmark(BenchmarkIngest bmIngest) {

		log.info("************ Entry KafkaBenchmarkIngestConsumer :: ingestBenchmark *********");

		try {

			String benchmarkId = bmIngest.getBenchmarkId();
			String downloadFolder = bmIngestDownloadFolder + "/benchmark";
			
			Path targetLocation = Paths.get(downloadFolder).toAbsolutePath().normalize();

			try {
				Files.createDirectories(targetLocation);
			} catch (Exception ex) {
				throw new Exception("Could not create the directory where the benchmark-dataset downloaded files will be stored.", ex);
			}
			

			Optional<Benchmark> benchmarkOpt = benchmarkDao.findById(benchmarkId);
			Benchmark benchmark = benchmarkOpt.get();
			String datasetUrl = benchmark.getDataset();
			
			String fileName =  benchmarkId + ".zip";
			
			String filePath = downloadUsingNIO(datasetUrl, downloadFolder, fileName);
			log.info("filePath :: " + filePath);
			
			String serviceRequestNumber =  benchmarkId;
			log.info("serviceRequestNumber :: " + serviceRequestNumber);
			Map<String, String> fileMap = unzipUtility.unzip(filePath, downloadFolder, serviceRequestNumber);
			
			String paramsFilePath = fileMap.get("baseLocation")  + File.separator + "params.json";
			
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File(paramsFilePath);
			Object obj = objectMapper.readValue(file, Object.class);
			String benchmarkJsonStr = objectMapper.writeValueAsString(obj);
			
			JSONObject params =  new JSONObject(benchmarkJsonStr);
			if(params.has("description")) {
				benchmark.setDataset(params.getString("description"));
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
			benchmark.setStatus(BenchmarkSubmissionType.COMPLETED.toString());
			
			benchmarkDao.save(benchmark);
			

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
