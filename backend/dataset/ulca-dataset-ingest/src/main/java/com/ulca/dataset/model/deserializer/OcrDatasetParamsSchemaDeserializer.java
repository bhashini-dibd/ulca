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

import io.swagger.model.CollectionDetailsOcr;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.ImageDPI;
import io.swagger.model.ImageFormat;
import io.swagger.model.ImageTextType;
import io.swagger.model.LanguagePair;
import io.swagger.model.OcrCollectionMethod;
import io.swagger.model.OcrDatasetParamsSchema;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OcrDatasetParamsSchemaDeserializer extends StdDeserializer<OcrDatasetParamsSchema> {

	protected OcrDatasetParamsSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public OcrDatasetParamsSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public OcrDatasetParamsSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** Entry OcrDatasetParamsSchema :: deserialize ********");
		ObjectMapper mapper = new ObjectMapper();
		OcrDatasetParamsSchema ocrParamsSchema = new OcrDatasetParamsSchema();
		JsonNode node = p.readValueAsTree();


		ArrayList<String> errorList = new ArrayList<String>();
		
		JSONObject obj = new JSONObject(node.toPrettyString());
		
		Set<String> keys = obj.keySet();
		
		for(String k : keys) {
			try {
				OcrDatasetParamsSchemaKeys key = OcrDatasetParamsSchemaKeys.valueOf(k) ;
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
			if (type != DatasetType.OCR_CORPUS) {
				errorList.add("datasetType field value " + DatasetType.OCR_CORPUS.toString());
			}
			ocrParamsSchema.setDatasetType(type);

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
					String targetLanguage = node.get("languages").get("targetLanguage").asText();
					
					if(LanguagePair.TargetLanguageEnum.fromValue(targetLanguage) != null) {
						lp.setTargetLanguage(LanguagePair.TargetLanguageEnum.fromValue(targetLanguage));
					}
					
				}
				if(node.get("languages").has("targetLanguageName")) {
					String targetLanguageName = node.get("languages").get("targetLanguageName").asText();
					lp.setSourceLanguageName(targetLanguageName);
				}
				
				
				ocrParamsSchema.setLanguages(lp);
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
					ocrParamsSchema.setCollectionSource(collectionSource);
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
				
				ocrParamsSchema.setDomain(domain);
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
					ocrParamsSchema.setLicense(license);
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
				ocrParamsSchema.setSubmitter(submitter);
			} catch (Exception e) {
				errorList.add("submitter field value not proper.");
				e.printStackTrace();
			}

		}

				      
		//optional params
		
		      if (node.has("format")) {
		    	  if(!node.get("format").isTextual()) {
		    		  errorList.add("format field should be String");
		    	  }else {
		    		  String format = node.get("format").asText();
		    		   
		    		  ImageFormat imageFormat  = ImageFormat.fromValue(format);
						if(imageFormat != null) {
							ocrParamsSchema.setFormat(imageFormat);
						}else {
							errorList.add("format not among one of specified");
						}
						
		    	  }
					
				} 
		      
				if (node.has("dpi")) {
			    	  if(!node.get("dpi").isTextual()) {
			    		  errorList.add("dpi field should be String");
			    	  }else {
			    		  String format = node.get("dpi").asText();
			    		   
			    		  ImageDPI imageDPI  = ImageDPI.fromValue(format);
							if(imageDPI != null) {
								ocrParamsSchema.setDpi(imageDPI);
							}else {
								errorList.add("format not among one of specified");
							}
							
			    	  }
						
					} 
				
				if (node.has("imageTextType")) {
			    	  if(!node.get("imageTextType").isTextual()) {
			    		  errorList.add("imageTextType field should be String");
			    	  }else {
			    		  String imageTextTypeValue = node.get("imageTextType").asText();
			    		   
			    		  ImageTextType imageTextType  = ImageTextType.fromValue(imageTextTypeValue);
							if(imageTextType != null) {
								ocrParamsSchema.setImageTextType(imageTextType);
							}else {
								errorList.add("imageTextType not among one of specified");
							}
							
			    	  }
						
					} 
		      
		     
	if(node.has("collectionMethod")) {
		if (node.get("collectionMethod").has("collectionDescription")) {
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

						ocrParamsSchema.setCollectionMethod(ocrCollectionMethod);


						/*
						 * collectionDetails is non mandatory
						 */
						if (node.get("collectionMethod").has("collectionDetails")) { 
						
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
								
								ocrParamsSchema.setCollectionMethod(ocrCollectionMethod);
								
							}else {
								errorList.add("ocrToolEnum should be one of specified values");
							}
						}
						}

					} catch (Exception e) {
						log.info("collection method not proper");
						errorList.add("collectionMethod field value not proper.");
						log.info("tracing the error");
						
						e.printStackTrace();
					}
				}

			}
		}else {
			errorList.add("if collectionMethod then collectionDescription should be present inside collectionMethod");
		}
	}

	if(!errorList.isEmpty())
		throw new IOException(errorList.toString());

		log.info("******** Exiting deserializer ********");
		return ocrParamsSchema;
	}

}
