package com.ulca.benchmark.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransliterationBenchmarkTest {
    @InjectMocks
    TransliterationBenchmark transliterationBenchmark;

    @Mock
    private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;

    @Mock
    ModelInferenceResponseDao modelInferenceResponseDao;

    @Value("${ulca.bm.ds.download.folder}")
    private String modelUploadFolder;

    @Mock
    OkHttpClientService okHttpClientService;

    @Test
    void prepareAndPushToMetric() throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        String baseLocation = modelUploadFolder + "ulca/specs/examples/benchmark-dataset/transliteration-benchmark-dataset";
        ModelExtended model = new ModelExtended();
        TransliterationRequest request = new TransliterationRequest();
        TransliterationConfig transliterationConfig = new TransliterationConfig();
        request.setConfig(transliterationConfig);
        InferenceAPIEndPoint inferenceAPIEndPoint = new InferenceAPIEndPoint();
        inferenceAPIEndPoint.setCallbackUrl("https://test.com");
        TransliterationInference transliterationInference= new TransliterationInference();
        transliterationInference.setRequest(request);
        inferenceAPIEndPoint.setSchema(transliterationInference);

        model.setInferenceEndPoint(inferenceAPIEndPoint);

        ModelTask modelTask = new ModelTask();
        modelTask.setType(ModelTask.TypeEnum.TRANSLITERATION);

        model.setTask(modelTask);

        TransliterationResponse transliterationResponse = new TransliterationResponse();
        SentencesList sentencesList = new SentencesList();
        SentenceList sentenceList = new SentenceList();
        sentenceList.addTargetItem("भारत");
        SentenceList sentenceList1 = new SentenceList();
        sentenceList1.addTargetItem("पिछले");
        SentenceList sentenceList2 = new SentenceList();
        sentenceList2.addTargetItem("प्रधानमंत्री");
        sentencesList.add(sentenceList);
        sentencesList.add(sentenceList1);
        sentencesList.add(sentenceList2);
        transliterationResponse.setOutput(sentencesList);

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(transliterationResponse);

        Benchmark benchmark = new Benchmark();

        Map<String,String> fileMap = new HashMap<>();
        fileMap.put("baseLocation",baseLocation);

        String metric = "cer";

        String benchmarkingProcessId = "1";

        when(okHttpClientService.okHttpClientPostCall(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(response);

        //assertEquals(3, transliterationBenchmark.prepareAndPushToMetric(model,benchmark,fileMap,metric,benchmarkingProcessId));
    }
}