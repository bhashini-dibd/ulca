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
import io.swagger.model.DocumentLayoutParamsSchema;
import io.swagger.model.ImageDPI;
import io.swagger.model.ImageFormat;
import io.swagger.model.OcrCollectionMethod;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentLayoutDatasetParamsSchemaDeserializer extends StdDeserializer<DocumentLayoutParamsSchema> {

	protected DocumentLayoutDatasetParamsSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public DocumentLayoutDatasetParamsSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DocumentLayoutParamsSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** inside deserializer ********");
		ObjectMapper mapper = new ObjectMapper();
		DocumentLayoutParamsSchema docLayoutParamsSchema = new DocumentLayoutParamsSchema();
		JsonNode node = p.readValueAsTree();

		ArrayList<String> errorList = new ArrayList<String>();

		JSONObject obj = new JSONObject(node.toPrettyString());
		Set<String> keys = obj.keySet();
		for (String k : keys) {
			try {
				DocumentLayoutDatasetParamsSchemaKeys key = DocumentLayoutDatasetParamsSchemaKeys.valueOf(k);
			} catch (Exception ex) {
				log.info("DocumentLayoutDatasetParamsSchemaKeys " + k + " not in defined keys");
				errorList.add(k + " unknown property ");
			}
		}

		// required

		if (!node.has("datasetType")) {
			errorList.add("datasetType field should be present");
		} else if (!node.get("datasetType").isTextual()) {
			errorList.add("datasetType field should be String");
		} else {
			String datasetType = node.get("datasetType").asText();
			DatasetType type = DatasetType.fromValue(datasetType);
			if (type != DatasetType.DOCUMENT_LAYOUT_CORPUS) {
				errorList.add("datasetType field value " + DatasetType.DOCUMENT_LAYOUT_CORPUS.toString());
			}
			docLayoutParamsSchema.setDatasetType(type);

		}

		if (!node.has("collectionSource")) {
			errorList.add("collectionSource field should be present");
		} else if (!node.get("collectionSource").isArray()) {
			errorList.add("collectionSource field should be String array");
		} else {

			try {
				Source collectionSource = mapper.readValue(node.get("collectionSource").toPrettyString(), Source.class);
				if (collectionSource.size() > 10 || collectionSource.size() < 0) {
					errorList.add("collectionSource array size should be > 0 and <= 10");
				} else {
					docLayoutParamsSchema.setCollectionSource(collectionSource);
				}

			} catch (Exception e) {
				errorList.add("collectionSource field value not proper.");
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
					docLayoutParamsSchema.setLicense(license);
					if(license == io.swagger.model.License.CUSTOM_LICENSE) {
						String licenseUrl = node.get("licenseUrl").asText();
						if(licenseUrl.isBlank()) {
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
				docLayoutParamsSchema.setSubmitter(submitter);
			} catch (Exception e) {
				errorList.add("submitter field value not proper.");
				e.printStackTrace();
			}

		}

		if (!node.has("format")) {
			errorList.add("format should be present");
		} else if (!node.get("format").isTextual()) {
			errorList.add("format field should be String");
		} else {
			String format = node.get("format").asText();

			ImageFormat imageFormat = ImageFormat.fromValue(format);
			if (imageFormat != null) {
				docLayoutParamsSchema.setFormat(imageFormat);
			} else {
				errorList.add("format not among one of specified");
			}

		}

		if (!node.has("dpi")) {
			errorList.add("format should be present");
		} else if (!node.get("dpi").isTextual()) {
			errorList.add("dpi field should be String");
		} else {
			String format = node.get("dpi").asText();

			ImageDPI imageDPI = ImageDPI.fromValue(format);
			if (imageDPI != null) {
				docLayoutParamsSchema.setDpi(imageDPI);
			} else {
				errorList.add("format not among one of specified");
			}

		}

		// optional params

		if (node.has("collectionMethod")) {
			if (node.get("collectionMethod").has("collectionDescription")) {
				if (!node.get("collectionMethod").get("collectionDescription").isArray()) {
					errorList.add("collectionDescription field should be String Array");
				} else {
					int size = node.get("collectionMethod").get("collectionDescription").size();
					if (size > 10 || size < 1) {
						errorList.add("collectionDescription field Array should contain atleast 1");
					} else {
						try {
							String collectionDescription = node.get("collectionMethod").get("collectionDescription")
									.get(0).asText();

							OcrCollectionMethod.CollectionDescriptionEnum collectionDescriptionEnum = OcrCollectionMethod.CollectionDescriptionEnum
									.fromValue(collectionDescription);

							OcrCollectionMethod ocrCollectionMethod = new OcrCollectionMethod();

							List<OcrCollectionMethod.CollectionDescriptionEnum> list = new ArrayList<OcrCollectionMethod.CollectionDescriptionEnum>();
							list.add(collectionDescriptionEnum);
							ocrCollectionMethod.setCollectionDescription(list);
                            docLayoutParamsSchema.setCollectionMethod(ocrCollectionMethod);
							/*
							 * collectionDetails is non mandatory
							 */
							if (node.get("collectionMethod").has("collectionDetails")) { 
								
							if (!node.get("collectionMethod").get("collectionDetails").has("ocrTool")) {
								errorList.add("collectionDetails should contain ocrTool");
							} else if (!node.get("collectionMethod").get("collectionDetails").get("ocrTool")
									.isTextual()) {
								errorList.add("ocrTool should be String");
							} else {
								String ocrTool = node.get("collectionMethod").get("collectionDetails").get("ocrTool")
										.asText();
								CollectionDetailsOcr.OcrToolEnum ocrToolEnum = CollectionDetailsOcr.OcrToolEnum
										.fromValue(ocrTool);
								if (ocrToolEnum != null) {
									CollectionDetailsOcr collectionDetailsOcr = new CollectionDetailsOcr();
									collectionDetailsOcr.setOcrTool(ocrToolEnum);
									if (node.get("collectionMethod").get("collectionDetails").has("ocrToolVersion")) {
										String ocrToolVersion = node.get("collectionMethod").get("collectionDetails")
												.get("ocrToolVersion").asText();
										collectionDetailsOcr.setOcrToolVersion(ocrToolVersion);
									}
									ocrCollectionMethod.setCollectionDetails(collectionDetailsOcr);

									docLayoutParamsSchema.setCollectionMethod(ocrCollectionMethod);

								} else {
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
			} else {
				errorList.add(
						"if collectionMethod then collectionDescription should be present inside collectionMethod");
			}
		}

		if (!errorList.isEmpty())
			throw new IOException(errorList.toString());

		log.info("******** Exiting deserializer ********");
		return docLayoutParamsSchema;
	}

}
