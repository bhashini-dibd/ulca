package com.ulca.dataset.kakfa;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.service.ProcessTaskTrackerService;

import io.swagger.model.DatasetType;
import io.swagger.model.ParallelDatasetParamsSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasetParallelCorpusValidateIngest {

	@Autowired
	ProcessTaskTrackerService processTaskTrackerService;

	@Autowired
	DatasetErrorPublishService datasetErrorPublishService;

	@Autowired
	private KafkaTemplate<String, String> datasetValidateKafkaTemplate;

	@Value(value = "${KAFKA_ULCA_DS_VALIDATE_IP_TOPIC}")
	private String validateTopic;

	public static final String SOURCE_TEXT = "sourceText";
	public static final String SOURCE_TEXT_HASH = "sourceTextHash";
	public static final String TARGET_TEXT = "targetText";
	public static final String TARGET_TEXT_HASH = "targetTextHash";

	public void validateIngest(Map<String, String> fileMap, FileDownload file, TaskTracker taskTrackerIngest) {

		log.info("************ Entry DatasetParallelCorpusValidateIngest :: validateIngest *********");

		String serviceRequestNumber = file.getServiceRequestNumber();
		ParallelDatasetParamsSchema paramsSchema = null;

		String paramsFilePath = fileMap.get("params.json");
		try {
			paramsSchema = validateParamsSchema(paramsFilePath);

		} catch (IOException e) {

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
			errorMessage.put("stage", "ingest");
			errorMessage.put("datasetType", DatasetType.PARALLEL_CORPUS.toString());
			errorMessage.put("message", e.getMessage());
			datasetErrorPublishService.publishDatasetError(errorMessage);

			e.printStackTrace();
		}
		try {
			ingest(paramsSchema, file, fileMap);
		} catch (IOException | JSONException | NoSuchAlgorithmException e) {
			processTaskTrackerService.updateTaskTracker(serviceRequestNumber, ToolEnum.ingest,
					com.ulca.dataset.model.TaskTracker.StatusEnum.failed);

			processTaskTrackerService.updateProcessTracker(serviceRequestNumber, StatusEnum.failed);
		}

	}

	public ParallelDatasetParamsSchema validateParamsSchema(String filePath)
			throws JsonParseException, JsonMappingException, IOException {

		log.info("************ Entry DatasetParallelCorpusValidateIngest :: validateParamsSchema *********");
		log.info("validing file :: against params schema");
		log.info(filePath);
		ObjectMapper mapper = new ObjectMapper();

		ParallelDatasetParamsSchema paramsSchema = mapper.readValue(new File(filePath),
				ParallelDatasetParamsSchema.class);

		return paramsSchema;

	}

	public void ingest(ParallelDatasetParamsSchema paramsSchema, FileDownload file, Map<String, String> fileMap)
			throws JSONException, IOException, NoSuchAlgorithmException {

		log.info("************ Entry DatasetParallelCorpusValidateIngest :: ingest *********");

		String datasetId = file.getDatasetId();
		String serviceRequestNumber = file.getServiceRequestNumber();
		String userId = file.getUserId();

		ObjectMapper objectMapper = new ObjectMapper();

		JSONObject record;

		record = new JSONObject(objectMapper.writeValueAsString(paramsSchema));

		String dataFilePath = fileMap.get("data.json");

		log.info("data.json file path :: " + dataFilePath);

		File jsonFile = new File(dataFilePath);
		JsonFactory jsonfactory = new JsonFactory(); // init factory
		JsonParser jsonParser = jsonfactory.createParser(jsonFile); // create JSON parser
		JsonToken jsonToken = jsonParser.nextToken();
		int numberOfRecords = 0;
		JSONObject vModel = new JSONObject();
		vModel.put("record", record);
		vModel.put("datasetId", datasetId);
		vModel.put("datasetType", paramsSchema.getDatasetType().toString());
		vModel.put("serviceRequestNumber", serviceRequestNumber);
		vModel.put("userId", userId);
		vModel.put("userMode", "real");
		int currentCount = 0;
		int batchCount = 999;

		while (jsonToken != JsonToken.END_ARRAY) {
			String fieldname = jsonParser.getCurrentName();
			if (SOURCE_TEXT.equals(fieldname)) {
				jsonToken = jsonParser.nextToken(); // read next token
				record.put(SOURCE_TEXT, jsonParser.getText());
				record.put(SOURCE_TEXT_HASH, getSha256Hash(jsonParser.getText()));
			}
			if (TARGET_TEXT.equals(fieldname)) {
				jsonToken = jsonParser.nextToken();
				record.put(TARGET_TEXT, jsonParser.getText());
				record.put(TARGET_TEXT_HASH, getSha256Hash(jsonParser.getText()));
			}
			if (jsonToken == JsonToken.END_OBJECT) {
				// do some processing, Indexing, saving in DB etc..

				numberOfRecords++;
				UUID uid = UUID.randomUUID();
				record.put("id", uid);
				if (record.has("languages")) {
					JSONObject language = record.getJSONObject("languages");
					String sourceLanguage = language.getString("sourceLanguage");
					String targetLanguage = language.getString("targetLanguage");
					record.put("sourceLanguage", sourceLanguage);
					record.put("targetLanguage", targetLanguage);
					record.remove("languages");
				}

				vModel.put("currentRecordIndex", numberOfRecords);
				currentCount++;
				// validate the current record
				// if validation success then send to validate topic
				// else

				datasetValidateKafkaTemplate.send(validateTopic, 0, null, vModel.toString());

				if (currentCount == batchCount) {

					currentCount = 0;
					// update the task tracker
					JSONObject details = new JSONObject();
					details.put("currentRecordIndex", currentCount);

					JSONArray processedCount = new JSONArray();

					JSONObject proCountSuccess = new JSONObject();
					proCountSuccess.put("type", "success");
					proCountSuccess.put("count", currentCount);
					processedCount.put(proCountSuccess);

					JSONObject proCountFailure = new JSONObject();

					proCountFailure.put("type", "failed");
					proCountFailure.put("count", 0);
					processedCount.put(proCountFailure);
					details.put("processedCount", processedCount);
					details.put("timeStamp", new Date().toString());

					processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.ingest,
							com.ulca.dataset.model.TaskTracker.StatusEnum.inprogress, details.toString());

				}

			}
			jsonToken = jsonParser.nextToken();

		}

		vModel.put("eof", true);
		vModel.remove("record");
		vModel.remove("currentRecordIndex");

		log.info("Eof reached");
		jsonParser.close();
		datasetValidateKafkaTemplate.send(validateTopic, 0, null, vModel.toString());

		JSONObject details = new JSONObject();
		details.put("currentRecordIndex", numberOfRecords);

		JSONArray processedCount = new JSONArray();

		JSONObject proCountSuccess = new JSONObject();
		proCountSuccess.put("type", "success");
		proCountSuccess.put("count", numberOfRecords);
		processedCount.put(proCountSuccess);

		JSONObject proCountFailure = new JSONObject();

		proCountFailure.put("type", "failed");
		proCountFailure.put("count", 0);
		processedCount.put(proCountFailure);
		details.put("processedCount", processedCount);
		details.put("timeStamp", new Date().toString());

		processTaskTrackerService.updateTaskTrackerWithDetails(serviceRequestNumber, ToolEnum.ingest,
				com.ulca.dataset.model.TaskTracker.StatusEnum.successful, details.toString());

		log.info("sent record for validation ");

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
