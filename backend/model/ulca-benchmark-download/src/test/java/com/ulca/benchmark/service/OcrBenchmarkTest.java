
package com.ulca.benchmark.service;

import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelInferenceResponseDao;
import io.swagger.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OcrBenchmarkTest {
    @InjectMocks
    OcrBenchmark ocrBenchmark;

    @Mock
    private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;
    
    @Mock
    ModelInferenceResponseDao modelInferenceResponseDao;

    @Mock
    OkHttpClientService okHttpClientService;

    @Value("${ulca.bm.ds.download.folder}")
    private String modelUploadFolder;
    @Mock
    BenchmarkProcessDao benchmarkProcessDao;

    @Test
    void prepareAndPushToMetric() throws IOException, URISyntaxException {

        String baseLocation = "src/test/resources/positive-testcase-01";
        OCRRequest ocrRequest = new OCRRequest();

        ModelExtended model = new ModelExtended();
        InferenceAPIEndPoint inferenceAPIEndPoint = new InferenceAPIEndPoint();
        inferenceAPIEndPoint.setCallbackUrl("https://test.com");
        OCRInference ocrInference = new OCRInference();
        ocrInference.setRequest(ocrRequest);
        inferenceAPIEndPoint.setSchema(ocrInference);
        model.setInferenceEndPoint(inferenceAPIEndPoint);
        ModelTask modelTask = new ModelTask();
        modelTask.setType(SupportedTasks.OCR);
        model.setTask(modelTask);

        Benchmark benchmark = new Benchmark();

        Map<String,String> fileMap = new HashMap<>();
        fileMap.put("baseLocation",baseLocation);

        String metric = "cer";

        String benchmarkingProcessId = "1";
        Map<String, String> map = new HashMap<String, String>();
        map.put(benchmarkingProcessId, metric);

        when(okHttpClientService.ocrCompute("https://test.com",ocrRequest)).thenReturn("test");
        when(benchmarkProcessDao.findByBenchmarkProcessId("1")).thenReturn(new BenchmarkProcess());


        assertEquals(true,  ocrBenchmark.prepareAndPushToMetric(model,benchmark,fileMap,map));
    }
}