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
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.model.deserializer.DocumentLayoutDatasetParamsSchemaDeserializer;
import com.ulca.dataset.model.deserializer.DocumentLayoutDatasetRowDataSchemaDeserializer;
import com.ulca.dataset.service.ProcessTaskTrackerService;

import io.swagger.model.DatasetType;
import io.swagger.model.DocumentLayoutParamsSchema;
import io.swagger.model.DocumentLayoutRowSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasetDocumentLayoutValidateIngest {

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

	@Value(value = "${KAFKA_ULCA_DS_VALIDATE_IP_TOPIC}")
	private String validateTopic;

	public void validateIngest(Map<String, String> fileMap, FileDownload file) {

		log.info("************ Entry DatasetDocumentLayoutValidateIngest :: validateIngest *********");
		String serviceRequestNumber = file.getServiceRequestNumber();
		DocumentLayoutParamsSchema paramsSchema = null;

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
			JSONObject errorMessage = new JSONObject();
			errorMessage.put("eventType", "dataset-training");
			errorMessage.put("messageType", "error");
			errorMessage.put("code", "1000_PARAMS_VALIDATION_FAILED");
			errorMessage.put("eventId", "serviceRequestNumber|" + serviceRequestNumber);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
			Date date = cal.getTime();
			// errorMessage.put("timestamp", df2.format(date));
			errorMessage.put("timestamp", new Date().toString());
			errorMessage.put("serviceRequestNumber", serviceRequestNumber);
			errorMessage.put("datasetName", file.getDatasetName());
			errorMessage.put("stage", "ingest");
			errorMessage.put("datasetType", DatasetType.DOCUMENT_LAYOUT_CORPUS.toString());
			errorMessage.put("message", e.getMessage());
			datasetErrorPublishService.publishDatasetError(errorMessage);

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
			JSONObject errorMessage = new JSONObject();
			errorMessage.put("eventType", "dataset-training");
			errorMessage.put("messageType", "error");
			errorMessage.put("code", "1000_PARAMS_VALIDATION_FAILED");
			errorMessage.put("eventId", "serviceRequestNumber|" + serviceRequestNumber);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
			Date date = cal.getTime();
			// errorMessage.put("timestamp", df2.format(date));
			errorMessage.put("timestamp", new Date().toString());
			errorMessage.put("serviceRequestNumber", serviceRequestNumber);
			errorMessage.put("datasetName", file.getDatasetName());
			errorMessage.put("stage", "ingest");
			errorMessage.put("datasetType", DatasetType.PARALLEL_CORPUS.toString());
			errorMessage.put("message", e.getMessage());
			datasetErrorPublishService.publishDatasetError(errorMessage);

			return;
		}

	}

	public DocumentLayoutParamsSchema validateParamsSchema(String filePath, FileDownload file)
			throws JsonParseException, JsonMappingException, IOException {

		log.info("************ Entry DatasetDocumentLayoutValidateIngest :: validateParamsSchema *********");
		log.info("validing file :: against params schema");
		log.info(filePath);
		String serviceRequestNumber = file.getServiceRequestNumber();
		log.info(serviceRequestNumber);
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(DocumentLayoutParamsSchema.class, new DocumentLayoutDatasetParamsSchemaDeserializer());
		mapper.registerModule(module);

		DocumentLayoutParamsSchema paramsSchema = mapper.readValue(new File(filePath), DocumentLayoutParamsSchema.class);
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

	public void ingest(DocumentLayoutParamsSchema paramsSchema, FileDownload file, Map<String, String> fileMap)
			throws IOException {

		log.info("************ Entry DatasetDocumentLayoutValidateIngest :: ingest *********");

		String datasetId = file.getDatasetId();
		String serviceRequestNumber = file.getServiceRequestNumber();
		String userId = file.getUserId();

		Set<String> keys = fileMap.keySet();
		log.info("logging the fileMap keys");
		for (String key : keys) {
			log.info("key :: " + key);
			log.info("value :: " + fileMap.get(key));
		}

		log.info("got paramsSchema object");

		ObjectMapper objectMapper = new ObjectMapper();

		JSONObject source;

		String path = fileMap.get("data.json");
		log.info("json.data file path :: " + path);

		source = new JSONObject(objectMapper.writeValueAsString(paramsSchema));

		InputStream inputStream = Files.newInputStream(Path.of(path));

		log.info("inputStream file done ");

		JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

		log.info("json reader created ");

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

		reader.beginArray();
		while (reader.hasNext()) {

			numberOfRecords++;
			log.info("reading records :: " + numberOfRecords);
			Object rowObj = new Gson().fromJson(reader, Object.class);

			ObjectMapper mapper = new ObjectMapper();
			
			String dataRow = mapper.writeValueAsString(rowObj);
			SimpleModule module = new SimpleModule();
			module.addDeserializer(DocumentLayoutRowSchema.class, new DocumentLayoutDatasetRowDataSchemaDeserializer());
			mapper.registerModule(module);
			
			DocumentLayoutRowSchema rowSchema = null;
			try {
				
				rowSchema = mapper.readValue(dataRow, DocumentLayoutRowSchema.class);
				log.info("row schema created");				
				
			} catch(Exception e) {
				
				log.info("record :: " +numberOfRecords + "failed " );
				log.info("tracing the error " );
				e.printStackTrace();
				
				failedCount++;
				// send error event
				JSONObject errorMessage = new JSONObject();
				errorMessage.put("eventType", "dataset-training");
				errorMessage.put("messageType", "error");
				errorMessage.put("code", "1000_ROW_DATA_VALIDATION_FAILED");
				errorMessage.put("eventId", "serviceRequestNumber|" + serviceRequestNumber);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
				Date date = cal.getTime();
				// errorMessage.put("timestamp", df2.format(date));
				errorMessage.put("timestamp", new Date().toString());
				errorMessage.put("serviceRequestNumber", serviceRequestNumber);
				errorMessage.put("datasetName", file.getDatasetName());
				errorMessage.put("stage", "ingest");
				errorMessage.put("datasetType", DatasetType.ASR_CORPUS.toString());
				errorMessage.put("message", e.getMessage());
				datasetErrorPublishService.publishDatasetError(errorMessage);
				
				
			}
			if(rowSchema != null) {
				log.info("rowSchema is not null" );
				JSONObject target =  new JSONObject(dataRow);
				
				JSONObject finalRecord = deepMerge(source, target);

				finalRecord.put("fileLocation", fileMap.get(finalRecord.get("imageFilename")));
				UUID uid = UUID.randomUUID();
				finalRecord.put("id", uid);

				vModel.put("record", finalRecord);
				vModel.put("currentRecordIndex", numberOfRecords);

				datasetValidateKafkaTemplate.send(validateTopic, 0, null, vModel.toString());
				successCount++;
				log.info("data row " + numberOfRecords + " sent for validation ");
			}

			

		}
		reader.endArray();
		reader.close();
		inputStream.close();

		log.info("Eof reached");
		vModel.put("eof", true);
		vModel.remove("record");
		vModel.remove("currentRecordIndex");
		if(failedCount < numberOfRecords) {
			datasetValidateKafkaTemplate.send(validateTopic, 0, null, vModel.toString());
		}
		

		JSONObject details = new JSONObject();
		details.put("currentRecordIndex", numberOfRecords);

		JSONArray processedCount = new JSONArray();

		JSONObject proCountSuccess = new JSONObject();
		proCountSuccess.put("type", "success");
		proCountSuccess.put("count", successCount);
		processedCount.put(proCountSuccess);

		JSONObject proCountFailure = new JSONObject();

		proCountFailure.put("type", "failed");
		proCountFailure.put("count", failedCount);
		processedCount.put(proCountFailure);
		details.put("processedCount", processedCount);
		details.put("timeStamp", new Date().toString());

		processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.ingest,
				com.ulca.dataset.model.TaskTracker.StatusEnum.successful, details.toString());

		log.info("sent record for validation ");

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