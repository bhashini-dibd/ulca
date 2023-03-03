package com.ulca.benchmark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.benchmark.request.*;
import com.ulca.benchmark.service.BenchmarkService;
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

@ExtendWith(MockitoExtension.class)
class BenchmarkControllerTest {

    @InjectMocks
    private BenchmarkController benchmarkController;

    @Mock
    BenchmarkService benchmarkService;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private String BASE_URL = "/ulca/apis/v0/benchmark";


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(benchmarkController).build();

    }

    @Test
    void listByUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/listByUserId").param("userId","test")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void executeBenchmark() throws Exception {
        ExecuteBenchmarkRequest request = new ExecuteBenchmarkRequest();


        mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/execute").contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void executeBenchmarkAllMetric() throws Exception {
        ExecuteBenchmarkAllMetricRequest request = new ExecuteBenchmarkAllMetricRequest();

        mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/execute/allMetric").contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getBenchmarkById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/getBenchmark").param("benchmarkId","test")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

	
	  @Test void listBytask() throws Exception { BenchmarkListByModelRequest
	  request = new BenchmarkListByModelRequest(); request.setModelId("test");
	  mapper = new ObjectMapper(); String json =
	  mapper.writeValueAsString(request);
	  
	  mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/getByTask").
	  contentType(MediaType.APPLICATION_JSON) .content(json)
	  .accept(MediaType.APPLICATION_JSON)).
	  andExpect(MockMvcResultMatchers.status().isOk()); }
	 
    @Test
    void searchBenchmark() throws Exception {
        BenchmarkSearchRequest request = new BenchmarkSearchRequest();
        request.setTask("test");

        mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL+"/search").contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void processStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/process/status").param("benchmarkProcessId","test")).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}