package com.ulca.dataset.model.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.DomainEnum;
import io.swagger.model.LanguagePair;
import io.swagger.model.MonolingualParamsSchema;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.model.SupportedLanguages;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonolingualDatasetParamsSchemaDeserializer extends StdDeserializer<MonolingualParamsSchema> {

	protected MonolingualDatasetParamsSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public MonolingualDatasetParamsSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public MonolingualParamsSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** Entry ParallelDatasetParamsSchemaDeserializer :: deserializer ********");

		ObjectMapper mapper = new ObjectMapper();

		MonolingualParamsSchema monolingualParamsSchema = new MonolingualParamsSchema();
		JsonNode node = p.readValueAsTree();

		ArrayList<String> errorList = new ArrayList<String>();

		JSONObject obj = new JSONObject(node.toPrettyString());

		Set<String> keys = obj.keySet();

		for (String k : keys) {
			try {
				MonolingualDatasetParamsSchemaKeys key = MonolingualDatasetParamsSchemaKeys.valueOf(k);
			} catch (Exception ex) {
				log.info("MonolingualDatasetParamsSchemaKeys " + k + " not in defined keys");
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
			if (type != DatasetType.MONOLINGUAL_CORPUS) {
				errorList.add("datasetType field value " + DatasetType.MONOLINGUAL_CORPUS.toString());
			}
			monolingualParamsSchema.setDatasetType(type);

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
					}

				}
				if (node.get("languages").has("targetLanguageName")) {
					String targetLanguageName = node.get("languages").get("targetLanguageName").asText();
					lp.setSourceLanguageName(targetLanguageName);
				}

				monolingualParamsSchema.setLanguages(lp);
			} catch (Exception e) {
				errorList.add("languages field value not proper.");
				e.printStackTrace();
			}

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
					monolingualParamsSchema.setCollectionSource(collectionSource);
				}

			} catch (Exception e) {
				errorList.add("collectionSource field value not proper.");
				e.printStackTrace();
			}
		}

		if (!node.has("domain")) {
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

				monolingualParamsSchema.setDomain(domain);
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
					monolingualParamsSchema.setLicense(license);
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
				monolingualParamsSchema.setSubmitter(submitter);
			} catch (Exception e) {
				errorList.add("submitter field value not proper.");
				e.printStackTrace();
			}

		}

		// optional params

		if (node.has("version")) {
			if (!node.get("version").isTextual()) {
				errorList.add("version field should be String");
			} else {
				String version = node.get("version").asText();
				monolingualParamsSchema.setVersion(version);
			}

		}
		if (node.has("licenseUrl")) {
			if (!node.get("licenseUrl").isTextual()) {
				errorList.add("licenseUrl field should be String");
			} else {
				String licenseUrl = node.get("licenseUrl").asText();
				monolingualParamsSchema.setLicenseUrl(licenseUrl);
			}

		}

		if (!errorList.isEmpty())
			throw new IOException(errorList.toString());

		return monolingualParamsSchema;
	}

}
