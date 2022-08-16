package com.ulca.dataset.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.dataset.dao.DatasetKafkaTransactionErrorLogDao;
import com.ulca.dataset.kakfa.model.FileDownload;
import com.ulca.dataset.model.DatasetKafkaTransactionErrorLog;
import com.ulca.dataset.model.ProcessTracker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, FileDownload> datasetFiledownloadKafkaTemplate;


    @Autowired
    DatasetKafkaTransactionErrorLogDao datasetKafkaTransactionErrorLogDao;
    public void   datasetFiledownload(String fileDownloadTopic, FileDownload fileDownload, ProcessTracker processTracker) {
        try {

            ListenableFuture<SendResult<String, FileDownload>> future = datasetFiledownloadKafkaTemplate.send(fileDownloadTopic, fileDownload);

            future.addCallback(new ListenableFutureCallback<SendResult<String, FileDownload>>() {

                public void onSuccess(SendResult<String, FileDownload> result) {
                    log.info("DatasetService :: datasetSubmit onSuccess message sent successfully to fileDownloadTopic, serviceRequestNumber :: " + processTracker.getServiceRequestNumber());
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.info("DatasetService :: datasetSubmit onFailure Error occured while sending message to fileDownloadTopic, serviceRequestNumber :: " + processTracker.getServiceRequestNumber());
                    log.info("Error message :: " + ex.getMessage());

                    DatasetKafkaTransactionErrorLog error = new DatasetKafkaTransactionErrorLog();
                    error.setServiceRequestNumber(processTracker.getServiceRequestNumber());
                    error.setAttempt(0);
                    error.setCreatedOn(new Date().toString());
                    error.setLastModifiedOn(new Date().toString());
                    error.setFailed(false);
                    error.setSuccess(false);
                    error.setStage("download");
                    List<String> er = new ArrayList<String>();
                    er.add(ex.getMessage());
                    error.setErrors(er);
                    ObjectMapper mapper = new ObjectMapper();

                    String dataRow;
                    try {
                        dataRow = mapper.writeValueAsString(fileDownload);
                        error.setData(dataRow);
                        datasetKafkaTransactionErrorLogDao.save(error);

                    } catch (JsonProcessingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            });


        } catch (KafkaException ex) {
            log.info("DatasetService :: datasetSubmit Error occured while sending message to fileDownloadTopic, serviceRequestNumber :: " + processTracker.getServiceRequestNumber());
            log.info("Error message :: " + ex.getMessage());
            DatasetKafkaTransactionErrorLog error = new DatasetKafkaTransactionErrorLog();
            error.setServiceRequestNumber(processTracker.getServiceRequestNumber());
            error.setAttempt(0);
            error.setCreatedOn(new Date().toString());
            error.setLastModifiedOn(new Date().toString());
            error.setFailed(false);
            error.setSuccess(false);
            error.setStage("download");
            List<String> er = new ArrayList<String>();
            er.add(ex.getMessage());
            error.setErrors(er);
            ObjectMapper mapper = new ObjectMapper();

            String dataRow;
            try {
                dataRow = mapper.writeValueAsString(fileDownload);
                error.setData(dataRow);
                datasetKafkaTransactionErrorLogDao.save(error);

            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            throw ex;
        }
    }

}
