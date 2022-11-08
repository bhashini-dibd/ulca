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

import io.swagger.model.CollectionDetailsGlossaryAutoAligned;
import io.swagger.model.CollectionDetailsMachineGenerated;
import io.swagger.model.CollectionDetailsMachineGeneratedPostEdited;
import io.swagger.model.CollectionDetailsManualCurated;
import io.swagger.model.GlossaryDatasetCollectionMethod;
import io.swagger.model.GlossaryDatasetRowSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlossaryDatasetRowSchemaDeserializer extends StdDeserializer<GlossaryDatasetRowSchema> {

	protected GlossaryDatasetRowSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public GlossaryDatasetRowSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public GlossaryDatasetRowSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		ArrayList<String> keysList = new ArrayList<String>();
		keysList.add("sourceText");
		keysList.add("targetText");
		keysList.add("level");
		keysList.add("collectionMethod");

		ObjectMapper mapper = new ObjectMapper();

		GlossaryDatasetRowSchema glossaryRowSchema = new GlossaryDatasetRowSchema();
		JsonNode node = p.readValueAsTree();

		ArrayList<String> errorList = new ArrayList<String>();

		JSONObject obj = new JSONObject(node.toPrettyString());

		Set<String> keys = obj.keySet();
		if (!keysList.containsAll(keys)) {
			errorList.add("json key should only contain sourceText, targetText,collectionMethod ");
		}

		if (!node.has("sourceText")) {
			errorList.add("sourceText field should be present");
		} else if (!node.get("sourceText").isTextual()) {
			errorList.add("sourceText field should be String");
		} else {
			String sourceText = node.get("sourceText").asText();

			glossaryRowSchema.setSourceText(sourceText);

		}

		if (!node.has("targetText")) {
			errorList.add("targetText field should be present");
		} else if (!node.get("targetText").isTextual()) {
			errorList.add("targetText field should be String");
		} else {
			String sourceText = node.get("targetText").asText();

			glossaryRowSchema.setSourceText(sourceText);

		}

		if (node.has("level")) {
			if (!node.get("level").isTextual()) {
				errorList.add("level field should be String");
			} else {
				String level = node.get("level").asText();

				GlossaryDatasetRowSchema.LevelEnum levelEnum = GlossaryDatasetRowSchema.LevelEnum.fromValue(level);

				if (levelEnum != null) {
					glossaryRowSchema.setLevel(levelEnum);

				} else {
					errorList.add("level not among one of specified values");
				}
			}
		}

		/*
		 * AUTO_ALIGNED("auto-aligned"),
		 * 
		 * MANUAL_CURATED("manual-curated"),
		 * 
		 * MACHINE_GENERATED("machine-generated"),
		 * 
		 * MACHINE_GENERATED_POST_EDITED("machine-generated-post-edited");
		 */

		if (node.has("collectionMethod")) {
			if (node.get("collectionMethod").has("collectionDescription")) {
				if (!node.get("collectionMethod").get("collectionDescription").isArray()) {
					errorList.add("collectionDescription field should be String Array");
				} else {

					try {
						String collectionDescription = node.get("collectionMethod").get("collectionDescription").get(0)
								.asText();

						GlossaryDatasetCollectionMethod.CollectionDescriptionEnum collectionDescriptionEnum = GlossaryDatasetCollectionMethod.CollectionDescriptionEnum
								.fromValue(collectionDescription);

						GlossaryDatasetCollectionMethod glossaryDatasetCollectionMethod = new GlossaryDatasetCollectionMethod();

						List<GlossaryDatasetCollectionMethod.CollectionDescriptionEnum> list = new ArrayList<GlossaryDatasetCollectionMethod.CollectionDescriptionEnum>();

						list.add(collectionDescriptionEnum);
						glossaryDatasetCollectionMethod.setCollectionDescription(list);

                         glossaryRowSchema.setCollectionMethod(glossaryDatasetCollectionMethod);
						/*
						 * collectionDetails is non mandatory
						 */
						if (node.get("collectionMethod").has("collectionDetails")) {

							switch (collectionDescriptionEnum) {
							case AUTO_ALIGNED:
								if (node.get("collectionMethod").get("collectionDetails").has("alignmentTool")) {
									String alignmentTool = node.get("collectionMethod").get("collectionDetails")
											.get("alignmentTool").asText();
									if (CollectionDetailsGlossaryAutoAligned.AlignmentToolEnum
											.fromValue(alignmentTool) == null) {
										errorList.add("alignmentTool is not one of defined alignmentTool");
									} else {
										CollectionDetailsGlossaryAutoAligned collectionDetailsGlossaryAutoAligned = mapper
												.readValue(
														node.get("collectionMethod").get("collectionDetails")
																.toPrettyString(),
														CollectionDetailsGlossaryAutoAligned.class);

										glossaryDatasetCollectionMethod
												.setCollectionDetails(collectionDetailsGlossaryAutoAligned);
										glossaryRowSchema.setCollectionMethod(glossaryDatasetCollectionMethod);
									}
								} else {
									errorList.add("alignmentTool key has to be present");
								}

								break;
							case MANUAL_CURATED:
								if (node.get("collectionMethod").has("collectionDetails")) {

									CollectionDetailsManualCurated collectionDetailsManualCurated = mapper.readValue(
											node.get("collectionMethod").get("collectionDetails").toPrettyString(),
											CollectionDetailsManualCurated.class);

									glossaryDatasetCollectionMethod
											.setCollectionDetails(collectionDetailsManualCurated);
									glossaryRowSchema.setCollectionMethod(glossaryDatasetCollectionMethod);

								} else {
									errorList.add("collectionDetails key has to be present");
								}

								break;

							case MACHINE_GENERATED:

								if (node.get("collectionMethod").get("collectionDetails").has("model")) {
									CollectionDetailsMachineGenerated collectionDetailsMachineGenerated = mapper
											.readValue(node.get("collectionMethod").get("collectionDetails")
													.toPrettyString(), CollectionDetailsMachineGenerated.class);

									glossaryDatasetCollectionMethod
											.setCollectionDetails(collectionDetailsMachineGenerated);
									glossaryRowSchema.setCollectionMethod(glossaryDatasetCollectionMethod);

								} else {
									errorList.add("model key has to be present");
								}

								break;

							case MACHINE_GENERATED_POST_EDITED:

								CollectionDetailsMachineGeneratedPostEdited collectionDetailsMachineGeneratedPostEdited = mapper
										.readValue(
												node.get("collectionMethod").get("collectionDetails").toPrettyString(),
												CollectionDetailsMachineGeneratedPostEdited.class);

								glossaryDatasetCollectionMethod
										.setCollectionDetails(collectionDetailsMachineGeneratedPostEdited);
								glossaryRowSchema.setCollectionMethod(glossaryDatasetCollectionMethod);
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

		if (!errorList.isEmpty())
			throw new IOException(errorList.toString());

		return glossaryRowSchema;
	}

}
