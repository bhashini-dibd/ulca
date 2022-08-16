package com.ulca.dataset.service;

import com.ulca.dataset.dao.*;
import com.ulca.dataset.kakfa.model.FileDownload;
import com.ulca.dataset.model.Dataset;
import com.ulca.dataset.model.Fileidentifier;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.request.DatasetSubmitRequest;
import com.ulca.dataset.request.SearchCriteria;
import com.ulca.dataset.response.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)

@SpringBootTest
class DatasetServiceTest {

    @InjectMocks
    DatasetService datasetService;

    @Mock
    DatasetDao datasetDao;


    @Mock
    FileIdentifierDao fileIdentifierDao;

    @Mock
    ProcessTrackerDao processTrackerDao;

    @Mock
    DatasetKafkaTransactionErrorLogDao datasetKafkaTransactionErrorLogDao;

    @Mock
    TaskTrackerDao taskTrackerDao;

    @Mock
    SearchKafkaPublishService searchKafkaPublish;


    @Value("${kafka.ulca.ds.filedownload.ip.topic}")
    private String fileDownloadTopic;

    @Mock
    KafkaService kafkaService;



    @Test
    void datasetSubmit() throws InterruptedException {
        DatasetSubmitRequest request = new DatasetSubmitRequest("test","test","test");


        ReflectionTestUtils.setField(datasetService,"fileDownloadTopic",fileDownloadTopic);

        assertInstanceOf(DatasetSubmitResponse.class,datasetService.datasetSubmit(request));

    }
    private static Stream<Arguments> datasetListByUserIdPaginationParam(){

        return Stream.of(Arguments.of("Failed"),
                Arguments.of("In-Progress"),
                Arguments.of("publish"));
    }

    @ParameterizedTest
    @MethodSource("datasetListByUserIdPaginationParam")
    void datasetListByUserIdPagination(String status) {
        ProcessTracker processTracker = new ProcessTracker();
        processTracker.setDatasetId("test");
        processTracker.setUserId("test");
        processTracker.setStatus(status);
        processTracker.serviceRequestNumber("1");

        Dataset dataset = new Dataset();
        dataset.setDatasetName("test");
        dataset.setDatasetType("test");
        dataset.setCreatedOn(String.valueOf(new Date()));

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setTool("test");
        taskTracker.setStatus(status);

        if(!status.equalsIgnoreCase("Failed")){
           when(taskTrackerDao.findAllByServiceRequestNumber("1")).thenReturn(Collections.singletonList(taskTracker));
        }

        Page<ProcessTracker> processTrackerPage = new PageImpl<>(Collections.singletonList(processTracker));
        when(datasetDao.findById("test")).thenReturn(Optional.of(dataset));
        when(processTrackerDao.findByUserId("test", PageRequest.of(0,10))).thenReturn(processTrackerPage);

        assertInstanceOf(DatasetListByUserIdResponse.class,datasetService.datasetListByUserIdPagination("test",1,1,null));

    }

    @Test
    void datasetListByUserId() {
        assertInstanceOf(DatasetListByUserIdResponse.class,datasetService.datasetListByUserId("test",null,null,null));

    }
    private static Stream<Arguments> datasetListByUserIdFetchAllParam(){

        return Stream.of(Arguments.of("Failed"),
                Arguments.of("In-Progress"),
                Arguments.of("publish"));
    }

    @ParameterizedTest
    @MethodSource("datasetListByUserIdFetchAllParam")
    void datasetListByUserIdFetchAll(String status) {
        ProcessTracker processTracker = new ProcessTracker();
        processTracker.setDatasetId("test");
        processTracker.setUserId("test");
        processTracker.setStatus(status);
        processTracker.serviceRequestNumber("1");

        Dataset dataset = new Dataset();
        dataset.setDatasetName("test");
        dataset.setDatasetType("test");
        dataset.setCreatedOn(String.valueOf(new Date()));

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setTool("test");
        taskTracker.setStatus(status);

        if(!status.equalsIgnoreCase("Failed")){
            when(taskTrackerDao.findAllByServiceRequestNumber("1")).thenReturn(Collections.singletonList(taskTracker));
        }

        when(datasetDao.findById("test")).thenReturn(Optional.of(dataset));
        when(processTrackerDao.findByUserId("test")).thenReturn(Collections.singletonList(processTracker));

        assertInstanceOf(DatasetListByUserIdResponse.class,datasetService.datasetListByUserIdFetchAll("test",null));

    }
    private static Stream<Arguments> datasetByIdParam(){
        ProcessTracker processTracker = new ProcessTracker();
        processTracker.setDatasetId("test");
        processTracker.setUserId("test");
        processTracker.setStatus("In-Progress");
        processTracker.serviceRequestNumber("1");

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setTool("test");
        taskTracker.setStatus("In-Progress");

        TaskTracker taskTracker1 = new TaskTracker();
        taskTracker1.setTool("download");
        taskTracker1.setStatus("In-Progress");

        TaskTracker taskTracker2 = new TaskTracker();
        taskTracker2.setTool("download");
        taskTracker2.setStatus("Failed");

        return Stream.of(Arguments.of(processTracker,taskTracker),
                Arguments.of(processTracker,taskTracker1),
                Arguments.of(processTracker,taskTracker2)


        );
    }
    @ParameterizedTest
    @MethodSource("datasetByIdParam")
    void datasetById(ProcessTracker processTracker,TaskTracker taskTracker) {

        when(processTrackerDao.findByDatasetId("test")).thenReturn(Collections.singletonList(processTracker));

        List<TaskTracker> taskTrackerList = new ArrayList<>();
        taskTrackerList.add(taskTracker);

        when(taskTrackerDao.findAllByServiceRequestNumber("1")).thenReturn(taskTrackerList);
        assertInstanceOf(DatasetByIdResponse.class,datasetService.datasetById("test"));


    }

    @Test
    void datasetByServiceRequestNumber() {

        ProcessTracker processTracker = new ProcessTracker();
        processTracker.setDatasetId("test");
        processTracker.setUserId("test");
        processTracker.setStatus("In-Progress");
        processTracker.serviceRequestNumber("1");

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setTool("test");
        taskTracker.setStatus("In-Progress");

        when(processTrackerDao.findByServiceRequestNumber("1")).thenReturn(processTracker);


        List<TaskTracker> taskTrackerList = new ArrayList<>();
        taskTrackerList.add(taskTracker);


        when(taskTrackerDao.findAllByServiceRequestNumber("1")).thenReturn(taskTrackerList);
        assertInstanceOf(DatasetByServiceReqNrResponse.class, datasetService.datasetByServiceRequestNumber("1"));
    }

        @Test
    void corpusSearch() {
    }

    @Test
    void searchStatus() {
        ProcessTracker processTracker = new ProcessTracker();
        processTracker.setDatasetId("test");
        processTracker.setUserId("test");
        processTracker.setStatus("In-Progress");
        processTracker.serviceRequestNumber("1");

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setTool("test");
        taskTracker.setStatus("In-Progress");

        when(processTrackerDao.findByServiceRequestNumber("1")).thenReturn(processTracker);


        List<TaskTracker> taskTrackerList = new ArrayList<>();
        taskTrackerList.add(taskTracker);


        when(taskTrackerDao.findAllByServiceRequestNumber("1")).thenReturn(taskTrackerList);
        assertInstanceOf(DatasetSearchStatusResponse.class, datasetService.searchStatus("1"));
    }

    private static Stream<Arguments> searchListByUserIdParam(){
        return Stream.of(Arguments.of("test",null,null),
                Arguments.of("test",1,1));
    }

    @ParameterizedTest
    @MethodSource("searchListByUserIdParam")
    void searchListByUserId(String userId, Integer startPage, Integer endPage) {
        ProcessTracker processTracker = new ProcessTracker();
        processTracker.setDatasetId("test");
        processTracker.setUserId("test");
        processTracker.setStatus("In-Progress");
        processTracker.serviceRequestNumber("1");

        processTracker.setSearchCriterion(new SearchCriteria());

        List<ProcessTracker> processTrackerList = new ArrayList<>();
        processTrackerList.add(processTracker);

        TaskTracker taskTracker = new TaskTracker();
        taskTracker.setTool("test");
        taskTracker.setStatus("In-Progress");

        List<TaskTracker> taskTrackerList = new ArrayList<>();
        taskTrackerList.add(taskTracker);


        Page<ProcessTracker> processTrackerPage = new PageImpl<>(Collections.singletonList(processTracker));


        when(taskTrackerDao.findAllByServiceRequestNumber("1")).thenReturn(taskTrackerList);
        if (startPage!=null) {
            when(processTrackerDao.findByUserIdAndServiceRequestTypeAndServiceRequestAction(userId, ProcessTracker.ServiceRequestTypeEnum.dataset, ProcessTracker.ServiceRequestActionEnum.search, PageRequest.of(0, 10))).thenReturn(processTrackerPage);
        } else
          when(processTrackerDao.findByUserIdAndServiceRequestTypeAndServiceRequestAction(userId, ProcessTracker.ServiceRequestTypeEnum.dataset, ProcessTracker.ServiceRequestActionEnum.search)).thenReturn(processTrackerList);

        assertInstanceOf(SearchListByUserIdResponse.class,datasetService.searchListByUserId(userId,startPage,endPage));
    }


}