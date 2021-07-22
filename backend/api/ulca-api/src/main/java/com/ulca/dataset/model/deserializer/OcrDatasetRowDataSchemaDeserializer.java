package com.ulca.dataset.model.deserializer;

import java.io.IOException;
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

import io.swagger.model.BoundingBox;
import io.swagger.model.CollectionDetailsOcr;
import io.swagger.model.OcrCollectionMethod;
import io.swagger.model.OcrDatasetRowSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OcrDatasetRowDataSchemaDeserializer extends StdDeserializer<OcrDatasetRowSchema> {

	protected OcrDatasetRowDataSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public OcrDatasetRowDataSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public OcrDatasetRowSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		OcrDatasetRowSchema asrRowSchema = new OcrDatasetRowSchema();
		JsonNode node = p.readValueAsTree();
		
		ArrayList<String> keysList = new ArrayList<String>();
		keysList.add("imageFilename");
		keysList.add("groundTruth");
		keysList.add("boundingBox");
		keysList.add("collectionMethod");
		
		ArrayList<String> errorList = new ArrayList<String>();

		JSONObject obj = new JSONObject(node.toPrettyString());

		Set<String> keys = obj.keySet();
		if(!keysList.containsAll(keys)) {
			errorList.add("json key should only contain imageFilename, groundTruth,boundingBox, collectionMethod ");
		}
		
		// required

		if (!node.has("imageFilename")) {
			errorList.add("imageFilename field should be present");
		} else if (!node.get("imageFilename").isTextual()) {
			errorList.add("imageFilename field should be String");
		} else {

			String imageFilename = node.get("imageFilename").asText();
			asrRowSchema.setImageFilename(imageFilename);

		}
		
		if (!node.has("groundTruth")) {
			errorList.add("groundTruth field should be present");
		} else if (!node.get("groundTruth").isTextual()) {
			errorList.add("groundTruth field should be String");
		} else {

			String groundTruth = node.get("groundTruth").asText();
			asrRowSchema.setGroundTruth(groundTruth);

		}
		
		// optional params
		
		if (node.has("boundingBox")) {
			if(node.get("boundingBox").has("vertices")) {
				if(!node.get("boundingBox").get("vertices").isArray()) {
					errorList.add("vertices field should be Array");
				}else {
					try {
						BoundingBox boundingBox  = mapper.readValue(node.get("boundingBox").toPrettyString(), BoundingBox.class);
						asrRowSchema.setBoundingBox(boundingBox);
					}catch (Exception e) {
						errorList.add("problem with vertices value");
						log.info("problem with vertices value");
					}
					
				}
			}
			
		} 

		if(node.has("collectionMethod")) {
			if (!node.get("collectionMethod").has("collectionDescription")) {
				if (!node.get("collectionMethod").get("collectionDescription").isArray()) {
					errorList.add("collectionDescription field should be String Array");
				} else {
					int size = node.get("collectionMethod").get("collectionDescription").size();
					if(size > 10 || size < 1) {
						errorList.add("collectionDescription field Array should contain atleast 1");
					}else {
						try {
							String collectionDescription = node.get("collectionMethod").get("collectionDescription").get(0)
									.asText();
							
							
							OcrCollectionMethod.CollectionDescriptionEnum collectionDescriptionEnum = OcrCollectionMethod.CollectionDescriptionEnum
									.fromValue(collectionDescription);

							OcrCollectionMethod ocrCollectionMethod = new OcrCollectionMethod();
							
							List<OcrCollectionMethod.CollectionDescriptionEnum> list = new ArrayList<OcrCollectionMethod.CollectionDescriptionEnum>();
							list.add(collectionDescriptionEnum);
							ocrCollectionMethod.setCollectionDescription(list);
							
							if(!node.get("collectionMethod").get("collectionDetails").has("ocrTool")){
								errorList.add("collectionDetails should contain ocrTool");
							}else if(!node.get("collectionMethod").get("collectionDetails").get("ocrTool").isTextual()) {
								errorList.add("ocrTool should be String");
							}else {
								String ocrTool = node.get("collectionMethod").get("collectionDetails").get("ocrTool").asText();
								CollectionDetailsOcr.OcrToolEnum ocrToolEnum = CollectionDetailsOcr.OcrToolEnum.fromValue(ocrTool);
								if(ocrToolEnum != null) {
									CollectionDetailsOcr collectionDetailsOcr = new CollectionDetailsOcr();
									collectionDetailsOcr.setOcrTool(ocrToolEnum);
									if(node.get("collectionMethod").get("collectionDetails").has("ocrToolVersion")) {
										String ocrToolVersion = node.get("collectionMethod").get("collectionDetails").get("ocrToolVersion").asText();
										collectionDetailsOcr.setOcrToolVersion(ocrToolVersion);
									}
									ocrCollectionMethod.setCollectionDetails(collectionDetailsOcr);
									
									asrRowSchema.setCollectionMethod(ocrCollectionMethod);
									
								}else {
									errorList.add("ocrToolEnum should be one of specified values");
								}
							}


						} catch (Exception e) {
							System.out.println("collection method not proper");
							errorList.add("collectionMethod field value not proper.");
							System.out.println("tracing the error");
							
							e.printStackTrace();
						}
					}

				}
			}else {
				errorList.add("if collectionMethod then collectionDescription should be present inside collectionMethod");
			}
		}

		if (!errorList.isEmpty())
			throw new IOException(errorList.toString());

		return asrRowSchema;
	}

}
