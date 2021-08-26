package com.ulca.benchmark.download.kafka.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ulca.benchmark.download.kafka.model.ExecuteBenchmarkRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaBenchmarkDownloadConsumer {
	
	
	@Value("${ulca.bm.ds.download.folder}")
	private String bmDsDownloadFolder;

	@KafkaListener(groupId = "${kafka.ulca.ds.filedownload.ip.topic.group.id}", topics = "${kafka.ulca.ds.filedownload.ip.topic}" , containerFactory = "filedownloadKafkaListenerContainerFactory")
	public void downloadBenchmarkDataset(ExecuteBenchmarkRequest benchmarkDownload) {
		
		
		String downloadFolder = bmDsDownloadFolder + "/benchmark-dataset";
		
		
		
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