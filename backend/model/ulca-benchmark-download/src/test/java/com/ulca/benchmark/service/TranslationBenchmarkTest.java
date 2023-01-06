//package com.ulca.benchmark.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ulca.benchmark.dao.BenchmarkProcessDao;
//import com.ulca.benchmark.model.BenchmarkProcess;
//import com.ulca.model.dao.ModelExtended;
//import com.ulca.model.dao.ModelInferenceResponseDao;
//import io.swagger.model.*;
//import okhttp3.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//class TranslationBenchmarkTest {
//    @InjectMocks
//    TranslationBenchmark translationBenchmark;
//    
//    @Mock
//    private KafkaTemplate<String, String> benchmarkMetricKafkaTemplate;
//
//    @Mock
//    ModelInferenceResponseDao modelInferenceResponseDao;
//
//    @Value("${ulca.bm.ds.download.folder}")
//    private String modelUploadFolder;
//
//    @Mock
//    OkHttpClientService okHttpClientService;
//
//    @Mock
//    BenchmarkProcessDao benchmarkProcessDao;
//
//    private static Stream<Arguments>  prepareAndPushToMetricParam(){
//        TranslationRequest request = new TranslationRequest();
//        InferenceAPIEndPoint inferenceAPIEndPoint = new InferenceAPIEndPoint();
//        inferenceAPIEndPoint.setCallbackUrl("https://test.com");
//        TranslationInference translationInference = new TranslationInference();
//        translationInference.setRequest(request);
//        inferenceAPIEndPoint.setSchema(translationInference);
//
//        InferenceAPIEndPoint inferenceAPIEndPoint1 = new InferenceAPIEndPoint();
//        inferenceAPIEndPoint1.setIsSyncApi(false);
//        AsyncApiDetails asyncApiDetails = new AsyncApiDetails();
//        TranslationAsyncInference translationAsyncInference = new TranslationAsyncInference();
//        translationAsyncInference.setRequest(request);
//        asyncApiDetails.setAsyncApiSchema(translationAsyncInference);
//        asyncApiDetails.setPollingUrl("https://test.com");
//        asyncApiDetails.setPollInterval(200);
//        inferenceAPIEndPoint1.setCallbackUrl("https://test.com");
//        inferenceAPIEndPoint1.setAsyncApiDetails(asyncApiDetails);
//
//        return Stream.of(Arguments.of(inferenceAPIEndPoint,false),
//                Arguments.of(inferenceAPIEndPoint1,true));
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("prepareAndPushToMetricParam")
//    void prepareAndPushToMetric(InferenceAPIEndPoint inferenceAPIEndPoint ,boolean isAsync) throws Exception {
//        String baseLocation = "src/test/resources/basic";
//        ModelExtended model = new ModelExtended();
//        model.setInferenceEndPoint(inferenceAPIEndPoint);
//
//        ModelTask modelTask = new ModelTask();
//        modelTask.setType(SupportedTasks.TRANSLATION);
//
//        model.setTask(modelTask);
//
//        TranslationResponse  translationResponse = new TranslationResponse();
//        Sentences sentences1 = new Sentences();
//        Sentence sentence1 = new Sentence();
//        sentence1.setTarget("भारत-नेपाल सीमा के करीब होने के चलते इस पुल का रणनीतिक महत्व भी है");
//        sentences1.add(sentence1);
//        Sentence sentence2 = new Sentence();
//        sentence2.setTarget("भारत-नेपाल सीमा के करीब होने के चलते इस पुल का रणनीतिक महत्व भी है");
//        sentences1.add(sentence2);
//        Sentence sentence3 = new Sentence();
//        sentence3.setTarget("भारत-नेपाल सीमा के करीब होने के चलते इस पुल का रणनीतिक महत्व भी है");
//        sentences1.add(sentence3);
//
//        translationResponse.setOutput(sentences1);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String response = objectMapper.writeValueAsString(translationResponse);
//
//        PollingRequest pollingRequest = new PollingRequest();
//        pollingRequest.setRequestId("test");
//        String response1 = objectMapper.writeValueAsString(pollingRequest);
//
//        Benchmark benchmark = new Benchmark();
//
//        Map<String,String> fileMap = new HashMap<>();
//        fileMap.put("baseLocation",baseLocation);
//
//        String metric = "blew";
//
//        String benchmarkingProcessId = "1";
//        String json = "{\"input\": \"test\"}";
//        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
//        Request httpRequest = new Request.Builder().url("https://test.com").post(body).build();
//
//        Response response2 = new Response.Builder()
//                .request(httpRequest)
//                .protocol(Protocol.HTTP_2)
//                .message("")
//                .code(200) // status code
//                .body(ResponseBody.create(
//                        MediaType.get("application/json; charset=utf-8"),
//                        objectMapper.writeValueAsString(translationResponse)
//                ))
//                .build();
//        when(benchmarkProcessDao.findByBenchmarkProcessId("1")).thenReturn(new BenchmarkProcess());
//
//        if (!isAsync) {
//            when(okHttpClientService.okHttpClientPostCall(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(response);
//        }
//        if (isAsync){
//            when(okHttpClientService.okHttpClientPostCall(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(response1);
//
//            when(okHttpClientService.okHttpClientAsyncPostCall(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(response2);
//        }
//        
//        Map<String, String> map = new HashMap<String, String>();
//        map.put(benchmarkingProcessId, metric);
//
//       assertEquals(true,  translationBenchmark.prepareAndPushToMetric(model,benchmark,fileMap, map));
//    }
//}