package com.ulca.benchmark.service;

import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.request.AsrComputeRequest;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelInferenceResponseDao;
import io.swagger.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AsrBenchmarkTest {
    @InjectMocks
    AsrBenchmark asrBenchmark;

    @Mock
    KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;

    @Value("${ulca.bm.ds.download.folder}")
    private String modelUploadFolder;


    @Mock
    ModelInferenceResponseDao modelInferenceResponseDao;


    @Mock
    OkHttpClientService okHttpClientService;

    @Mock
    BenchmarkProcessDao benchmarkProcessDao;



    @Test
    void prepareAndPushToMetric() throws Exception {
        String baseLocation =  "src/test/resources/asr-benchmark-dataset";

        ASRRequest asrRequest = new ASRRequest();
        AudioConfig audioConfig = new AudioConfig();
        LanguagePair languagePair = new LanguagePair();
        languagePair.setSourceLanguage(LanguagePair.SourceLanguageEnum.EN);
        audioConfig.setLanguage(languagePair);
        asrRequest.setConfig(audioConfig);

        ModelExtended model = new ModelExtended();
        InferenceAPIEndPoint inferenceAPIEndPoint = new InferenceAPIEndPoint();
        inferenceAPIEndPoint.setCallbackUrl("https://test.com");
        ASRInference asrInference = new ASRInference();
        asrInference.setRequest(asrRequest);
        inferenceAPIEndPoint.setSchema(asrInference);
        model.setInferenceEndPoint(inferenceAPIEndPoint);

        ModelTask modelTask = new ModelTask();
        modelTask.setType(ModelTask.TypeEnum.ASR);

        model.setTask(modelTask);


        Benchmark benchmark = new Benchmark();


        Map<String,String> fileMap = new HashMap<>();
        fileMap.put("baseLocation",baseLocation);

        String metric = "cer";

        String benchmarkingProcessId = "1";
        Map<String, String> map = new HashMap<String, String>();
        map.put(benchmarkingProcessId, metric);
        

        when(okHttpClientService.asrComputeInternal(ArgumentMatchers.any(AsrComputeRequest.class))).thenReturn("test");
        when(benchmarkProcessDao.findByBenchmarkProcessId("1")).thenReturn(new BenchmarkProcess());

        assertEquals(true,  asrBenchmark.prepareAndPushToMetric(model,benchmark,fileMap,map));
    }
}