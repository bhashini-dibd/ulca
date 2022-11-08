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
import io.swagger.model.CollectionDetailsMachineTranslated;
import io.swagger.model.CollectionDetailsMachineTranslatedPostEdited;
import io.swagger.model.CollectionDetailsManualAligned;
import io.swagger.model.CollectionDetailsManualTranslated;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.LanguagePair;
import io.swagger.model.ParallelDatasetCollectionMethod;
import io.swagger.model.ParallelDatasetParamsSchema;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParallelDatasetParamsSchemaDeserializer extends StdDeserializer<ParallelDatasetParamsSchema> {

	protected ParallelDatasetParamsSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public ParallelDatasetParamsSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ParallelDatasetParamsSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** Entry ParallelDatasetParamsSchemaDeserializer :: deserializer ********");
		
		
		
		

		ObjectMapper mapper = new ObjectMapper();

		ParallelDatasetParamsSchema parallelParamsSchema = new ParallelDatasetParamsSchema();
		JsonNode node = p.readValueAsTree();
		
		ArrayList<String> errorList = new ArrayList<String>();
		
		JSONObject obj = new JSONObject(node.toPrettyString());
		
		Set<String> keys = obj.keySet();
		
		for(String k : keys) {
			try {
				ParallelDatasetSchemaKeys key = ParallelDatasetSchemaKeys.valueOf(k) ;
			}catch (Exception ex) {
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
			if (type != DatasetType.PARALLEL_CORPUS) {
				errorList.add("datasetType field value " + DatasetType.PARALLEL_CORPUS.toString());
			}
			parallelParamsSchema.setDatasetType(type);

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
				
				
				parallelParamsSchema.setLanguages(lp);
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
					parallelParamsSchema.setCollectionSource(collectionSource);
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
				
				parallelParamsSchema.setDomain(domain);
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
					parallelParamsSchema.setLicense(license);
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
				parallelParamsSchema.setSubmitter(submitter);
			} catch (Exception e) {
				errorList.add("submitter field value not proper.");
				e.printStackTrace();
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
					ParallelDatasetCollectionMethod.CollectionDescriptionEnum collectionDescriptionEnum = ParallelDatasetCollectionMethod.CollectionDescriptionEnum
							.fromValue(collectionDescription);

					ParallelDatasetCollectionMethod parallelDatasetCollectionMethod = new ParallelDatasetCollectionMethod();
					List<ParallelDatasetCollectionMethod.CollectionDescriptionEnum> list = new ArrayList<ParallelDatasetCollectionMethod.CollectionDescriptionEnum>();
					list.add(collectionDescriptionEnum);
					parallelDatasetCollectionMethod.setCollectionDescription(list);
                    parallelParamsSchema.setCollectionMethod(parallelDatasetCollectionMethod);
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
								parallelDatasetCollectionMethod.setCollectionDetails(collectionDetailsAutoAligned);
								parallelParamsSchema.setCollectionMethod(parallelDatasetCollectionMethod);
							}
						}else {
							errorList.add("alignmentTool key has to be present");	
						}
						
						break;
					case AUTO_ALIGNED_FROM_PARALLEL_DOCS:
						if(node.get("collectionMethod").get("collectionDetails").has("alignmentTool")) {
							String alignmentTool = node.get("collectionMethod").get("collectionDetails").get("alignmentTool").asText();
							if(CollectionDetailsAutoAlignedFromParallelDocs.AlignmentToolEnum.fromValue(alignmentTool) == null) {
								errorList.add("alignmentTool is not one of defined alignmentTool");	
							}else {
								CollectionDetailsAutoAlignedFromParallelDocs collectionDetailsAutoAlignedFromParallelDocs = mapper.readValue(
										node.get("collectionMethod").get("collectionDetails").toPrettyString(),CollectionDetailsAutoAlignedFromParallelDocs.class);
							
								parallelDatasetCollectionMethod.setCollectionDetails(collectionDetailsAutoAlignedFromParallelDocs);
								parallelParamsSchema.setCollectionMethod(parallelDatasetCollectionMethod);
							}
						}else {
							errorList.add("alignmentTool key has to be present");
						}
						
						
						break;
						
					case MANUAL_ALIGNED:
						
						CollectionDetailsManualAligned collectionDetailsManualAligned = mapper.readValue(
								node.get("collectionMethod").get("collectionDetails").toPrettyString(),
								CollectionDetailsManualAligned.class);
						parallelDatasetCollectionMethod.setCollectionDetails(collectionDetailsManualAligned);
						parallelParamsSchema.setCollectionMethod(parallelDatasetCollectionMethod);
						
						break;

					case MACHINE_TRANSLATED:

						CollectionDetailsMachineTranslated collectionDetailsMachineTranslated = mapper.readValue(
								node.get("collectionMethod").get("collectionDetails").toPrettyString(),
								CollectionDetailsMachineTranslated.class);
						parallelDatasetCollectionMethod.setCollectionDetails(collectionDetailsMachineTranslated);
						parallelParamsSchema.setCollectionMethod(parallelDatasetCollectionMethod);
						break;

					case MACHINE_TRANSLATED_POST_EDITED:

						CollectionDetailsMachineTranslatedPostEdited collectionDetailsMachineTranslatedPostEdited = mapper
								.readValue(node.get("collectionMethod").get("collectionDetails").toPrettyString(),
										CollectionDetailsMachineTranslatedPostEdited.class);
						parallelDatasetCollectionMethod
								.setCollectionDetails(collectionDetailsMachineTranslatedPostEdited);
						parallelParamsSchema.setCollectionMethod(parallelDatasetCollectionMethod);
						break;

					case MANUAL_TRANSLATED:

						CollectionDetailsManualTranslated collectionDetailsManualTranslated = mapper.readValue(
								node.get("collectionMethod").get("collectionDetails").toPrettyString(),
								CollectionDetailsManualTranslated.class);
						parallelDatasetCollectionMethod.setCollectionDetails(collectionDetailsManualTranslated);
						parallelParamsSchema.setCollectionMethod(parallelDatasetCollectionMethod);
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
		

		return parallelParamsSchema;
	}

}
