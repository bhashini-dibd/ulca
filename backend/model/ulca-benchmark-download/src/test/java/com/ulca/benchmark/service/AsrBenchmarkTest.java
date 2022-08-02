package com.ulca.benchmark.service;

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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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



    @Test
    void prepareAndPushToMetric() throws IOException, URISyntaxException {
        String baseLocation = modelUploadFolder + "/ulca/specs/examples/benchmark-dataset/asr-benchmark-dataset";
        File file = new File(baseLocation + File.separator + "ahd_00300_long_00454_eng_0008.wav");
        byte[] bytes = Files.readAllBytes(file.toPath());

        File file1 = new File(baseLocation + File.separator + "ahd_00300_long_00698_eng_0003.wav");
        byte[] bytes1 = Files.readAllBytes(file1.toPath());

        File file2 = new File(baseLocation + File.separator + "ahd_00300_long_00698_eng_0009.wav");
        byte[] bytes2 = Files.readAllBytes(file1.toPath());



        AudioFile audioFile = new AudioFile();
        audioFile.audioContent(bytes);
        audioFile.audioUri("test");

        AudioFile audioFile1 = new AudioFile();
        audioFile1.audioContent(bytes1);
        audioFile1.audioUri("test1");

        AudioFile audioFile2 = new AudioFile();
        audioFile2.audioContent(bytes2);
        audioFile2.audioUri("test2");



        AudioFiles audioFiles = new AudioFiles();
        audioFiles.add(audioFile);
        audioFiles.add(audioFile1);
        audioFiles.add(audioFile2);

        ASRRequest asrRequest = new ASRRequest();
        asrRequest.setAudio(audioFiles);
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

        when(okHttpClientService.asrComputeInternal(ArgumentMatchers.any(AsrComputeRequest.class))).thenReturn("test");

        //assertEquals(3,  asrBenchmark.prepareAndPushToMetric(model,benchmark,fileMap,metric,benchmarkingProcessId));
    }
}