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
import io.swagger.model.CollectionDetailsAutoAlignedFromParallelDocs;
import io.swagger.model.CollectionDetailsMachineGenerated;
import io.swagger.model.CollectionDetailsMachineGeneratedPostEdited;
import io.swagger.model.CollectionDetailsMachineTranslated;
import io.swagger.model.CollectionDetailsMachineTranslatedPostEdited;
import io.swagger.model.CollectionDetailsManualAligned;
import io.swagger.model.CollectionDetailsManualCurated;
import io.swagger.model.CollectionDetailsManualTranslated;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.LanguagePair;
import io.swagger.model.NerDatasetCollectionMethod;
import io.swagger.model.NerDatasetParamsSchema;


import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.model.SupportedLanguages;
import io.swagger.model.SupportedTagsFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NerDatasetParamsSchemaDeserializer extends StdDeserializer<NerDatasetParamsSchema> {

	protected NerDatasetParamsSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public NerDatasetParamsSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public NerDatasetParamsSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** Entry NerDatasetParamsSchemaDeserializer :: deserializer ********");

		ObjectMapper mapper = new ObjectMapper();

		NerDatasetParamsSchema nerParamsSchema = new NerDatasetParamsSchema();
		JsonNode node = p.readValueAsTree();

		ArrayList<String> errorList = new ArrayList<String>();

		JSONObject obj = new JSONObject(node.toPrettyString());

		Set<String> keys = obj.keySet();

		for (String k : keys) {
			try {
				NerDatasetSchemaKeys key = NerDatasetSchemaKeys.valueOf(k);
			} catch (Exception ex) {
				log.info(k + " unknown property ");
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
			if (type != DatasetType.NER_CORPUS) {
				errorList.add("datasetType field value " + DatasetType.NER_CORPUS.toString());
			}
			nerParamsSchema.setDatasetType(type);

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
					if (SupportedLanguages.fromValue(sourceLanguage) != null) {
						lp.setSourceLanguage(SupportedLanguages.fromValue(sourceLanguage));
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

					if (SupportedLanguages.fromValue(targetLanguage) != null) {
						lp.setTargetLanguage(SupportedLanguages.fromValue(targetLanguage));
					} else {
						errorList.add("targetLanguage is not one of defined language pair");
					}

				}
				if (node.get("languages").has("targetLanguageName")) {
					String targetLanguageName = node.get("languages").get("targetLanguageName").asText();
					lp.setSourceLanguageName(targetLanguageName);
				}

				nerParamsSchema.setLanguages(lp);
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
					nerParamsSchema.setCollectionSource(collectionSource);
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

				nerParamsSchema.setDomain(domain);
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
					nerParamsSchema.setLicense(license);
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
				nerParamsSchema.setSubmitter(submitter);
			} catch (Exception e) {
				errorList.add("submitter field value not proper.");
				e.printStackTrace();
			}

		}

		
		if (!node.has("tagsFormat")) {
			errorList.add("tagsFormat field should be present");
		} else if (!node.get("tagsFormat").isObject()) {
			errorList.add("tagsFormat field should be object");
		} else {
			String tagsFormat = node.get("tagsFormat").asText();
		
			SupportedTagsFormat tagsFormatEnum = SupportedTagsFormat.fromValue(tagsFormat);
			   
		
			
		
			if ( tagsFormatEnum == null) {
				errorList.add("tagsFormat field value not proper.");
			}else {
			
				nerParamsSchema.setTagsFormat(tagsFormatEnum);
			}
			

		}
		
		
		
		
		// optional params

		if (node.has("version")) {
			if (!node.get("version").isTextual()) {
				errorList.add("version field should be String");
			} else {
				String version = node.get("version").asText();
				nerParamsSchema.setVersion(version);
			}

		}
		if (node.has("licenseUrl")) {
			if (!node.get("licenseUrl").isTextual()) {
				errorList.add("licenseUrl field should be String");
			} else {
				String licenseUrl = node.get("licenseUrl").asText();
				nerParamsSchema.setLicenseUrl(licenseUrl);
			}

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

						nerParamsSchema.setCollectionMethod(nerDatasetCollectionMethod);

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
									nerParamsSchema.setCollectionMethod(nerDatasetCollectionMethod);

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
									nerParamsSchema.setCollectionMethod(nerDatasetCollectionMethod);

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
								nerParamsSchema.setCollectionMethod(nerDatasetCollectionMethod);
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

		return nerParamsSchema;
	}

}
