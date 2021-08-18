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

import io.swagger.model.CollectionDetailsAutoAligned;
import io.swagger.model.CollectionDetailsMachineTranslated;
import io.swagger.model.CollectionDetailsMachineTranslatedPostEdited;
import io.swagger.model.CollectionDetailsManualTranslated;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.LanguagePair;
import io.swagger.model.ParallelDatasetCollectionMethod;
import io.swagger.model.ParallelDatasetParamsSchema;
import io.swagger.model.ParallelDatasetRowSchema;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParallelDatasetRowSchemaDeserializer extends StdDeserializer<ParallelDatasetRowSchema> {

	protected ParallelDatasetRowSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public ParallelDatasetRowSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ParallelDatasetRowSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		
		ArrayList<String> keysList = new ArrayList<String>();
		keysList.add("sourceText");
		keysList.add("targetText");
		keysList.add("collectionMethod");
		

		ObjectMapper mapper = new ObjectMapper();

		ParallelDatasetRowSchema parallelRowSchema = new ParallelDatasetRowSchema();
		JsonNode node = p.readValueAsTree();
		
		ArrayList<String> errorList = new ArrayList<String>();
		
		JSONObject obj = new JSONObject(node.toPrettyString());
		
		Set<String> keys = obj.keySet();
		if(!keysList.containsAll(keys)) {
			errorList.add("json key should only contain sourceText, targetText,collectionMethod ");
		}
		
		
		
		
		if (!node.has("sourceText")) {
			errorList.add("sourceText field should be present");
		} else if (!node.get("sourceText").isTextual()) {
			errorList.add("sourceText field should be String");
		} else {
			String sourceText = node.get("sourceText").asText();
			
			parallelRowSchema.setSourceText(sourceText);

		}
		
		if (!node.has("targetText")) {
			errorList.add("targetText field should be present");
		} else if (!node.get("targetText").isTextual()) {
			errorList.add("targetText field should be String");
		} else {
			String sourceText = node.get("targetText").asText();
			
			parallelRowSchema.setSourceText(sourceText);

		}
		
		
		if(node.has("collectionMethod")) {
			if (node.get("collectionMethod").has("collectionDescription")) {
				if (!node.get("collectionMethod").get("collectionDescription").isArray()) {
					errorList.add("collectionDescription field should be String Array");
				} else {

					try {
						String collectionDescription = node.get("collectionMethod").get("collectionDescription").get(0)
								.asText();
						ParallelDatasetCollectionMethod.CollectionDescriptionEnum collectionDescriptionEnum = ParallelDatasetCollectionMethod.CollectionDescriptionEnum
								.fromValue(collectionDescription);

						ParallelDatasetCollectionMethod parallelDatasetCollectionMethod = new ParallelDatasetCollectionMethod();
						List<ParallelDatasetCollectionMethod.CollectionDescriptionEnum> list = new ArrayList<ParallelDatasetCollectionMethod.CollectionDescriptionEnum>();
						list.add(collectionDescriptionEnum);
						parallelDatasetCollectionMethod.setCollectionDescription(list);

						switch (collectionDescriptionEnum) {
						case AUTO_ALIGNED:
							if(node.get("collectionMethod").get("collectionDetails").has("alignmentTool")) {
								String alignmentTool = node.get("collectionMethod").get("collectionDetails").get("alignmentTool").asText();
								if(CollectionDetailsAutoAligned.AlignmentToolEnum.fromValue(alignmentTool) == null ) {
									errorList.add("alignmentTool is not one of defined alignmentTool");	
								}else {
									CollectionDetailsAutoAligned collectionDetailsAutoAligned = mapper.readValue(
											node.get("collectionMethod").get("collectionDetails").toPrettyString(),
											CollectionDetailsAutoAligned.class);
									parallelDatasetCollectionMethod.setCollectionDetails(collectionDetailsAutoAligned);
									parallelRowSchema.setCollectionMethod(parallelDatasetCollectionMethod);
								}
							}
							
							break;

						case MACHINE_TRANSLATED:

							CollectionDetailsMachineTranslated collectionDetailsMachineTranslated = mapper.readValue(
									node.get("collectionMethod").get("collectionDetails").toPrettyString(),
									CollectionDetailsMachineTranslated.class);
							parallelDatasetCollectionMethod.setCollectionDetails(collectionDetailsMachineTranslated);
							parallelRowSchema.setCollectionMethod(parallelDatasetCollectionMethod);
							break;

						case MACHINE_TRANSLATED_POST_EDITED:

							CollectionDetailsMachineTranslatedPostEdited collectionDetailsMachineTranslatedPostEdited = mapper
									.readValue(node.get("collectionMethod").get("collectionDetails").toPrettyString(),
											CollectionDetailsMachineTranslatedPostEdited.class);
							parallelDatasetCollectionMethod
									.setCollectionDetails(collectionDetailsMachineTranslatedPostEdited);
							parallelRowSchema.setCollectionMethod(parallelDatasetCollectionMethod);
							break;

						case MANUAL_TRANSLATED:

							CollectionDetailsManualTranslated collectionDetailsManualTranslated = mapper.readValue(
									node.get("collectionMethod").get("collectionDetails").toPrettyString(),
									CollectionDetailsManualTranslated.class);
							parallelDatasetCollectionMethod.setCollectionDetails(collectionDetailsManualTranslated);
							parallelRowSchema.setCollectionMethod(parallelDatasetCollectionMethod);
							break;

						}

					} catch (Exception e) {
						System.out.println("collection method not proper");
						errorList.add("collectionMethod field value not proper.");
						System.out.println("tracing the error");
						
						e.printStackTrace();
					}

				}
			}
		}

		
		if(!errorList.isEmpty())
			throw new IOException(errorList.toString());
		

		return parallelRowSchema;
	}

}
