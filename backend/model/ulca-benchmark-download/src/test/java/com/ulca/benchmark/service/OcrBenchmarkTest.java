package com.ulca.benchmark.service;

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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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

    @Test
    void prepareAndPushToMetric() throws IOException, URISyntaxException {

        String baseLocation = modelUploadFolder + "/ulca/ulca-test-datasets/ocr-dataset/positive-testcase-01";

        File file = new File(baseLocation + File.separator + "860190fb-3217-4c47-a350-2fd87c69a1d1.png");
        byte[] bytes = Files.readAllBytes(file.toPath());

        File file1 = new File(baseLocation + File.separator + "e2e22810-8aaa-430d-a30e-00eb6ac1e1ab.png");
        byte[] bytes1 = Files.readAllBytes(file1.toPath());

        ImageFile imageFile = new ImageFile();
        imageFile.setImageContent(bytes1);
        imageFile.setImageUri("test");

        ImageFile imageFile1 = new ImageFile();
        imageFile1.setImageContent(bytes);
        imageFile1.setImageUri("test1");

        ImageFiles imageFiles = new ImageFiles();
        imageFiles.add(imageFile);
        imageFiles.add(imageFile1);

        OCRRequest ocrRequest = new OCRRequest();
        ocrRequest.setImage(imageFiles);

        ModelExtended model = new ModelExtended();
        InferenceAPIEndPoint inferenceAPIEndPoint = new InferenceAPIEndPoint();
        inferenceAPIEndPoint.setCallbackUrl("https://test.com");
        OCRInference ocrInference = new OCRInference();
        ocrInference.setRequest(ocrRequest);
        inferenceAPIEndPoint.setSchema(ocrInference);
        model.setInferenceEndPoint(inferenceAPIEndPoint);
        ModelTask modelTask = new ModelTask();
        modelTask.setType(ModelTask.TypeEnum.OCR);
        model.setTask(modelTask);

        Benchmark benchmark = new Benchmark();

        Map<String,String> fileMap = new HashMap<>();
        fileMap.put("baseLocation",baseLocation);

        String metric = "cer";

        String benchmarkingProcessId = "1";

        when(okHttpClientService.ocrCompute("https://test.com",ocrRequest)).thenReturn("test");

        //assertEquals(2,  ocrBenchmark.prepareAndPushToMetric(model,benchmark,fileMap,metric,benchmarkingProcessId));
    }
}