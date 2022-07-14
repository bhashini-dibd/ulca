package com.ulca.model.service;

import com.ulca.benchmark.dao.BenchmarkProcessDao;
import com.ulca.benchmark.model.BenchmarkProcess;
import com.ulca.benchmark.util.ModelConstants;
import com.ulca.model.dao.ModelDao;
import com.ulca.model.dao.ModelExtended;
import com.ulca.model.dao.ModelFeedbackDao;
import com.ulca.model.response.ModelListByUserIdResponse;
import com.ulca.model.response.ModelListResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
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






    @Test
    void modelSubmit() {

        ModelExtended modelExtended = new ModelExtended();
        when(modelDao.save(modelExtended)).thenReturn(modelExtended);
        assertEquals(modelExtended,modelService.modelSubmit(modelExtended));
    }

    private static Stream<Arguments> modelListByUserIdParam(){
        return Stream.of(Arguments.of("test",1,1),
                         Arguments.of("test",null,null));
    }



    @ParameterizedTest
    @MethodSource("modelListByUserIdParam")
    void modelListByUserId(String userId, Integer startPage, Integer endPage) {
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

        assertEquals(new ModelListByUserIdResponse("Model list by UserId", modelDtoList, modelDtoList.size()),
                modelService.modelListByUserId(userId,startPage,endPage));

    }

    @Test
    void getModelByModelId() {
    }

    @Test
    void storeModelFile() {
    }

    @Test
    void storeModelTryMeFile() {
    }

    @Test
    void uploadModel() {
    }

    @Test
    void getUploadedModel() {
    }

    @Test
    void searchModel() {
    }

    @Test
    void computeModel() {
    }

    @Test
    void tryMeOcrImageContent() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void modelFeedbackSubmit() {
    }

    @Test
    void getModelFeedbackByModelId() {
    }

    @Test
    void getModelFeedbackByTaskType() {
    }
}