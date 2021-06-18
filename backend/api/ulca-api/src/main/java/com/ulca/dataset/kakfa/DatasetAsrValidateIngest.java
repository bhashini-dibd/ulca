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
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.model.deserializer.ASRDatasetRowDataSchemaDeserializer;
import com.ulca.dataset.model.deserializer.ASRParamsSchemaDeserializer;
import com.ulca.dataset.model.deserializer.ParallelDatasetRowSchemaDeserializer;
import com.ulca.dataset.service.ProcessTaskTrackerService;

import io.swagger.model.ASRParamsSchema;
import io.swagger.model.ASRRowSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.ParallelDatasetRowSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasetAsrValidateIngest {

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

		log.info("************ Entry DatasetAsrValidateIngest :: validateIngest *********");
		String serviceRequestNumber = file.getServiceRequestNumber();
		ASRParamsSchema paramsSchema = null;

		Set<String> keys = fileMap.keySet();
		log.info("logging the fileMap keys");
		for (String key : keys) {
			log.info("key :: " + key);
			log.info("value :: " + fileMap.get(key));
		}

		String paramsFilePath = fileMap.get("params.json");
		if (paramsFilePath == null) {
			log.info("params.json file not available");
			processTaskTrackerService.updateTaskTracker(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed);

			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);
			return;
		}
		try {
			paramsSchema = validateParamsSchema(paramsFilePath, file);
			// ingest(paramsSchema, file, fileMap, taskTrackerIngest);

		} catch (IOException | JSONException | NullPointerException e) {
			log.info("Exception while validating params :: " + e.getMessage());
			processTaskTrackerService.updateTaskTracker(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed);

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
			errorMessage.put("stage", "ingest");
			errorMessage.put("datasetType", DatasetType.PARALLEL_CORPUS.toString());
			errorMessage.put("message", e.getMessage());
			datasetErrorPublishService.publishDatasetError(errorMessage);

			e.printStackTrace();
			return;
		}
		try {
			ingest(paramsSchema, file, fileMap);

		} catch (IOException e) {

			log.info("Exception while validating params :: " + e.getMessage());
			processTaskTrackerService.updateTaskTracker(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed);

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
			errorMessage.put("stage", "ingest");
			errorMessage.put("datasetType", DatasetType.PARALLEL_CORPUS.toString());
			errorMessage.put("message", e.getMessage());
			datasetErrorPublishService.publishDatasetError(errorMessage);

			return;
		}

	}

	public ASRParamsSchema validateParamsSchema(String filePath, FileDownload file)
			throws JsonParseException, JsonMappingException, IOException {

		log.info("************ Entry DatasetAsrValidateIngest :: validateParamsSchema *********");
		log.info("validing file :: against params schema");
		log.info(filePath);
		String serviceRequestNumber = file.getServiceRequestNumber();
		log.info(serviceRequestNumber);
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(ASRParamsSchema.class, new ASRParamsSchemaDeserializer());
		mapper.registerModule(module);

		ASRParamsSchema paramsSchema = mapper.readValue(new File(filePath), ASRParamsSchema.class);
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

	public void ingest(ASRParamsSchema paramsSchema, FileDownload file, Map<String, String> fileMap)
			throws IOException {

		log.info("************ Entry DatasetAsrValidateIngest :: ingest *********");

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
			module.addDeserializer(ASRRowSchema.class, new ASRDatasetRowDataSchemaDeserializer());
			mapper.registerModule(module);
			
			ASRRowSchema rowSchema = null;
			try {
				
				rowSchema = mapper.readValue(dataRow, ASRRowSchema.class);
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
				errorMessage.put("stage", "ingest");
				errorMessage.put("datasetType", DatasetType.ASR_CORPUS.toString());
				errorMessage.put("message", e.getMessage());
				datasetErrorPublishService.publishDatasetError(errorMessage);
				
				
				
			}
			if(rowSchema != null) {
				log.info("rowSchema is not null" );
				JSONObject target =  new JSONObject(dataRow);
				
				JSONObject finalRecord = deepMerge(source, target);
				String sourceLanguage = finalRecord.getJSONObject("languages").getString("sourceLanguage");
				finalRecord.remove("languages");
				finalRecord.put("sourceLanguage", sourceLanguage);

				finalRecord.put("fileLocation", fileMap.get(finalRecord.get("audioFilename")));
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