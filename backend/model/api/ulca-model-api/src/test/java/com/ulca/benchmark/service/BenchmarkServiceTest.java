package com.ulca.benchmark.service;

import com.ulca.benchmark.dao.BenchmarkDao;
import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.kafka.model.BenchmarkIngest;
import com.ulca.benchmark.kafka.model.BmDatasetDownload;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.request.*;
import com.ulca.benchmark.response.*;
import com.ulca.benchmark.util.ModelConstants;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.response.BmProcessListByProcessIdResponse;
import io.swagger.model.Benchmark;
import io.swagger.model.LanguagePair;
import io.swagger.model.LanguagePairs;
import io.swagger.model.ModelTask;
import io.swagger.model.SupportedLanguages;
import io.swagger.model.SupportedTasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BenchmarkServiceTest {
    @InjectMocks
    BenchmarkService benchmarkService;

    @Mock
    BenchmarkDao benchmarkDao;

    @Mock
    BenchmarkProcessDao benchmarkProcessDao;

    @Mock
    ModelDao modelDao;

    @Mock
    BenchmarkSubmtStatusService bmSubmitStatusService;

    @Mock
    KafkaTemplate<String, BenchmarkIngest> benchmarkIngestKafkaTemplate;

    @Mock
    KafkaTemplate<String, BmDatasetDownload> benchmarkDownloadKafkaTemplate;

    @Value("${kafka.ulca.bm.filedownload.ip.topic}")
    String benchmarkDownloadTopic;

    @BeforeEach
    void setUp() {
    }

    @Test
    void submitBenchmark() {
        BenchmarkSubmitRequest request = new BenchmarkSubmitRequest("test","test","test");

        when(benchmarkDao.findByName("test")).thenReturn(null);
        assertInstanceOf(BenchmarkSubmitResponse.class,benchmarkService.submitBenchmark(request));
    }

    @Test
    void executeBenchmark() {
        ExecuteBenchmarkRequest request = new ExecuteBenchmarkRequest();
        request.setModelId("test");
        Benchmark benchmark = new Benchmark();
        benchmark.setBenchmarkId("test");
        benchmark.setStatus("test");

        BenchmarkMetricRequest benchmarkMetricRequest = new BenchmarkMetricRequest();
        benchmarkMetricRequest.setBenchmarkId("test");
        benchmarkMetricRequest.setMetric("test");

        request.setBenchmarks(Collections.singletonList(benchmarkMetricRequest));


        ModelExtended modelExtended = new ModelExtended();

        BenchmarkProcess benchmarkProcess = new BenchmarkProcess();
        benchmarkProcess.setBenchmarkProcessId("test");
        benchmarkProcess.setStatus("test");

        when(modelDao.findById("test")).thenReturn(Optional.of(modelExtended));
        when(benchmarkDao.findByBenchmarkId("test")).thenReturn(benchmark);
        when(benchmarkProcessDao.findByModelIdAndBenchmarkDatasetIdAndMetric("test","test","test"))
                .thenReturn(Collections.singletonList(benchmarkProcess));

        assertInstanceOf(ExecuteBenchmarkResponse.class,benchmarkService.executeBenchmark(request));


    }

    @Test
    void executeBenchmarkAllMetric() {
        ExecuteBenchmarkAllMetricRequest request = new ExecuteBenchmarkAllMetricRequest();
        request.setModelId("test");
        request.setBenchmarkId("test");

        Benchmark benchmark = new Benchmark();
        benchmark.setBenchmarkId("test");

        ModelTask modelTask = new ModelTask();
        modelTask.setType(SupportedTasks.TRANSLATION);
        benchmark.setTask(modelTask);

        ModelConstants modelConstants = new ModelConstants();


        ReflectionTestUtils.setField(benchmarkService,"modelConstants",modelConstants);



        ModelExtended modelExtended = new ModelExtended();

        BenchmarkProcess benchmarkProcess = new BenchmarkProcess();
        benchmarkProcess.setBenchmarkProcessId("test");
        benchmarkProcess.setStatus("test");

        when(modelDao.findById("test")).thenReturn(Optional.of(modelExtended));
        when(benchmarkDao.findByBenchmarkId("test")).thenReturn(benchmark);
        when(benchmarkProcessDao.findByModelIdAndBenchmarkDatasetIdAndMetric("test","test",modelConstants.getMetricListByModelTask("translation").get(0)))
                .thenReturn(Collections.singletonList(benchmarkProcess));

        assertInstanceOf(ExecuteBenchmarkResponse.class,benchmarkService.executeBenchmarkAllMetric(request));


    }

    @Test
    void listByTaskID() {
        BenchmarkListByModelRequest request = new BenchmarkListByModelRequest();
        request.setModelId("test");

        ModelExtended modelExtended = new ModelExtended();
        LanguagePairs languagePairs = new LanguagePairs();
        LanguagePair languagePair = new LanguagePair();
        languagePair.setSourceLanguage(SupportedLanguages.EN);
        languagePair.setTargetLanguage(SupportedLanguages.HI);
        languagePairs.add(languagePair);
        modelExtended.setLanguages(languagePairs);

        Benchmark benchmark = new Benchmark();
        benchmark.setBenchmarkId("test");

        ModelTask modelTask = new ModelTask();
        modelTask.setType(SupportedTasks.TRANSLATION);
        benchmark.setTask(modelTask);

        BenchmarkProcess benchmarkProcess = new BenchmarkProcess();
        benchmarkProcess.setBenchmarkProcessId("test");
        benchmarkProcess.setStatus("test");

        ModelConstants modelConstants = new ModelConstants();


        ReflectionTestUtils.setField(benchmarkService,"modelConstants",modelConstants);


        when(modelDao.findByModelId("test")).thenReturn(modelExtended);
        when(benchmarkDao.findByTaskAndLanguages(modelExtended.getTask(),languagePair)).thenReturn(Collections.singletonList(benchmark));
        when(benchmarkProcessDao.findByModelIdAndBenchmarkDatasetId("test","test")).thenReturn(Collections.singletonList(benchmarkProcess));

       assertInstanceOf(BenchmarkListByModelResponse.class,benchmarkService.listByTaskID(request));


    }

    private static Stream<Arguments> searchBenchmarkParam(){
        BenchmarkSearchRequest request = new BenchmarkSearchRequest();
        request.setTask("translation");
        request.setSourceLanguage("en");
        request.setTargetLanguage("hi");

        return Stream.of(Arguments.of(request,1,1),
                         Arguments.of(request,null,null));
    }

    @ParameterizedTest
    @MethodSource("searchBenchmarkParam")
    void searchBenchmark(BenchmarkSearchRequest request, Integer startPage, Integer endPage) {
        ModelTask modelTask = null;
        LanguagePair lp = null;

        if (request.getTask() != null && !request.getTask().isBlank()) {
            modelTask = new ModelTask();
            modelTask.setType(SupportedTasks.fromValue(request.getTask()));

        }

        if (request.getSourceLanguage() != null && !request.getSourceLanguage().isBlank()) {
            lp = new LanguagePair();
            lp.setSourceLanguage(SupportedLanguages.fromValue(request.getSourceLanguage()));

            if (request.getTargetLanguage() != null && !request.getTargetLanguage().isBlank()) {
                lp.setTargetLanguage(SupportedLanguages.fromValue(request.getTargetLanguage()));
            }

        }

        Pageable paging = PageRequest.of(0, 10);


            if(lp!=null && modelTask!=null) {
                if (startPage != null) {
                    when(benchmarkDao.findByTaskAndLanguages(modelTask, lp, paging)).thenReturn(Collections.singletonList(new Benchmark()));
                } else
                    when(benchmarkDao.findByTaskAndLanguages(modelTask, lp)).thenReturn(Collections.singletonList(new Benchmark()));
             }
            assertEquals(new BenchmarkSearchResponse("Benchmark Search Result", Collections.singletonList(new Benchmark()),1),benchmarkService.searchBenchmark(request,startPage,endPage));

    }

    @Test
    void processStatus() {

        List<BenchmarkProcess> list = Collections.singletonList(new BenchmarkProcess());

        when(benchmarkProcessDao.findByBenchmarkProcessId("test")).thenReturn(new BenchmarkProcess());
        assertEquals(new BmProcessListByProcessIdResponse("Benchmark Process list", list,
                list.size()),benchmarkService.processStatus("test"));
    }

    @Test
    void getBenchmarkById() {

        Benchmark benchmark = new Benchmark();
        ModelTask modelTask = new ModelTask();
        modelTask.setType(SupportedTasks.TRANSLATION);
        benchmark.setTask(modelTask);


        BenchmarkProcess benchmarkProcess = new BenchmarkProcess();
        ModelConstants modelConstants = new ModelConstants();
        ReflectionTestUtils.setField(benchmarkService,"modelConstants",modelConstants);
        benchmarkProcess.setMetric(modelConstants.getMetricListByModelTask("translation").toString());
        benchmarkProcess.setStatus("Completed");
        benchmarkProcess.setModelId("test");

        ModelExtended modelExtended = new ModelExtended();
        modelExtended.setName("test");
        modelExtended.setStatus("unpublished");
        modelExtended.setVersion("test");

        GetBenchmarkByIdResponse bmDto = new GetBenchmarkByIdResponse();
        BeanUtils.copyProperties(benchmark, bmDto);
        bmDto.setMetric(modelConstants.getMetricListByModelTask("translation"));

        bmDto.setBenchmarkPerformance(Collections.EMPTY_LIST);

        when(benchmarkDao.findByBenchmarkId("test")).thenReturn(benchmark);
        when(benchmarkProcessDao.findByBenchmarkDatasetId("test")).thenReturn(Collections.singletonList(benchmarkProcess));

        assertEquals(bmDto,benchmarkService.getBenchmarkById("test"));

    }
    private static Stream<Arguments> benchmarkListByUserIdParam(){

        return Stream.of(Arguments.of("test",1,1,null,null),
                Arguments.of("test",null,null,null,null));
    }


    @ParameterizedTest
    @MethodSource("benchmarkListByUserIdParam")
    void benchmarkListByUserId(String userId, Integer startPage, Integer endPage,Integer pgSize,String name) {

        List<Benchmark> list = Collections.singletonList(new Benchmark());
        if (startPage!=null) {
            Pageable paging = PageRequest.of(0, 10, Sort.by("submittedOn").descending());
            Page page = new PageImpl(list);
            when(benchmarkDao.findByUserId("test", paging)).thenReturn(page);
        } else
            when(benchmarkDao.findByUserId(userId)).thenReturn(list);

        assertInstanceOf(BenchmarkListByUserIdResponse.class,
                benchmarkService.benchmarkListByUserId(userId,startPage,endPage,pgSize,name));
    }
}