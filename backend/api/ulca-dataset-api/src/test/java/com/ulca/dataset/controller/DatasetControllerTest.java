package com.ulca.dataset.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.dataset.request.DatasetCorpusSearchRequest;
import com.ulca.dataset.request.SearchCriteria;
import com.ulca.dataset.service.DatasetService;
import io.swagger.model.DatasetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DatasetControllerTest {
    @InjectMocks
    DatasetController datasetController;

    @Mock
    DatasetService benchmarkService;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private String BASE_URL = "/ulca/apis/v0/dataset";


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(datasetController).build();

    }


    @Test
    void listByUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/listByUserId").param("userId","test")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void datasetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/getByDatasetId").param("datasetId","test")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void datasetByServiceRequestNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/getByServiceRequestNumber").param("serviceRequestNumber","test")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void searchStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/corpus/search/status").param("serviceRequestNumber","test")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }


	/*
	 * @Test void searchListByUserId() throws Exception {
	 * mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+
	 * "/corpus/search/listByUserId").param("userId","test")).
	 * andExpect(MockMvcResultMatchers.status().isOk()); }
	 */}