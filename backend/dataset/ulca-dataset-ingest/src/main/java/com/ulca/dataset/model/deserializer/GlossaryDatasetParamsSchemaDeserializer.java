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
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.GlossaryDatasetCollectionMethod;
import io.swagger.model.GlossaryDatasetParamsSchema;
import io.swagger.model.LanguagePair;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlossaryDatasetParamsSchemaDeserializer extends StdDeserializer<GlossaryDatasetParamsSchema> {

	protected GlossaryDatasetParamsSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public GlossaryDatasetParamsSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public GlossaryDatasetParamsSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** Entry GlossaryDatasetParamsSchemaDeserializer :: deserializer ********");

		ObjectMapper mapper = new ObjectMapper();

		GlossaryDatasetParamsSchema glossaryParamsSchema = new GlossaryDatasetParamsSchema();
		JsonNode node = p.readValueAsTree();

		ArrayList<String> errorList = new ArrayList<String>();

		JSONObject obj = new JSONObject(node.toPrettyString());

		Set<String> keys = obj.keySet();

		for (String k : keys) {
			try {
				GlossaryDatasetSchemaKeys key = GlossaryDatasetSchemaKeys.valueOf(k);
			} catch (Exception ex) {

				log.info("GlossaryDatasetSchemaKeys " + k + " not in defined keys");
				errorList.add(k + " unknown property ");
			}

		}

		if (!node.has("datasetType")) {
			errorList.add("datasetType field should be present");
		} else if (!node.get("datasetType").isTextual()) {
			errorList.add("datasetType field should be String");
		} else {
			String datasetType = node.get("datasetType").asText();
			DatasetType type = DatasetType.fromValue(datasetType);
			if (type != DatasetType.GLOSSARY_CORPUS) {
				errorList.add("datasetType field value " + DatasetType.GLOSSARY_CORPUS.toString());
			}
			glossaryParamsSchema.setDatasetType(type);

		}
		if (!node.has("languages")) {
			errorList.add("languages field should be present");
		} else if (!node.get("languages").isObject()) {
			errorList.add("languages field should be JSON");
		} else {
			try {
				LanguagePair lp = new LanguagePair();

				if (node.get("languages").has("sourceLanguage")) {
					String sourceLanguage = node.get("languages").get("sourceLanguage").asText();
					if (LanguagePair.SourceLanguageEnum.fromValue(sourceLanguage) != null) {
						lp.setSourceLanguage(LanguagePair.SourceLanguageEnum.fromValue(sourceLanguage));
					} else {
						errorList.add("sourceLanguage is not one of defined language pair");
					}

				} else {
					errorList.add("sourceLanguage should be present");
				}
				if (node.get("languages").has("sourceLanguageName")) {
					String sourceLanguageName = node.get("languages").get("sourceLanguageName").asText();
					lp.setSourceLanguageName(sourceLanguageName);
				}
				if (node.get("languages").has("targetLanguage")) {
					String targetLanguage = node.get("languages").get("targetLanguage").asText();

					if (LanguagePair.TargetLanguageEnum.fromValue(targetLanguage) != null) {
						lp.setTargetLanguage(LanguagePair.TargetLanguageEnum.fromValue(targetLanguage));
					} else {
						errorList.add("targetLanguage is not one of defined language pair");
					}

				}
				if (node.get("languages").has("targetLanguageName")) {
					String targetLanguageName = node.get("languages").get("targetLanguageName").asText();
					lp.setSourceLanguageName(targetLanguageName);
				}

				glossaryParamsSchema.setLanguages(lp);
			} catch (Exception e) {
				errorList.add("languages field value not proper.");
				e.printStackTrace();
			}

		}

		if (node.get("collectionSource").isEmpty()) {
			errorList.add("collectionSource field should be present");
		} else if (!node.get("collectionSource").isArray()) {
			errorList.add("collectionSource field should be String array");
		} else {

			try {
				Source collectionSource = mapper.readValue(node.get("collectionSource").toPrettyString(), Source.class);
				if (collectionSource.size() > 10 || collectionSource.size() < 0) {
					errorList.add("collectionSource array size should be > 0 and <= 10");
				} else {
					glossaryParamsSchema.setCollectionSource(collectionSource);
				}

			} catch (Exception e) {
				errorList.add("collectionSource field value not proper.");
				e.printStackTrace();
			}
		}

		if (node.get("domain").isEmpty()) {
			errorList.add("domain field should be present");
		} else if (!node.get("domain").isArray()) {
			errorList.add("domain field should be String array");
		} else {

			try {
				Domain domain = new Domain();
				int size = node.get("domain").size();

				for (int i = 0; i < size; i++) {

					String enumValue = node.get("domain").get(i).asText();

					DomainEnum dEnum = DomainEnum.fromValue(enumValue);
					if (dEnum == null) {
						errorList.add("domain value not part of defined domains");
					} else {
						domain.add(enumValue);
					}
				}

				glossaryParamsSchema.setDomain(domain);
			} catch (Exception e) {
				errorList.add("domain field value not proper.");
				e.printStackTrace();
			}

		}

		if (!node.has("license")) {
			errorList.add("license field should be present");
		} else if (!node.get("license").isTextual()) {
			errorList.add("license field should be String");
		} else {
			try {

				String licenseText = node.get("license").asText();

				io.swagger.model.License license = io.swagger.model.License.fromValue(licenseText);
				if (license != null) {
					glossaryParamsSchema.setLicense(license);
					if (license == io.swagger.model.License.CUSTOM_LICENSE) {
						String licenseUrl = node.get("licenseUrl").asText();
						if (licenseUrl.isBlank()) {
							errorList.add("custom licenseUrl field value should be present");
						}
					}
				} else {
					errorList.add("license field value should be present in license list");
				}

			} catch (Exception e) {
				errorList.add("license field value not proper.");
				e.printStackTrace();
			}

		}

		if (node.get("submitter").isEmpty()) {
			errorList.add("submitter field should be present");
		} else if (!node.get("submitter").isObject()) {
			errorList.add("submitter field should be JSON");
		} else {
			try {
				Submitter submitter = mapper.readValue(node.get("submitter").toPrettyString(), Submitter.class);
				glossaryParamsSchema.setSubmitter(submitter);
			} catch (Exception e) {
				errorList.add("submitter field value not proper.");
				e.printStackTrace();
			}

		}

		/*
		 * optional keys : version, licenseUrl, level, collectionMethod,
		 */

		if (node.has("version")) {
			if (!node.get("version").isTextual()) {
				errorList.add("version field should be String");
			} else {
				String version = node.get("version").asText();
				glossaryParamsSchema.setVersion(version);
			}
		}

		if (node.has("licenseUrl")) {
			if (!node.get("licenseUrl").isTextual()) {
				errorList.add("licenseUrl field should be String");
			} else {
				String licenseUrl = node.get("licenseUrl").asText();
				glossaryParamsSchema.setLicenseUrl(licenseUrl);
			}
		}

		if (node.has("level")) {
			if (!node.get("level").isTextual()) {
				errorList.add("level field should be String");
			} else {
				String level = node.get("level").asText();

				GlossaryDatasetParamsSchema.LevelEnum levelEnum = GlossaryDatasetParamsSchema.LevelEnum
						.fromValue(level);

				if (levelEnum != null) {
					glossaryParamsSchema.setLevel(levelEnum);

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

						glossaryParamsSchema.setCollectionMethod(glossaryDatasetCollectionMethod);

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
										glossaryParamsSchema.setCollectionMethod(glossaryDatasetCollectionMethod);
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
									glossaryParamsSchema.setCollectionMethod(glossaryDatasetCollectionMethod);

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
									glossaryParamsSchema.setCollectionMethod(glossaryDatasetCollectionMethod);

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
								glossaryParamsSchema.setCollectionMethod(glossaryDatasetCollectionMethod);
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

		return glossaryParamsSchema;
	}

}
