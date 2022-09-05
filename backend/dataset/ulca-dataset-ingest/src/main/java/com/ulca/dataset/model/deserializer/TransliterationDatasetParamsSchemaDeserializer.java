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
import io.swagger.model.CollectionDetailsMachineTransliterated;
import io.swagger.model.CollectionDetailsMachineTransliteratedPostEdited;
import io.swagger.model.CollectionDetailsManualTransliterated;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.LanguagePair;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.model.TransliterationDatasetCollectionMethod;
import io.swagger.model.TransliterationDatasetParamsSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransliterationDatasetParamsSchemaDeserializer extends StdDeserializer<TransliterationDatasetParamsSchema> {

	protected TransliterationDatasetParamsSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public TransliterationDatasetParamsSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public TransliterationDatasetParamsSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** Entry TransliterationDatasetParamsSchemaDeserializer :: deserializer ********");
		
		
		
		

		ObjectMapper mapper = new ObjectMapper();

		TransliterationDatasetParamsSchema transliterationParamsSchema = new TransliterationDatasetParamsSchema();
		JsonNode node = p.readValueAsTree();
		
		ArrayList<String> errorList = new ArrayList<String>();
		
		JSONObject obj = new JSONObject(node.toPrettyString());
		
		Set<String> keys = obj.keySet();
		
		for(String k : keys) {
			try {
				TransliterationDatasetSchemaKeys key = TransliterationDatasetSchemaKeys.valueOf(k) ;
			}catch (Exception ex) {
				log.info(k + " unknown property ");
				errorList.add(k + " unknown property ");
			}
			
		}
		
		//required
		
		if (!node.has("datasetType")) {
			errorList.add("datasetType field should be present");
		} else if (!node.get("datasetType").isTextual()) {
			errorList.add("datasetType field should be String");
		} else {
			String datasetType = node.get("datasetType").asText();
			DatasetType type = DatasetType.fromValue(datasetType);
			if (type != DatasetType.TRANSLITERATION_CORPUS) {
				errorList.add("datasetType field value " + DatasetType.TRANSLITERATION_CORPUS.toString());
			}
			transliterationParamsSchema.setDatasetType(type);

		}
		if (!node.has("languages")) {
			errorList.add("languages field should be present");
		} else if (!node.get("languages").isObject()) {
			errorList.add("languages field should be JSON");
		} else {
			try {
				LanguagePair lp = new LanguagePair();
				
				if(node.get("languages").has("sourceLanguage")) {
					String sourceLanguage =  	node.get("languages").get("sourceLanguage").asText();
					if(LanguagePair.SourceLanguageEnum.fromValue(sourceLanguage) != null) {
						lp.setSourceLanguage(LanguagePair.SourceLanguageEnum.fromValue(sourceLanguage));
					}else {
						errorList.add("sourceLanguage is not one of defined language pair");
					}
					
				}else {
					errorList.add("sourceLanguage should be present");
				}
				if(node.get("languages").has("sourceLanguageName")) {
					String sourceLanguageName =  	node.get("languages").get("sourceLanguageName").asText();
					lp.setSourceLanguageName(sourceLanguageName);
				}
				if(node.get("languages").has("targetLanguage")) {
					String targetLanguage =  	node.get("languages").get("targetLanguage").asText();
					
					if(LanguagePair.TargetLanguageEnum.fromValue(targetLanguage) != null) {
						lp.setTargetLanguage(LanguagePair.TargetLanguageEnum.fromValue(targetLanguage));
					}else {
						errorList.add("targetLanguage is not one of defined language pair");
					}
					
				}
				if(node.get("languages").has("targetLanguageName")) {
					String targetLanguageName =  	node.get("languages").get("targetLanguageName").asText();
					lp.setSourceLanguageName(targetLanguageName);
				}
				
				
				transliterationParamsSchema.setLanguages(lp);
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
				if(collectionSource.size() > 10 || collectionSource.size() < 0) {
					errorList.add("collectionSource array size should be > 0 and <= 10");
				}else {
					transliterationParamsSchema.setCollectionSource(collectionSource);
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
				
				for(int i=0; i < size; i++) {
					
					String enumValue = node.get("domain").get(i).asText();
					
					DomainEnum dEnum = DomainEnum.fromValue(enumValue);
					if(dEnum == null) {
						errorList.add("domain value not part of defined domains");
					}else {
						domain.add(enumValue);
					}
				}
				
				transliterationParamsSchema.setDomain(domain);
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
				if(license != null) {
					transliterationParamsSchema.setLicense(license);
					if(license == io.swagger.model.License.CUSTOM_LICENSE) {
						String licenseUrl = node.get("licenseUrl").asText();
						if(licenseUrl.isBlank()) {
							errorList.add("custom licenseUrl field value should be present");
						}
					}
				}else {
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
				transliterationParamsSchema.setSubmitter(submitter);
			} catch (Exception e) {
				errorList.add("submitter field value not proper.");
				e.printStackTrace();
			}

		}

		
		//optional
		//collectionMethod,
		//version,
		//licenseUrl
		
		if (node.has("version")) {
			if (!node.get("version").isTextual()) {
				errorList.add("version field should be String");
			} else {
				String version = node.get("version").asText();
				transliterationParamsSchema.setVersion(version);
			}
		}
		
		if (node.has("licenseUrl")) {
			if (!node.get("licenseUrl").isTextual()) {
				errorList.add("licenseUrl field should be String");
			} else {
				String licenseUrl = node.get("licenseUrl").asText();
				transliterationParamsSchema.setLicenseUrl(licenseUrl);
			}
		}
		
		if(node.has("collectionMethod")) {
		if (node.get("collectionMethod").has("collectionDescription")) {
			if (!node.get("collectionMethod").get("collectionDescription").isArray()) {
				errorList.add("collectionDescription field should be String Array");
			} else {

				try {
					String collectionDescription = node.get("collectionMethod").get("collectionDescription").get(0)
							.asText();
					TransliterationDatasetCollectionMethod.CollectionDescriptionEnum collectionDescriptionEnum = TransliterationDatasetCollectionMethod.CollectionDescriptionEnum
							.fromValue(collectionDescription);

					TransliterationDatasetCollectionMethod transliterationDatasetCollectionMethod = new TransliterationDatasetCollectionMethod();
					List<TransliterationDatasetCollectionMethod.CollectionDescriptionEnum> list = new ArrayList<TransliterationDatasetCollectionMethod.CollectionDescriptionEnum>();
					list.add(collectionDescriptionEnum);
					transliterationDatasetCollectionMethod.setCollectionDescription(list);

					/*
					 * collectionDetails is non mandatory
					 */
					if (node.get("collectionMethod").has("collectionDetails")) { 
						
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
								transliterationDatasetCollectionMethod.setCollectionDetails(collectionDetailsAutoAligned);
								transliterationParamsSchema.setCollectionMethod(transliterationDatasetCollectionMethod);
							}
						}else {
							errorList.add("alignmentTool key has to be present");	
						}
						
						break;
						
					case MACHINE_TRANSLITERATED:

						CollectionDetailsMachineTransliterated collectionDetailsMachineTransliterated = mapper.readValue(
								node.get("collectionMethod").get("collectionDetails").toPrettyString(),
								CollectionDetailsMachineTransliterated.class);
						
						transliterationDatasetCollectionMethod.setCollectionDetails(collectionDetailsMachineTransliterated);
						transliterationParamsSchema.setCollectionMethod(transliterationDatasetCollectionMethod);
						break;	
						
					case MACHINE_TRANSLITERATED_POST_EDITED:

						CollectionDetailsMachineTransliteratedPostEdited collectionDetailsMachineTransliteratedPostEdited = mapper
								.readValue(node.get("collectionMethod").get("collectionDetails").toPrettyString(),
										CollectionDetailsMachineTransliteratedPostEdited.class);
						transliterationDatasetCollectionMethod
								.setCollectionDetails(collectionDetailsMachineTransliteratedPostEdited);
						transliterationParamsSchema.setCollectionMethod(transliterationDatasetCollectionMethod);
						break;
						
					case MANUAL_TRANSLITERATED:
						
						CollectionDetailsManualTransliterated collectionDetailsManualTransliterated = mapper.readValue(
								node.get("collectionMethod").get("collectionDetails").toPrettyString(),
								CollectionDetailsManualTransliterated.class);
						transliterationDatasetCollectionMethod.setCollectionDetails(collectionDetailsManualTransliterated);
						transliterationParamsSchema.setCollectionMethod(transliterationDatasetCollectionMethod);
						
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
		

		return transliterationParamsSchema;
	}

}
