package com.ulca.model.controller;

import com.ulca.model.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ModelControllerTest {
    @InjectMocks
    private ModelController modelController;

    @Mock
    ModelService modelService;

    private MockMvc mockMvc;

    private String BASE_URL = "/ulca/apis/v0/model";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(modelController).build();
    }

    @Test
    void listByUserId() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/listByUserId").param("userId","test")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getModel() {
    }

    @Test
    void uploadModel() {
    }

    @Test
    void searchModel() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void computeModel() {
    }

    @Test
    void tryMeOcrImageContent() {
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