package com.ulca.dataset.model.deserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.swagger.model.ASRParamsSchema;
import io.swagger.model.ASRParamsSchema.AgeEnum;
import io.swagger.model.ASRParamsSchema.DialectEnum;
import io.swagger.model.ASRRowSchema;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioFormat;
import io.swagger.model.AudioQualityEvaluation;
import io.swagger.model.AudioQualityEvaluation.MethodTypeEnum;
import io.swagger.model.BoundingBox;
import io.swagger.model.CollectionDetailsAudioAutoAligned;
import io.swagger.model.CollectionDetailsMachineGeneratedTranscript;
import io.swagger.model.CollectionDetailsManualTranscribed;
import io.swagger.model.CollectionDetailsOcr;
import io.swagger.model.CollectionMethodAudio;
import io.swagger.model.DatasetType;
import io.swagger.model.DocumentLayoutRowSchema;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.Gender;
import io.swagger.model.LanguagePair;
import io.swagger.model.OcrCollectionMethod;
import io.swagger.model.OcrDatasetRowSchema;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.model.TranscriptionEvaluationMethod1;
import io.swagger.model.WadaSnr;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentLayoutDatasetRowDataSchemaDeserializer extends StdDeserializer<DocumentLayoutRowSchema> {

	protected DocumentLayoutDatasetRowDataSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public DocumentLayoutDatasetRowDataSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DocumentLayoutRowSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** inside deserializer ********");
		ObjectMapper mapper = new ObjectMapper();
		DocumentLayoutRowSchema docLayoutRowSchema = new DocumentLayoutRowSchema();
		JsonNode node = p.readValueAsTree();

		ArrayList<String> keysList = new ArrayList<String>();
		keysList.add("imageFilename");
		keysList.add("layoutClass");
		keysList.add("boundingBox");
		
		ArrayList<String> errorList = new ArrayList<String>();

		JSONObject obj = new JSONObject(node.toPrettyString());

		Set<String> keys = obj.keySet();
		if(!keysList.containsAll(keys)) {
			errorList.add("json key should only contain imageFilename, layoutClass, boundingBox  ");
		}
		
		// required

		if (!node.has("imageFilename")) {
			errorList.add("imageFilename field should be present");
		} else if (!node.get("imageFilename").isTextual()) {
			errorList.add("imageFilename field should be String");
		} else {

			String imageFilename = node.get("imageFilename").asText();
			docLayoutRowSchema.setImageFilename(imageFilename);

		}
		
		if (!node.has("layoutClass")) {
			errorList.add("layoutClass field should be present");
		} else if (!node.get("layoutClass").isTextual()) {
			errorList.add("layoutClass field should be String");
		} else {

			String layoutClass = node.get("layoutClass").asText();
			
			DocumentLayoutRowSchema.LayoutClassEnum  layoutClassEnum = DocumentLayoutRowSchema.LayoutClassEnum.fromValue(layoutClass);
			
			if(layoutClassEnum != null) {
				docLayoutRowSchema.setLayoutClass(layoutClassEnum);
			}else {
				errorList.add("layoutClass should be one of defined values");
			}
			

		}
		
		
		if (!node.has("boundingBox")) {
			errorList.add("boundingBox field should be present");
		}else if(!node.get("boundingBox").has("vertices")) {
			errorList.add("vertices field should be present");
		 }else if(!node.get("boundingBox").get("vertices").isArray()) {
				errorList.add("vertices field should be Array");
			}else {
				try {
					BoundingBox boundingBox  = mapper.readValue(node.get("boundingBox").toPrettyString(), BoundingBox.class);
					docLayoutRowSchema.setBoundingBox(boundingBox);
				}catch (Exception e) {
					errorList.add("problem with vertices value");
					log.info("problem with vertices value");
				}
				
			}
		

		if (!errorList.isEmpty())
			throw new IOException(errorList.toString());

		log.info("******** Exiting deserializer ********");
		return docLayoutRowSchema;
	}

}
