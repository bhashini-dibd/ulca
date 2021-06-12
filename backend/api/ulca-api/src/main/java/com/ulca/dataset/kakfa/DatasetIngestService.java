package com.ulca.dataset.kakfa;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.dataset.dao.ProcessTrackerDao;

import io.swagger.model.ParallelDatasetParamsSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatasetIngestService {
	
	
	@Autowired
	private KafkaTemplate<String, String> datasetValidateKafkaTemplate;

	@Value(value = "${KAFKA_ULCA_DS_VALIDATE_IP_TOPIC}")
	private String validateTopic;
	
	public static final String SOURCE_TEXT = "sourceText";
	public static final String SOURCE_TEXT_HASH = "sourceTextHash";
	public static final String TARGET_TEXT = "targetText";
	public static final String TARGET_TEXT_HASH = "targetTextHash";

	public void datasetIngest(ParallelDatasetParamsSchema paramsSchema, FileDownload file,
			Map<String, String> fileMap) {

		log.info("************ Entry DatasetIngestService :: datasetIngest *********");

		String datasetId = file.getDatasetId();
		String serviceRequestNumber = file.getServiceRequestNumber();
		String userId = file.getUserId();

		if (paramsSchema != null) {

			log.info("got paramsSchema object");

			ObjectMapper objectMapper = new ObjectMapper();

			JSONObject record;
			try {
				record = new JSONObject(objectMapper.writeValueAsString(paramsSchema));

				File jsonFile = new File(fileMap.get("data"));
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

						datasetValidateKafkaTemplate.send(validateTopic, vModel.toString());

					}
					jsonToken = jsonParser.nextToken();

				}

				vModel.put("eof", true);
				vModel.remove("record");
				vModel.remove("currentRecordIndex");

				log.info("Eof reached");
				jsonParser.close();
				datasetValidateKafkaTemplate.send(validateTopic, vModel.toString());

			} catch (JsonProcessingException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			log.info("send record for validation ");
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

}
