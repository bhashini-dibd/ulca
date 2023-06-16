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
import io.swagger.model.CollectionDetailsMachineGenerated;
import io.swagger.model.CollectionDetailsMachineGeneratedPostEdited;
import io.swagger.model.CollectionDetailsMachineTranslated;
import io.swagger.model.CollectionDetailsMachineTranslatedPostEdited;
import io.swagger.model.CollectionDetailsManualCurated;
import io.swagger.model.CollectionDetailsManualTranslated;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.LanguagePair;
import io.swagger.model.NerData;
import io.swagger.model.NerDataArray;
import io.swagger.model.NerDatasetCollectionMethod;
import io.swagger.model.NerDatasetRowSchema;
import io.swagger.model.ParallelDatasetCollectionMethod;
import io.swagger.model.ParallelDatasetParamsSchema;
import io.swagger.model.ParallelDatasetRowSchema;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NerDatasetRowSchemaDeserializer extends StdDeserializer<NerDatasetRowSchema> {

	protected NerDatasetRowSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public NerDatasetRowSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public NerDatasetRowSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		
		ArrayList<String> keysList = new ArrayList<String>();
		keysList.add("sourceText");
		keysList.add("nerData");
		keysList.add("collectionMethod");
		

		ObjectMapper mapper = new ObjectMapper();

		NerDatasetRowSchema nerRowSchema = new NerDatasetRowSchema();
		JsonNode node = p.readValueAsTree();
		
		ArrayList<String> errorList = new ArrayList<String>();
		
		JSONObject obj = new JSONObject(node.toPrettyString());
		
		Set<String> keys = obj.keySet();
		if(!keysList.containsAll(keys)) {
			errorList.add("json key should only contain sourceText, nerData,collectionMethod ");
		}
		
		
		
		
		if (!node.has("sourceText")) {
			errorList.add("sourceText field should be present");
		} else if (!node.get("sourceText").isTextual()) {
			errorList.add("sourceText field should be String");
		} else {
			String sourceText = node.get("sourceText").asText();
			
			nerRowSchema.setSourceText(sourceText);

		}
		
		if (!node.has("nerData")) {
			errorList.add("nerData field should be present");
		} else if (!node.get("nerData").isArray()) {
			errorList.add("nerData field should be Array");
		} else {
			int size = node.get("nerData").size();

			log.info(" nerData size :: "+size);
			
			log.info("nerData value :: "+node.get("nerData").toString());
			NerDataArray  nerDataArray = new NerDataArray();
				for(int i=0;i<size;i++) {
					
					
					  NerData nerData = mapper.readValue(node.get("nerData").get(i).toPrettyString()
					 , NerData.class);
					  
				
				      nerDataArray.add(nerData);
				}	
				nerRowSchema.setNerData(nerDataArray);
		
		}
		
		
		if (node.has("collectionMethod")) {
			if (node.get("collectionMethod").has("collectionDescription")) {
				if (!node.get("collectionMethod").get("collectionDescription").isArray()) {
					errorList.add("collectionDescription field should be String Array");
				} else {

					try {
						String collectionDescription = node.get("collectionMethod").get("collectionDescription").get(0)
								.asText();

						NerDatasetCollectionMethod.CollectionDescriptionEnum collectionDescriptionEnum = NerDatasetCollectionMethod.CollectionDescriptionEnum
								.fromValue(collectionDescription);

						NerDatasetCollectionMethod nerDatasetCollectionMethod = new NerDatasetCollectionMethod();
						List<NerDatasetCollectionMethod.CollectionDescriptionEnum> list = new ArrayList<NerDatasetCollectionMethod.CollectionDescriptionEnum>();
						list.add(collectionDescriptionEnum);
						nerDatasetCollectionMethod.setCollectionDescription(list);

						nerRowSchema.setCollectionMethod(nerDatasetCollectionMethod);

						/*
						 * collectionDetails is non mandatory
						 */
						if (node.get("collectionMethod").has("collectionDetails")) {

							switch (collectionDescriptionEnum) {
							case MANUAL_CURATED:
								if (node.get("collectionMethod").has("collectionDetails")) {

									CollectionDetailsManualCurated collectionDetailsManualCurated = mapper.readValue(
											node.get("collectionMethod").get("collectionDetails").toPrettyString(),
											CollectionDetailsManualCurated.class);

									nerDatasetCollectionMethod
											.setCollectionDetails(collectionDetailsManualCurated);
									nerRowSchema.setCollectionMethod(nerDatasetCollectionMethod);

								} else {
									errorList.add("collectionDetails key has to be present");
								}

								break;
							case MACHINE_GENERATED:

								if (node.get("collectionMethod").get("collectionDetails").has("model")) {
									CollectionDetailsMachineGenerated collectionDetailsMachineGenerated = mapper
											.readValue(node.get("collectionMethod").get("collectionDetails")
													.toPrettyString(), CollectionDetailsMachineGenerated.class);

									nerDatasetCollectionMethod
											.setCollectionDetails(collectionDetailsMachineGenerated);
									nerRowSchema.setCollectionMethod(nerDatasetCollectionMethod);

								} else {
									errorList.add("model key has to be present");
								}

								break;

							

							case MACHINE_GENERATED_POST_EDITED:

								CollectionDetailsMachineGeneratedPostEdited collectionDetailsMachineGeneratedPostEdited = mapper
										.readValue(
												node.get("collectionMethod").get("collectionDetails").toPrettyString(),
												CollectionDetailsMachineGeneratedPostEdited.class);

								nerDatasetCollectionMethod
										.setCollectionDetails(collectionDetailsMachineGeneratedPostEdited);
								nerRowSchema.setCollectionMethod(nerDatasetCollectionMethod);
								break;


							

							}
						}
					} catch (Exception e) {
						errorList.add("collectionMethod field value not proper.");

						e.printStackTrace();
					}

				}
			}
		}

		
		if(!errorList.isEmpty())
			throw new IOException(errorList.toString());
		

		return nerRowSchema;
	}

}
