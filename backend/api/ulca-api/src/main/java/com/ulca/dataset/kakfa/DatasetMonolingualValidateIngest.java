package com.ulca.dataset.kakfa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.dao.TaskTrackerRedisDao;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.model.deserializer.ASRDatasetRowDataSchemaDeserializer;
import com.ulca.dataset.model.deserializer.ASRParamsSchemaDeserializer;
import com.ulca.dataset.model.deserializer.MonolingualDatasetParamsSchemaDeserializer;
import com.ulca.dataset.model.deserializer.OcrDatasetParamsSchemaDeserializer;
import com.ulca.dataset.model.deserializer.OcrDatasetRowDataSchemaDeserializer;
import com.ulca.dataset.model.deserializer.ParallelDatasetRowSchemaDeserializer;
import com.ulca.dataset.service.ProcessTaskTrackerService;

import io.swagger.model.ASRParamsSchema;
import io.swagger.model.ASRRowSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.MonolingualParamsSchema;
import io.swagger.model.OcrDatasetParamsSchema;
import io.swagger.model.OcrDatasetRowSchema;
import io.swagger.model.ParallelDatasetRowSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasetMonolingualValidateIngest {

	@Autowired
	ProcessTaskTrackerService processTaskTrackerService;

	@Autowired
	ProcessTrackerDao processTrackerDao;

	@Autowired
	TaskTrackerDao taskTrackerDao;

	@Autowired
	DatasetErrorPublishService datasetErrorPublishService;

	@Autowired
	private KafkaTemplate<String, String> datasetValidateKafkaTemplate;

	@Value("${kafka.ulca.ds.validate.ip.topic}")
	private String validateTopic;
	
	@Autowired
	TaskTrackerRedisDao taskTrackerRedisDao;

	public void validateIngest(Map<String, String> fileMap, FileDownload file) {

		log.info("************ Entry DatasetMonolingualValidateIngest :: validateIngest *********");
		
		String serviceRequestNumber = file.getServiceRequestNumber();
		String datasetName = file.getDatasetName();
		DatasetType datasetType = file.getDatasetType();
		
		MonolingualParamsSchema paramsSchema = null;

		Set<String> keys = fileMap.keySet();
		log.info("logging the fileMap keys");
		for (String key : keys) {
			log.info("key :: " + key);
			log.info("value :: " + fileMap.get(key));
		}

		String paramsFilePath = fileMap.get("params.json");
		if (paramsFilePath == null) {
			log.info("params.json file not available");
			Error error = new Error();
			error.setCause("params.json file not available");
			error.setMessage("params validation failed");
			error.setCode("1000_PARAMS_JSON_FILE_NOT_AVAILABLE");

			processTaskTrackerService.updateTaskTrackerWithError(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed, error);
			
			

			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);
			return;
		}
		try {
			paramsSchema = validateParamsSchema(paramsFilePath, file);

		} catch (IOException | JSONException | NullPointerException e) {
			log.info("Exception while validating params :: " + e.getMessage());
			Error error = new Error();
			error.setCause(e.getMessage());
			error.setMessage("params validation failed");
			error.setCode("1000_PARAMS_VALIDATION_FAILED");

			processTaskTrackerService.updateTaskTrackerWithError(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed, error);

			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);
			
			// send error event
			datasetErrorPublishService.publishDatasetError("dataset-training","1000_PARAMS_VALIDATION_FAILED", e.getMessage(), serviceRequestNumber, datasetName,"ingest" , datasetType.toString()) ;

			e.printStackTrace();
			return;
		}
		try {
			ingest(paramsSchema, file, fileMap);

		} catch (IOException e) {

			log.info("Exception while ingesting :: " + e.getMessage());
			
			Error error = new Error();
			error.setCause(e.getMessage());
			error.setMessage("INGEST FAILED");
			error.setCode("1000_INGEST_FAILED");

			processTaskTrackerService.updateTaskTrackerWithError(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed, error);

			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);

			// send error event
			datasetErrorPublishService.publishDatasetError("dataset-training","1000_INGEST_FAILED", e.getMessage(), serviceRequestNumber, datasetName,"ingest" , datasetType.toString()) ;
						

			return;
		}

	}

	public MonolingualParamsSchema validateParamsSchema(String filePath, FileDownload file)
			throws JsonParseException, JsonMappingException, IOException {

		log.info("************ Entry DatasetMonolingualValidateIngest :: validateParamsSchema *********");
		log.info("validing file :: against params schema");
		log.info(filePath);
		String serviceRequestNumber = file.getServiceRequestNumber();
		log.info(serviceRequestNumber);
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(MonolingualParamsSchema.class, new MonolingualDatasetParamsSchemaDeserializer());
		mapper.registerModule(module);

		MonolingualParamsSchema paramsSchema = mapper.readValue(new File(filePath), MonolingualParamsSchema.class);
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

	public void ingest(MonolingualParamsSchema paramsSchema, FileDownload file, Map<String, String> fileMap)
			throws IOException {

		log.info("************ Entry DatasetMonolingualValidateIngest :: ingest *********");

		String datasetId = file.getDatasetId();
		String serviceRequestNumber = file.getServiceRequestNumber();
		String userId = file.getUserId();
		String datasetName = file.getDatasetName();
		DatasetType datasetType = file.getDatasetType();
		

		ObjectMapper objectMapper = new ObjectMapper();

		JSONObject source;

		String path = fileMap.get("data.json");
		log.info("json.data file path :: " + path);

		source = new JSONObject(objectMapper.writeValueAsString(paramsSchema));

		InputStream inputStream = Files.newInputStream(Path.of(path));
		JsonReader reader = new JsonReader(new InputStreamReader(inputStream));


		int numberOfRecords = 0;
		int failedCount = 0;
		int successCount = 0;
		
		JSONObject vModel = new JSONObject();
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
			
			JSONObject target =  new JSONObject(dataRow);
			if(!target.has("text")) {
				
				failedCount++;
				taskTrackerRedisDao.increment(serviceRequestNumber, "ingestError");
				// send error event
				datasetErrorPublishService.publishDatasetError("dataset-training","1000_ROW_DATA_VALIDATION_FAILED","data row does not contains text field", serviceRequestNumber, datasetName,"ingest" , datasetType.toString()) ;
				
				log.info("record :: " +numberOfRecords + "failed " );
				log.info("data row does not contains text field");
				
				
			}else {
				Set<String> rowKeys = target.keySet();
				if(rowKeys.size() > 1) {
					log.info("record :: " +numberOfRecords + "failed " );
					log.info("data row does contains unkown fields" );
					
					failedCount++;
					taskTrackerRedisDao.increment(serviceRequestNumber, "ingestError");
					// send error event
					datasetErrorPublishService.publishDatasetError("dataset-training","1000_ROW_DATA_VALIDATION_FAILED", "data row does contains unkown fields", serviceRequestNumber, datasetName,"ingest" , datasetType.toString()) ;
					
					
				}else {
					
					successCount++;
					taskTrackerRedisDao.increment(serviceRequestNumber, "ingestSuccess");
					
					JSONObject finalRecord = deepMerge(source, target);
					String sourceLanguage = finalRecord.getJSONObject("languages").getString("sourceLanguage");
					finalRecord.remove("languages");
					finalRecord.put("sourceLanguage", sourceLanguage);

					UUID uid = UUID.randomUUID();
					finalRecord.put("id", uid);

					vModel.put("record", finalRecord);
					vModel.put("currentRecordIndex", numberOfRecords);

					datasetValidateKafkaTemplate.send(validateTopic, 0, null, vModel.toString());
					
				}
			}
			
			

		}
		reader.endArray();
		reader.close();
		inputStream.close();
		

		taskTrackerRedisDao.setCountAndIngestComplete(serviceRequestNumber, numberOfRecords);
		
		log.info("data sending for validation serviceRequestNumber :: " + serviceRequestNumber + " total Record :: " + numberOfRecords + " success record :: " + successCount) ;
		
		
	}

	public JSONObject deepMerge(JSONObject source, JSONObject target) throws JSONException {
		for (String key : JSONObject.getNames(source)) {
			Object value = source.get(key);
			if (!target.has(key)) {
				// new value for "key":
				target.put(key, value);
			} else {
				// existing value for "key" - recursively deep merge:
				if (value instanceof JSONObject) {
					JSONObject valueJson = (JSONObject) value;
					deepMerge(valueJson, target.getJSONObject(key));
				} else {
					target.put(key, value);
				}
			}
		}
		return target;
	}

}