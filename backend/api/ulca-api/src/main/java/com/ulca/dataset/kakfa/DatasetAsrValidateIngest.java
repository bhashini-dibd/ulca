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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ulca.dataset.dao.ProcessTrackerDao;
import com.ulca.dataset.dao.TaskTrackerDao;
import com.ulca.dataset.model.Error;
import com.ulca.dataset.model.ProcessTracker;
import com.ulca.dataset.model.TaskTracker;
import com.ulca.dataset.model.ProcessTracker.StatusEnum;
import com.ulca.dataset.model.TaskTracker.ToolEnum;
import com.ulca.dataset.model.deserializer.ASRParamsSchemaDeserializer;

import io.swagger.model.ASRParamsSchema;
import io.swagger.model.DatasetType;
import io.swagger.model.ParallelDatasetParamsSchema;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;


@Slf4j
@Service
public class DatasetAsrValidateIngest {
	
	
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
	
	public void validateIngest(Map<String,String> fileMap,FileDownload file, ProcessTracker processTracker, TaskTracker taskTrackerIngest) {
		
		log.info("************ Entry DatasetAsrValidateIngest :: validateIngest *********");
		String serviceRequestNumber = file.getServiceRequestNumber();
		ASRParamsSchema paramsSchema = null;

		Set<String> keys = fileMap.keySet();
		log.info("logging the fileMap keys");
		for(String key : keys) {
			log.info("key :: "+key);
			log.info("value :: " + fileMap.get(key));
		}
		
		String paramsFilePath = fileMap.get("params.json");
		try {
			paramsSchema = validateParamsSchema(paramsFilePath);
			//ingest(paramsSchema, file, fileMap, taskTrackerIngest);

		} catch (IOException e) {

			taskTrackerIngest.setLastModified(new Date().toString());
			taskTrackerIngest.setEndTime(new Date().toString());
			taskTrackerIngest.setTool(ToolEnum.ingest);
			taskTrackerIngest.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.failed);
			Error error = new Error();
			error.setCause(e.getMessage());
			error.setMessage("params validation failed");
			error.setCode("1000_PARAMS_VALIDATION_FAILED");
			taskTrackerIngest.setError(error);
			taskTrackerIngest.setServiceRequestNumber(serviceRequestNumber);

			taskTrackerDao.save(taskTrackerIngest);
			processTracker.setStatus(StatusEnum.failed);
			processTrackerDao.save(processTracker);

			// send error event
			JSONObject errorMessage = new JSONObject();
			errorMessage.put("eventType", "dataset-training");
			errorMessage.put("messageType", "error");
			errorMessage.put("code", "1000_PARAMS_VALIDATION_FAILED");
			errorMessage.put("eventId", "serviceRequestNumber|" + serviceRequestNumber);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
			Date date = cal.getTime();
			errorMessage.put("timestamp", df2.format(date));
			errorMessage.put("serviceRequestNumber", serviceRequestNumber);
			errorMessage.put("stage", "ingest");
			errorMessage.put("datasetType", DatasetType.PARALLEL_CORPUS.toString());
			errorMessage.put("message", e.getMessage());
			datasetErrorPublishService.publishDatasetError(errorMessage);

			e.printStackTrace();
		}
		ingest(paramsSchema, file, fileMap, taskTrackerIngest);
		
	}
	
	public ASRParamsSchema validateParamsSchema(String filePath) throws JsonParseException, JsonMappingException, IOException {
		
		log.info("************ Entry DatasetAsrValidateIngest :: validateParamsSchema *********");
		log.info("validing file :: against params schema");
		log.info(filePath);
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(ASRParamsSchema.class, new ASRParamsSchemaDeserializer());
		mapper.registerModule(module);

		ASRParamsSchema paramsSchema = mapper.readValue(new File(filePath), ASRParamsSchema.class);

	   if(paramsSchema != null) {
		   log.info("Asr params validated");
	   }
			
	   return paramsSchema;
		
	}
	
	public void ingest(ASRParamsSchema paramsSchema, FileDownload file, Map<String, String> fileMap,
			TaskTracker taskTrackerIngest) {

		log.info("************ Entry DatasetAsrValidateIngest :: ingest *********");

		String datasetId = file.getDatasetId();
		String serviceRequestNumber = file.getServiceRequestNumber();
		String userId = file.getUserId();

		Set<String> keys = fileMap.keySet();
		log.info("logging the fileMap keys");
		for(String key : keys) {
			log.info("key :: "+key);
			log.info("value :: " + fileMap.get(key));
		}
		
		if (paramsSchema != null) {

			log.info("got paramsSchema object");

			ObjectMapper objectMapper = new ObjectMapper();

			JSONObject source;
			try {
				String path = fileMap.get("data.json");
				log.info("json.data file path :: " + path);
				
				source = new JSONObject(objectMapper.writeValueAsString(paramsSchema));
				
				InputStream inputStream = Files.newInputStream(Path.of(path));
				
				log.info("inputStream file done ");
				
	            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
	            
	        	log.info("json reader created ");
	        	
	            int numberOfRecords = 0;
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
		        	
		        	Object test = new Gson().fromJson(reader, Object.class);
		        	
		        	ObjectMapper mapper = new ObjectMapper();
		        	
		        	JSONObject target = new JSONObject(mapper.writeValueAsString(test));
		        	
		        	
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
		        	
		        	
		        }
		        reader.endArray();

				log.info("Eof reached");
				vModel.put("eof", true);
				vModel.remove("record");
				vModel.remove("currentRecordIndex");
				
				
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

				taskTrackerIngest.setLastModified(new Date().toString());
				taskTrackerIngest.setEndTime(new Date().toString());
				taskTrackerIngest.setDetails(details.toString());
				taskTrackerIngest.setStatus(com.ulca.dataset.model.TaskTracker.StatusEnum.successful);
				taskTrackerIngest.setServiceRequestNumber(file.getServiceRequestNumber());

				taskTrackerDao.save(taskTrackerIngest);

			} catch (JsonProcessingException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			log.info("sent record for validation ");
		} else {
			log.info("paramsSchema object is null");
		}

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
	public  JSONObject deepMerge(JSONObject source, JSONObject target) throws JSONException {
	    for (String key: JSONObject.getNames(source)) {
	            Object value = source.get(key);
	            if (!target.has(key)) {
	                // new value for "key":
	                target.put(key, value);
	            } else {
	                // existing value for "key" - recursively deep merge:
	                if (value instanceof JSONObject) {
	                    JSONObject valueJson = (JSONObject)value;
	                    deepMerge(valueJson, target.getJSONObject(key));
	                } else {
	                    target.put(key, value);
	                }
	            }
	    }
	    return target;
	}
	
}