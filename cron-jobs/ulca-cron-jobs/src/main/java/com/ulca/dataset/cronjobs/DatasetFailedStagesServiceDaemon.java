package com.ulca.dataset.cronjobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.dataset.dao.DatasetKafkaTransactionErrorLogDao;
import com.ulca.dataset.kakfa.model.DatasetIngest;
import com.ulca.dataset.kakfa.model.FileDownload;
import com.ulca.dataset.model.DatasetKafkaTransactionErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Slf4j
@Component
public class DatasetFailedStagesServiceDaemon {

	@Autowired
	DatasetKafkaTransactionErrorLogDao datasetKafkaTransactionErrorLogDao;

	@Autowired
	private KafkaTemplate<String, FileDownload> datasetFiledownloadKafkaTemplate;

	@Value("${kafka.ulca.ds.filedownload.ip.topic}")
	private String fileDownloadTopic;
	
	
	@Autowired
	private KafkaTemplate<String, DatasetIngest> datasetIngestKafkaTemplate;

	@Value("${kafka.ulca.ds.ingest.ip.topic}")
	private String datasetIngestTopic;

	

	//@Scheduled(cron = "0 */1 * * * *")
	public void reIngestDataset() {

		
		List<DatasetKafkaTransactionErrorLog> errorList = datasetKafkaTransactionErrorLogDao.findByFailedAndSuccess(false,false);

		for (DatasetKafkaTransactionErrorLog error : errorList) {

			if (error.getStage().equalsIgnoreCase("download")) {
				String data = error.getData();
				ObjectMapper mapper = new ObjectMapper();
				try {
					FileDownload fileDownload = mapper.readValue(data, FileDownload.class);
					try {

						ListenableFuture<SendResult<String, FileDownload>> future = datasetFiledownloadKafkaTemplate
								.send(fileDownloadTopic, fileDownload);

						future.addCallback(new ListenableFutureCallback<SendResult<String, FileDownload>>() {

							public void onSuccess(SendResult<String, FileDownload> result) {
								log.info("DatasetFailedStagesServiceDaemon :: reIngestDataset - stage : download -  message sent successfully to fileDownloadTopic, serviceRequestNumber :: "
										+ error.getServiceRequestNumber());
								
								error.setSuccess(true);
								datasetKafkaTransactionErrorLogDao.save(error);
								
							}

							@Override
							public void onFailure(Throwable ex) {
								log.info(
										"DatasetFailedStagesServiceDaemon :: reIngestDataset - stage : download - Error occured while sending message to fileDownloadTopic, serviceRequestNumber :: "
												+ error.getServiceRequestNumber());
								log.info("Error message :: " + ex.getMessage());

								int faileCount = error.getAttempt();
								List<String> er = error.getErrors();
								er.add(ex.getMessage());
								
								if((faileCount + 1 ) >= 3) {
									error.setFailed(true);
								}
								error.setAttempt(faileCount + 1);
								error.setErrors(er);
								datasetKafkaTransactionErrorLogDao.save(error);

							}
						});

					} catch (KafkaException ex) {
						log.info("DatasetFailedStagesServiceDaemon :: reIngestDataset - stage : download - Error occured while sending message to fileDownloadTopic, serviceRequestNumber :: "
								+ error.getServiceRequestNumber());
						log.info("Error message :: " + ex.getMessage());

						int faileCount = error.getAttempt();
						List<String> er = error.getErrors();
						er.add(ex.getMessage());
						if((faileCount + 1 ) >= 3) {
							error.setFailed(true);
						}
						error.setAttempt(faileCount + 1);
						error.setErrors(er);
						datasetKafkaTransactionErrorLogDao.save(error);

					}

				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (error.getStage().equalsIgnoreCase("ingest")) {

				String data = error.getData();
				ObjectMapper mapper = new ObjectMapper();
				try {
					DatasetIngest datasetIngest = mapper.readValue(data, DatasetIngest.class);
					try {

						ListenableFuture<SendResult<String, DatasetIngest>> future = datasetIngestKafkaTemplate
								.send(datasetIngestTopic, datasetIngest);

						future.addCallback(new ListenableFutureCallback<SendResult<String, DatasetIngest>>() {

							public void onSuccess(SendResult<String, DatasetIngest> result) {
								log.info("DatasetFailedStagesServiceDaemon :: reIngestDataset - stage : ingest onSuccess - message sent successfully to datasetIngestTopic, serviceRequestNumber :: "
										+ error.getServiceRequestNumber());
								
								error.setSuccess(true);
								datasetKafkaTransactionErrorLogDao.save(error);
							}

							@Override
							public void onFailure(Throwable ex) {
								log.info(
										"DatasetFailedStagesServiceDaemon :: reIngestDataset - stage : ingest onFailure - Error occured while sending message to datasetIngestTopic, serviceRequestNumber :: "
												+ error.getServiceRequestNumber());
								log.info("Error message :: " + ex.getMessage());

								int faileCount = error.getAttempt();
								List<String> er = error.getErrors();
								er.add(ex.getMessage());
								if((faileCount + 1 ) >= 3) {
									error.setFailed(true);
								}
								error.setAttempt(faileCount + 1);
								error.setErrors(er);
								datasetKafkaTransactionErrorLogDao.save(error);

							}
						});

					} catch (KafkaException ex) {
						log.info("DatasetFailedStagesServiceDaemon :: reIngestDataset - stage : ingest - Error occured while sending message to datasetIngestTopic, serviceRequestNumber :: "
								+ error.getServiceRequestNumber());
						log.info("Error message :: " + ex.getMessage());

						int faileCount = error.getAttempt();
						List<String> er = error.getErrors();
						er.add(ex.getMessage());
						if((faileCount + 1 ) >= 3) {
							error.setFailed(true);
						}
						error.setAttempt(faileCount + 1);
						error.setErrors(er);
						datasetKafkaTransactionErrorLogDao.save(error);

					}

				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

}
