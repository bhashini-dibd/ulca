package com.ulca.model.service;

import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.util.ModelConstants;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelFeedback;
import com.ulca.model.dao.ModelFeedbackDao;
import com.ulca.model.request.ModelComputeRequest;
import com.ulca.model.request.ModelFeedbackSubmitRequest;
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.request.ModelStatusChangeRequest;
import com.ulca.model.response.*;
import io.swagger.model.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest


class ModelServiceTest {
    @InjectMocks
    private ModelService modelService;

    @Mock
    ModelDao modelDao;


    @Mock
    BenchmarkProcessDao benchmarkProcessDao;

    @Mock
    ModelFeedbackDao modelFeedbackDao;

    @Mock
    ModelInferenceEndPointService modelInferenceEndPointService;

    @Value("${ulca.model.upload.folder}")
    private String modelUploadFolder;


    @Test
    void modelSubmit() {

        ModelExtended modelExtended = new ModelExtended();
        when(modelDao.save(modelExtended)).thenReturn(modelExtended);
        assertEquals(modelExtended,modelService.modelSubmit(modelExtended));
    }

    private static Stream<Arguments> modelListByUserIdParam(){
        return Stream.of(Arguments.of("test",1,1,null,null),
                         Arguments.of("test",null,null,null,null));
    }



    @ParameterizedTest
    @MethodSource("modelListByUserIdParam")
    void modelListByUserId(String userId, Integer startPage, Integer endPage,Integer pgSize,String name) {
        if (startPage != null) {
            int startPg = startPage - 1;
            for (int i = startPg; i < endPage; i++) {
                Pageable paging = PageRequest.of(i, 10);
                Page<ModelExtended> modelList = new PageImpl<>(Collections.singletonList(new ModelExtended()));
                when(modelDao.findByUserId(userId,paging)).thenReturn(modelList);
            }
        } else {
            when(modelDao.findByUserId(userId)).thenReturn(Collections.singletonList(new ModelExtended()));

        }
        for (ModelExtended model : Collections.singletonList(new ModelExtended())) {
            when(benchmarkProcessDao.findByModelId(model.getModelId())).thenReturn(Collections.singletonList(new BenchmarkProcess()));

        }
        ModelListResponseDto modelDto = new ModelListResponseDto();
        modelDto.setBenchmarkPerformance(Collections.singletonList(new BenchmarkProcess()));

        List<ModelListResponseDto>  modelDtoList= new ArrayList<>();
        modelDtoList.add(modelDto);

        assertInstanceOf(ModelListByUserIdResponse.class,
                modelService.modelListByUserId(userId,startPage,endPage,pgSize,name));

    }
    private static Stream<Arguments> getModelByModelIdParam(){
        ModelConstants modelConstants = new ModelConstants();
        ModelExtended modelExtended = new ModelExtended();
        ModelTask modelTask = new ModelTask();
        modelTask.setType(ModelTask.TypeEnum.TRANSLATION);
        modelExtended.setTask(modelTask);
        modelExtended.setModelId("test");
        ModelListResponseDto modelDto = new ModelListResponseDto();
        BeanUtils.copyProperties(modelExtended, modelDto);
        modelDto.setMetric(modelConstants.getMetricListByModelTask(ModelTask.TypeEnum.TRANSLATION.toString()));
        modelDto.setBenchmarkPerformance(Collections.singletonList(new BenchmarkProcess()));
        ModelExtended modelExtended1 = new ModelExtended();
        modelExtended1.setModelId("test1");




        return Stream.of(Arguments.of(modelExtended,modelDto),
                         Arguments.of(modelExtended1,null));
    }

    @ParameterizedTest
    @MethodSource("getModelByModelIdParam")
    void getModelByModelId(ModelExtended modelExtended, ModelListResponseDto modelListResponseDto) {

        ReflectionTestUtils.setField(modelService,"modelConstants",new ModelConstants());

           if(modelExtended.getModelId().equalsIgnoreCase("test")) {
               when(modelDao.findById(modelExtended.getModelId())).thenReturn((Optional.of(modelExtended)));
               when(benchmarkProcessDao.findByModelIdAndStatus(modelExtended.getModelId(), "Completed"))
                       .thenReturn(Collections.singletonList(new BenchmarkProcess()));
           }
        assertEquals(modelListResponseDto,modelService.getModelByModelId(modelExtended.getModelId()));
    }

    private static Stream<Arguments> uploadModelParam(){
        byte[] fileContent = ("{\n" +
                "   \"name\": \"test\",\n" +
                "   \"version\": \"v1.0\",\n" +
                "   \"description\": \"test\",\n" +
                "   \"refUrl\": \"test\",\n" +
                "   \"task\": {\n" +
                "      \"type\" : \"translation\"\n" +
                "   },\n" +
                "   \"languages\": [\n" +
                "      {\n" +
                "         \"sourceLanguage\": \"en\",\n" +
                "          \"targetLanguage\": \"hi\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"license\": \"mit\",\n" +
                "   \"domain\": [\n" +
                "      \"general\"\n" +
                "   ],\n" +
                "   \"submitter\": {\n" +
                "       \"name\": \"test\"\n" +
                "       },\n" +
                "   \"inferenceEndPoint\": {\n" +
                "      \"callbackUrl\": \"test+\",\n" +
                "      \"schema\": {\n" +
                "         \"taskType\": \"translation\",\n" +
                "         \"request\": {\n" +
                "            \"config\": {\n" +
                "               \"modelId\": \"103\",\n" +
                "               \"language\": {\n" +
                "                  \"sourceLanguage\": \"en\",\n" +
                "                  \"targetLanguage\": \"hi\"\n" +
                "               }\n" +
                "            }\n" +
                "         }\n" +
                "      }\n" +
                "   },\n" +
                "   \"trainingDataset\": {\n" +
                "      \"datasetId\": \"2398749282\",\n" +
                "      \"description\": \"test\"\n" +
                "   }\n" +
                "}\n").getBytes(StandardCharsets.UTF_8);

        MockMultipartFile filePart = new MockMultipartFile("file", "test", "JSON", fileContent);
        ModelExtended modelExtended = new ModelExtended();
        modelExtended.setModelId("test");
        modelExtended.setName("test");
        modelExtended.setDescription("test");
        modelExtended.setRefUrl("test");
        modelExtended.setLicense(License.fromValue("mit"));
        Domain domain = new Domain();
        domain.add("general");
        modelExtended.setDomain(domain);

        LanguagePairs languagePairs = new LanguagePairs();
        LanguagePair languagePair = new LanguagePair();
        languagePair.setSourceLanguage(LanguagePair.SourceLanguageEnum.EN);
        languagePair.setTargetLanguage(LanguagePair.TargetLanguageEnum.HI);

        languagePairs.add(languagePair);
        modelExtended.setLanguages(languagePairs);

        Submitter submitter = new Submitter();
        submitter.setName("test");
        modelExtended.setSubmitter(submitter);

        TrainingDataset trainingDataset = new TrainingDataset();
        trainingDataset.setDatasetId("2398749282");
        trainingDataset.setDescription("test");
        modelExtended.setTrainingDataset(trainingDataset);

        ModelTask modelTask = new ModelTask();
        modelTask.setType(ModelTask.TypeEnum.TRANSLATION);
        modelExtended.setTask(modelTask);
        UploadModelResponse response = new UploadModelResponse("Model Saved Successfully",modelExtended);


        return Stream.of(Arguments.of(filePart,"test",response));
    }

    @ParameterizedTest
    @MethodSource("uploadModelParam")
    void uploadModel(MultipartFile multipartFile,String userId,UploadModelResponse response) throws Exception {
        assertEquals(modelService.uploadModel(multipartFile,userId),response);
    }

    private static Stream<Arguments> searchModelParam(){
        ModelSearchRequest request = new ModelSearchRequest();
        request.setTask("translation");
        request.setSourceLanguage("en");
        request.setTargetLanguage("hi");

        ModelExtended modelExtended = new ModelExtended();

        ModelTask modelTask = new ModelTask();
        modelTask.setType(ModelTask.TypeEnum.TRANSLATION);
        modelExtended.setTask(modelTask);

        LanguagePairs languagePairs = new LanguagePairs();
        LanguagePair languagePair = new LanguagePair();
        languagePair.setSourceLanguage(LanguagePair.SourceLanguageEnum.EN);
        languagePair.setTargetLanguage(LanguagePair.TargetLanguageEnum.HI);

        languagePairs.add(languagePair);
        modelExtended.setLanguages(languagePairs);
        List<ModelExtended> list = Collections.singletonList(modelExtended);

        ModelSearchResponse response = new ModelSearchResponse("Model Search Result",list,1);

        ModelExtended modelExtended1 = modelExtended;
        Example<ModelExtended> example = Example.of(modelExtended1);

        modelExtended1.setStatus("published");

        return Stream.of(Arguments.of(request,response,list,example));
    }
    @ParameterizedTest
    @MethodSource("searchModelParam")
    void searchModel(ModelSearchRequest request,ModelSearchResponse response,List<ModelExtended> list,Example<ModelExtended> example) {
        when(modelDao.findAll(example)).thenReturn(list);
        assertEquals(modelService.searchModel(request),response);
    }

    @Test
    void computeModel() throws URISyntaxException, IOException, NoSuchAlgorithmException, InterruptedException, KeyManagementException {
        ModelComputeRequest request = new ModelComputeRequest();
        request.setModelId("test");

        ModelComputeResponse response = new ModelComputeResponse();
        ModelExtended modelExtended = new ModelExtended();
        InferenceAPIEndPoint inferenceAPIEndPoint = new InferenceAPIEndPoint();
        modelExtended.setInferenceEndPoint(inferenceAPIEndPoint);

        when(modelDao.findById("test")).thenReturn(Optional.of(modelExtended));
        when(modelInferenceEndPointService.compute(inferenceAPIEndPoint,request)).thenReturn(response);

        assertEquals(response,modelService.computeModel(request));

    }

    @Test
    void tryMeOcrImageContent() throws Exception {

        File file = new File("src/test/resources/860190fb-3217-4c47-a350-2fd87c69a1d1.png");

        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                file.getName(), "image/png", IOUtils.toByteArray(input));

        ModelService modelService1 = new ModelService();
        String filePath = modelService1.storeModelTryMeFile(multipartFile);

        ModelComputeResponse response = new ModelComputeResponse();
        ModelExtended modelExtended = new ModelExtended();
        InferenceAPIEndPoint inferenceAPIEndPoint = new InferenceAPIEndPoint();
        inferenceAPIEndPoint.setCallbackUrl("test");
        inferenceAPIEndPoint.setSchema(new OCRInference());
        modelExtended.setInferenceEndPoint(inferenceAPIEndPoint);
        when(modelDao.findById("test")).thenReturn(Optional.of(modelExtended));
        when(modelInferenceEndPointService.compute("test",new OCRInference(),filePath)).thenReturn(response);

        assertEquals(response,modelService.tryMeOcrImageContent(multipartFile,"test"));


    }

    @Test
    void changeStatus() {
        ModelStatusChangeRequest request = new ModelStatusChangeRequest();
        request.setStatus(ModelStatusChangeRequest.StatusEnum.unpublished);
        request.setModelId("test");
        request.setUserId("test");
        request.setUnpublishReason("test");
        ModelExtended modelExtended = new ModelExtended();
        modelExtended.setModelId("test");
        modelExtended.setUserId("test");
        when(modelDao.findByModelId("test")).thenReturn(modelExtended);
        assertEquals(new ModelStatusChangeResponse("Model " + ModelStatusChangeRequest.StatusEnum.unpublished.toString() +  " successfull."),modelService.changeStatus(request));
    }
    private  static Stream<Arguments> modelFeedbackSubmitParam(){
        ModelFeedbackSubmitRequest request = new ModelFeedbackSubmitRequest();
        request.setTaskType("sts");
        request.setDetailedFeedback(Collections.singletonList(new ModelFeedbackSubmitRequest()));

        return Stream.of(Arguments.of(request));
    }
    @ParameterizedTest
    @MethodSource("modelFeedbackSubmitParam")
    void modelFeedbackSubmit(ModelFeedbackSubmitRequest request) {
        assertInstanceOf(ModelFeedbackSubmitResponse.class,modelService.modelFeedbackSubmit(request));
    }

    @Test
    void getModelFeedbackByModelId() {
        when(modelFeedbackDao.findByModelId("test")).thenReturn(Collections.singletonList(new ModelFeedback()));
        assertEquals(Collections.singletonList(new ModelFeedback()),modelService.getModelFeedbackByModelId("test"));
    }

    private static Stream<Arguments> getModelFeedbackByTaskTypeParam(){
        ModelFeedback modelFeedback = new ModelFeedback();
        modelFeedback.setFeedbackId("test");

        GetModelFeedbackListResponse response = new GetModelFeedbackListResponse();
        response.setDetailedFeedback(Collections.EMPTY_LIST);

        return Stream.of(Arguments.of("translation",Collections.singletonList(new GetModelFeedbackListResponse()),
                Collections.singletonList(modelFeedback)),
                Arguments.of("sts",Collections.singletonList(response),
                        Collections.singletonList(modelFeedback)));
    }
    @ParameterizedTest
    @MethodSource("getModelFeedbackByTaskTypeParam")
    void getModelFeedbackByTaskType(String type,List<GetModelFeedbackListResponse> responses,List<ModelFeedback> list) {

        when(modelFeedbackDao.findByTaskType(type)).thenReturn(list);
        when(modelFeedbackDao.findByStsFeedbackId(type)).thenReturn(list);
        assertEquals(responses,modelService.getModelFeedbackByTaskType(type));
    }
}