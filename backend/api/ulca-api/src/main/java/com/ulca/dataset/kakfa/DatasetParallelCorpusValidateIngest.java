package com.ulca.dataset.kakfa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ulca.dataset.dao.TaskTrackerRedisDao;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.model.deserializer.ParallelDatasetParamsSchemaDeserializer;
import com.ulca.dataset.model.deserializer.ParallelDatasetRowSchemaDeserializer;
import com.ulca.dataset.service.DatasetService;
import com.ulca.dataset.service.ProcessTaskTrackerService;

import io.swagger.model.DatasetType;
import io.swagger.model.ParallelDatasetParamsSchema;
import io.swagger.model.ParallelDatasetRowSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasetParallelCorpusValidateIngest implements DatasetValidateIngest {

	@Autowired
	ProcessTaskTrackerService processTaskTrackerService;

	@Autowired
	DatasetErrorPublishService datasetErrorPublishService;

	@Autowired
	private KafkaTemplate<String, String> datasetValidateKafkaTemplate;

	@Value("${kafka.ulca.ds.validate.ip.topic}")
	private String validateTopic;
	
	@Autowired
	DatasetService datasetService;
	
	
	@Autowired
	TaskTrackerRedisDao taskTrackerRedisDao;

	public static final String SOURCE_TEXT = "sourceText";
	public static final String SOURCE_TEXT_HASH = "sourceTextHash";
	public static final String TARGET_TEXT = "targetText";
	public static final String TARGET_TEXT_HASH = "targetTextHash";

	public void validateIngest(Map<String, String> fileMap, FileDownload file) {

		log.info("************ Entry DatasetParallelCorpusValidateIngest :: validateIngest *********");

		String serviceRequestNumber = file.getServiceRequestNumber();
		String datasetName = file.getDatasetName();
		DatasetType datasetType = file.getDatasetType();
		String userId = file.getUserId();
		String datasetId = file.getDatasetId();
		
		ParallelDatasetParamsSchema paramsSchema = null;

		Error fileError = validateFileExistence(fileMap);
		
		if (fileError != null) {
			
			log.info("params.json or data.json file missing :: serviceRequestNumber : "+serviceRequestNumber ); 
			processTaskTrackerService.updateTaskTrackerWithError(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed, fileError);
			
			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);
			//send error event for download failure
			datasetErrorPublishService.publishDatasetError("dataset-training", fileError.getCode(), fileError.getMessage(), serviceRequestNumber, datasetName,"download" , datasetType.toString()) ;
			datasetErrorPublishService.publishEofStatus(serviceRequestNumber);
			return;
		}
		
		
		String paramsFilePath = fileMap.get("params.json");
		try {
			paramsSchema = validateParamsSchema(paramsFilePath,file);

		} catch (IOException | JSONException | NullPointerException e) {

			log.info("Exception while validating params :: "+serviceRequestNumber + " error : " + e.getMessage());
			Error error = new Error();
			error.setCause(e.getMessage());
			error.setMessage("params validation failed");
			error.setCode("1000_PARAMS_VALIDATION_FAILED");

			processTaskTrackerService.updateTaskTrackerWithError(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed, error);

			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);

			// send error event
			datasetErrorPublishService.publishDatasetError("dataset-training","1000_PARAMS_VALIDATION_FAILED", e.getMessage(), serviceRequestNumber, datasetName,"ingest" , datasetType.toString()) ;
			
			datasetErrorPublishService.publishEofStatus(serviceRequestNumber);
			e.printStackTrace();
			return;
		}
		try {
			ingest(paramsSchema, file, fileMap);
			
			
			
		} catch (IOException | JSONException | NoSuchAlgorithmException | NullPointerException   e) {
			
			log.info("Exception while ingesting the dataset :: serviceRequestNumber : "+serviceRequestNumber + " error : " + e.getMessage());
			Error error = new Error();
			error.setCause(e.getMessage());
			error.setMessage("INGEST FAILED");
			error.setCode("1000_INGEST_FAILED");

			processTaskTrackerService.updateTaskTrackerWithError(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed, error);
			
			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);
			
			// send error event
			datasetErrorPublishService.publishDatasetError("dataset-training","1000_INGEST_FAILED", e.getMessage(), serviceRequestNumber, datasetName,"ingest" , datasetType.toString()) ;
			datasetErrorPublishService.publishEofStatus(serviceRequestNumber);
			return;
			
		}
		//update the dataset
		
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			JSONObject record;
			record = new JSONObject(objectMapper.writeValueAsString(paramsSchema));
			
			datasetService.updateDataset(datasetId, userId, record);
			
		} catch (JsonProcessingException | JSONException e) {
			
			log.info("update Dataset failed , datasetId :: " + datasetId + " reason :: " + e.getMessage());
		}

		
		
		

	}

	public ParallelDatasetParamsSchema validateParamsSchema(String filePath, FileDownload file)
			throws JsonParseException, JsonMappingException, IOException {

		log.info("************ Entry DatasetParallelCorpusValidateIngest :: validateParamsSchema *********");
		log.info("validing file :: against params schema");
		log.info(filePath);
		
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(ParallelDatasetParamsSchema.class, new ParallelDatasetParamsSchemaDeserializer());
		mapper.registerModule(module);
		ParallelDatasetParamsSchema paramsSchema = mapper.readValue(new File(filePath),
				ParallelDatasetParamsSchema.class);
		if (paramsSchema == null) {
			log.info("params validation failed");
			throw new IOException("paramsValidation failed");

		}
		if (paramsSchema.getDatasetType() != file.getDatasetType()) {
			log.info("params validation failed");
			throw new IOException("params datasetType does not matches with submitted datasetType");
		}

		return paramsSchema;

	}
	public void ingest(ParallelDatasetParamsSchema paramsSchema, FileDownload file, Map<String, String> fileMap)
			throws JSONException, IOException, NoSuchAlgorithmException {

		log.info("************ Entry DatasetParallelCorpusValidateIngest :: ingest *********");

		String datasetId = file.getDatasetId();
		String serviceRequestNumber = file.getServiceRequestNumber();
		String userId = file.getUserId();
		String datasetName = file.getDatasetName();
		DatasetType datasetType = file.getDatasetType();

		ObjectMapper objectMapper = new ObjectMapper();

		JSONObject record;

		record = new JSONObject(objectMapper.writeValueAsString(paramsSchema));

		String dataFilePath = fileMap.get("data.json");

		log.info("data.json file path :: " + dataFilePath);
		
		InputStream inputStream = Files.newInputStream(Path.of(dataFilePath));
		JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

	
		int numberOfRecords = 0;
		int failedCount = 0;
		int successCount = 0;
		
		JSONObject vModel = new JSONObject();
		vModel.put("record", record);
		vModel.put("datasetId", datasetId);
		vModel.put("datasetName", file.getDatasetName());
		vModel.put("datasetType", paramsSchema.getDatasetType().toString());
		vModel.put("serviceRequestNumber", serviceRequestNumber);
		vModel.put("userId", userId);
		vModel.put("userMode", "real");
		
		 
		 
		taskTrackerRedisDao.intialize(serviceRequestNumber);
		log.info("starting to ingest serviceRequestNumber :: " + serviceRequestNumber);
		 
		reader.beginArray();
		while (reader.hasNext()) {

			numberOfRecords++;
			
			Object rowObj = new Gson().fromJson(reader, Object.class);
			ObjectMapper mapper = new ObjectMapper();
			String dataRow = mapper.writeValueAsString(rowObj);
			SimpleModule module = new SimpleModule();
			module.addDeserializer(ParallelDatasetRowSchema.class, new ParallelDatasetRowSchemaDeserializer());
			mapper.registerModule(module);
			
			ParallelDatasetRowSchema rowSchema  = null;
			
			try {
				
				rowSchema = mapper.readValue(dataRow, ParallelDatasetRowSchema.class);
							
				
			} catch(Exception e) {
				
				failedCount++;
				taskTrackerRedisDao.increment(serviceRequestNumber, "ingestError");
				datasetErrorPublishService.publishDatasetError("dataset-training","1000_ROW_DATA_VALIDATION_FAILED", e.getMessage(), serviceRequestNumber, datasetName,"ingest" , datasetType.toString()) ;
				
				log.info("record :: " +numberOfRecords + "failed " );
				log.info("tracing the error " );
				e.printStackTrace();
				
			}
			if(rowSchema != null) {
				
				successCount++;
				taskTrackerRedisDao.increment(serviceRequestNumber, "ingestSuccess");
				
				JSONObject target =  new JSONObject(dataRow);
				JSONObject finalRecord = deepMerge(record, target);
				
				if (finalRecord.has("languages")) {
					JSONObject language = finalRecord.getJSONObject("languages");
					String sourceLanguage = language.getString("sourceLanguage");
					String targetLanguage = language.getString("targetLanguage");
					finalRecord.put("sourceLanguage", sourceLanguage);
					finalRecord.put("targetLanguage", targetLanguage);
					finalRecord.remove("languages");
				}
				
				
				UUID uid = UUID.randomUUID();
				finalRecord.put("id", uid);

				vModel.put("record", finalRecord);
				vModel.put("currentRecordIndex", numberOfRecords);
				
				datasetValidateKafkaTemplate.send(validateTopic, vModel.toString());
				
				
			}
			
		}
		reader.endArray();
		reader.close();
		inputStream.close();

		
		taskTrackerRedisDao.setCountAndIngestComplete(serviceRequestNumber, numberOfRecords);
		
		log.info("data sending for validation serviceRequestNumber :: " + serviceRequestNumber + " total Record :: " + numberOfRecords + " success record :: " + successCount) ;
		
		

	}


	public String getSha256Hash(String input) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		byte[] output = md.digest(input.getBytes(StandardCharsets.UTF_8));
		BigInteger number = new BigInteger(1, output);

		StringBuilder hexString = new StringBuilder(number.toString(16));

		// Pad with leading zeros
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}

		String hashString = hexString.toString();
		return hashString;
	}
	

}
