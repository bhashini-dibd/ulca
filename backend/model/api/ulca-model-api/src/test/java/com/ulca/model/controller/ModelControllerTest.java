/*
 * package com.ulca.model.controller;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper; import
 * com.ulca.model.request.ModelComputeRequest; import
 * com.ulca.model.request.ModelFeedbackSubmitRequest; import
 * com.ulca.model.request.ModelSearchRequest; import
 * com.ulca.model.request.ModelStatusChangeRequest; import
 * com.ulca.model.service.ModelService; import org.junit.jupiter.api.BeforeEach;
 * import org.junit.jupiter.api.Test; import
 * org.junit.jupiter.api.extension.ExtendWith; import org.mockito.InjectMocks;
 * import org.mockito.Mock; import org.mockito.junit.jupiter.MockitoExtension;
 * import org.springframework.http.MediaType; import
 * org.springframework.mock.web.MockMultipartFile; import
 * org.springframework.test.web.servlet.MockMvc; import
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders; import
 * org.springframework.test.web.servlet.result.MockMvcResultMatchers; import
 * org.springframework.test.web.servlet.setup.MockMvcBuilders;
 * 
 * import java.nio.charset.StandardCharsets;
 * 
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.
 * multipart;
 * 
 * @ExtendWith(MockitoExtension.class) class ModelControllerTest {
 * 
 * @InjectMocks private ModelController modelController;
 * 
 * @Mock ModelService modelService;
 * 
 * private MockMvc mockMvc;
 * 
 * private ObjectMapper mapper;
 * 
 * private String BASE_URL = "/ulca/apis/v0/model";
 * 
 * @BeforeEach void setUp() { mockMvc =
 * MockMvcBuilders.standaloneSetup(modelController).build();
 * 
 * }
 * 
 * @Test void listByUserId() throws Exception {
 * 
 * mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL +
 * "/listByUserId").param("userId", "test"))
 * .andExpect(MockMvcResultMatchers.status().isOk()); }
 * 
 * @Test void getModel() throws Exception {
 * 
 * mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL +
 * "/getModel").param("modelId", "test"))
 * .andExpect(MockMvcResultMatchers.status().isOk()); }
 * 
 * @Test void uploadModel() throws Exception { byte[] fileContent =
 * "test".getBytes(StandardCharsets.UTF_8); MockMultipartFile filePart = new
 * MockMultipartFile("file", "test", null, fileContent);
 * mockMvc.perform(multipart(BASE_URL +
 * "/upload").file(filePart).param("userId", "test")
 * .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(MockMvcResultMatchers.
 * status().isOk()); }
 * 
 * @Test void searchModel() throws Exception {
 * 
 * ModelSearchRequest request = new ModelSearchRequest();
 * request.setTask("test");
 * 
 * mapper = new ObjectMapper(); String json =
 * mapper.writeValueAsString(request);
 * 
 * mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL +
 * "/search").contentType(MediaType.APPLICATION_JSON)
 * .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(
 * MockMvcResultMatchers.status().isOk()); }
 * 
 * @Test void changeStatus() throws Exception { ModelStatusChangeRequest request
 * = new ModelStatusChangeRequest();
 * 
 * mapper = new ObjectMapper(); String json =
 * mapper.writeValueAsString(request);
 * 
 * mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL +
 * "/status/change").contentType(MediaType.APPLICATION_JSON)
 * .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(
 * MockMvcResultMatchers.status().isOk()); }
 * 
 * @Test void computeModel() throws Exception { ModelComputeRequest request =
 * new ModelComputeRequest(); request.setModelId("test");
 * 
 * mapper = new ObjectMapper(); String json =
 * mapper.writeValueAsString(request);
 * 
 * mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL +
 * "/compute").contentType(MediaType.APPLICATION_JSON)
 * .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(
 * MockMvcResultMatchers.status().isOk()); }
 * 
 * @Test void tryMeOcrImageContent() throws Exception { byte[] fileContent =
 * "test".getBytes(StandardCharsets.UTF_8); MockMultipartFile filePart = new
 * MockMultipartFile("file", "test", null, fileContent);
 * mockMvc.perform(multipart(BASE_URL +
 * "/tryMe").file(filePart).param("modelId", "test").param("userId", "test")
 * .contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(MockMvcResultMatchers.
 * status().isOk()); }
 * 
 * @Test void modelFeedbackSubmit() throws Exception {
 * ModelFeedbackSubmitRequest request = new ModelFeedbackSubmitRequest();
 * request.setTaskType("test"); mapper = new ObjectMapper(); String json =
 * mapper.writeValueAsString(request);
 * 
 * mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/feedback/submit")
 * .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.
 * APPLICATION_JSON)) .andExpect(MockMvcResultMatchers.status().isOk()); }
 * 
 * @Test void getModelFeedbackByModelId() throws Exception {
 * mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL +
 * "/feedback/getByModelId").param("modelId", "test"))
 * .andExpect(MockMvcResultMatchers.status().isOk());
 * 
 * }
 * 
 * @Test void getModelFeedbackByTaskType() throws Exception {
 * mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL +
 * "/feedback/getByTaskType").param("taskType", "test"))
 * .andExpect(MockMvcResultMatchers.status().isOk());
 * 
 * } }
 */