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
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.kafka.model.BmDatasetDownload;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.service.TranslationBenchmark;
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
	BenchmarkProcessDao benchmarkProcessDao;

	@Autowired
	TranslationBenchmark translationBenchmark;

	@KafkaListener(groupId = "${kafka.ulca.bm.filedownload.ip.topic.group.id}", topics = "${kafka.ulca.bm.filedownload.ip.topic}", containerFactory = "benchmarkDownloadKafkaListenerContainerFactory")
	public void downloadBenchmarkDataset(BmDatasetDownload bmDsDownload) {

		log.info("************ Entry KafkaBenchmarkDownloadConsumer :: downloadBenchmarkDataset *********");

		try {

			String benchmarkProcessId = bmDsDownload.getBenchmarkProcessId();
			String downloadFolder = bmDsDownloadFolder + "/benchmark-dataset";

			List<BenchmarkProcess> bmProcessList = benchmarkProcessDao.findByBenchmarkProcessId(benchmarkProcessId);

			for (BenchmarkProcess bmProcess : bmProcessList) {

				String bmDatasetId = bmProcess.getBenchmarkDatasetId();
				String fileName = benchmarkProcessId + bmDatasetId + ".zip";

				String modelId = bmProcess.getModelId();
				Optional<ModelExtended> modelOpt = modelDao.findById(modelId);
				ModelExtended model = modelOpt.get();

				Optional<Benchmark> benchmarkOpt = benchmarkDao.findById(bmDatasetId);
				Benchmark benchmark = benchmarkOpt.get();
				String datasetUrl = benchmark.getDataset();

				try {
					String filePath = downloadUsingNIO(datasetUrl, downloadFolder, fileName);

					log.info("filePath :: " + filePath);
					

					Map<String, String> fileMap = null;

					String serviceRequestNumber = benchmarkProcessId + bmDatasetId;
					
					log.info("serviceRequestNumber :: " + serviceRequestNumber);
					

					fileMap = unzipUtility.unzip(filePath, downloadFolder, serviceRequestNumber);

					ModelTask.TypeEnum type = model.getTask().getType();

					switch (type) {
					case TRANSLATION:
						log.info("modelTaskType :: " + ModelTask.TypeEnum.TRANSLATION.toString());
						
						translationBenchmark.prepareAndPushToMetric(model, benchmark, fileMap, bmProcess.getMetric(),
								benchmarkProcessId);
						break;
					case ASR:

						break;

					case OCR:

						break;

					default:

						break;

					}

					log.info(filePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (Exception ex) {
			log.info("error in listener");
			ex.printStackTrace();
		}

	}

	private String downloadUsingNIO(String urlStr, String downloadFolder, String fileName) throws IOException {
		log.info("************ Entry KafkaBenchmarkDownloadConsumer :: downloadUsingNIO *********");
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
