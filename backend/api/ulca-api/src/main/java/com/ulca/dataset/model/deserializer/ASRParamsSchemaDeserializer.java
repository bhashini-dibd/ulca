package com.ulca.dataset.model.deserializer;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import io.swagger.model.ASRParamsSchema;
import io.swagger.model.AudioQualityEvaluation;
import io.swagger.model.AudioQualityEvaluation.MethodTypeEnum;
import io.swagger.model.CollectionDetailsAudioAutoAligned;
import io.swagger.model.CollectionDetailsMachineGeneratedTranscript;
import io.swagger.model.CollectionDetailsManualTranscribed;
import io.swagger.model.CollectionMethodAudio;
import io.swagger.model.CollectionMethodAudio.CollectionDescriptionEnum;
import io.swagger.model.DatasetType;
import io.swagger.model.Domain;
import io.swagger.model.LanguagePair;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.model.TranscriptionEvaluationMethod1;
import io.swagger.model.WadaSnr;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ASRParamsSchemaDeserializer extends StdDeserializer<ASRParamsSchema> {

	protected ASRParamsSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public ASRParamsSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ASRParamsSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		log.info("******** inside deserializer ********");
		ASRParamsSchema asrParamsSchema = new ASRParamsSchema();
		JsonNode node = p.readValueAsTree();

		String datasetType = node.get("datasetType").asText();
		log.info(datasetType);

		DatasetType type = DatasetType.fromValue(datasetType);

		asrParamsSchema.setDatasetType(type);

		ObjectMapper mapper = new ObjectMapper();
		LanguagePair lp = mapper.readValue(node.get("languages").toPrettyString(), LanguagePair.class);
		asrParamsSchema.setLanguages(lp);

		Submitter submitter = mapper.readValue(node.get("submitter").toPrettyString(), Submitter.class);
		asrParamsSchema.setSubmitter(submitter);

		Source collectionSource = mapper.readValue(node.get("collectionSource").toPrettyString(), Source.class);
		log.info(node.get("collectionSource").toPrettyString());
		asrParamsSchema.setCollectionSource(collectionSource);

		String methodType = node.get("snr").get("methodType").asText();
		if (methodType.equals("WadaSnr")) {
			WadaSnr wadaSnr = mapper.readValue(node.get("snr").get("methodDetails").toPrettyString(), WadaSnr.class);
			AudioQualityEvaluation audioQualityEvaluation = new AudioQualityEvaluation();
			audioQualityEvaluation.setMethodType(MethodTypeEnum.WADASNR);
			audioQualityEvaluation.setMethodDetails(wadaSnr);
			asrParamsSchema.setSnr(audioQualityEvaluation);

		}

		Domain domain = mapper.readValue(node.get("domain").toPrettyString(), Domain.class);
		asrParamsSchema.setDomain(domain);

		String collectionDescription = node.get("collectionMethod").get("collectionDescription").get(0).asText();
		if (collectionDescription.equals("auto-aligned")) {
			CollectionMethodAudio collectionMethodAudio = new CollectionMethodAudio();
			ArrayList<CollectionDescriptionEnum> colDescList = new ArrayList<CollectionDescriptionEnum>();
			colDescList.add(CollectionDescriptionEnum.AUTO_ALIGNED);
			CollectionDetailsAudioAutoAligned collectionDetailsAudioAutoAligned = mapper.readValue(
					node.get("collectionMethod").get("collectionDetails").toPrettyString(),
					CollectionDetailsAudioAutoAligned.class);
			collectionMethodAudio.setCollectionDescription(colDescList);
			collectionMethodAudio.setCollectionDetails(collectionDetailsAudioAutoAligned);
			asrParamsSchema.setCollectionMethod(collectionMethodAudio);
			log.info("auto-aligned");

		}
		if (collectionDescription.equals("machine-generated-transcript")) {
			CollectionMethodAudio collectionMethodAudio = new CollectionMethodAudio();
			ArrayList<CollectionDescriptionEnum> colDescList = new ArrayList<CollectionDescriptionEnum>();
			colDescList.add(CollectionDescriptionEnum.MACHINE_GENERATED_TRANSCRIPT);
			CollectionDetailsMachineGeneratedTranscript collectionDetailsMachineGeneratedTranscript = new CollectionDetailsMachineGeneratedTranscript();
			TranscriptionEvaluationMethod1 transcriptionEvaluationMethod1 = mapper.readValue(
					node.get("collectionMethod").get("collectionDetails").get("evaluationMethod").toPrettyString(),
					TranscriptionEvaluationMethod1.class);
			collectionDetailsMachineGeneratedTranscript.setEvaluationMethod(transcriptionEvaluationMethod1);
			collectionDetailsMachineGeneratedTranscript
					.setAsrModel(node.get("collectionMethod").get("collectionDetails").get("asrModel").asText());
			collectionMethodAudio.setCollectionDescription(colDescList);
			collectionMethodAudio.setCollectionDetails(collectionDetailsMachineGeneratedTranscript);
			asrParamsSchema.setCollectionMethod(collectionMethodAudio);

			log.info("machine-generated-transcript");

		}
		if (collectionDescription.equals("manual-transcribed")) {
			CollectionMethodAudio collectionMethodAudio = new CollectionMethodAudio();
			ArrayList<CollectionDescriptionEnum> colDescList = new ArrayList<CollectionDescriptionEnum>();
			colDescList.add(CollectionDescriptionEnum.MANUAL_TRANSCRIBED);
			CollectionDetailsManualTranscribed collectionDetailsManualTranscribed = mapper.readValue(
					node.get("collectionMethod").get("collectionDetails").toPrettyString(),
					CollectionDetailsManualTranscribed.class);
			collectionMethodAudio.setCollectionDescription(colDescList);
			collectionMethodAudio.setCollectionDetails(collectionDetailsManualTranscribed);
			asrParamsSchema.setCollectionMethod(collectionMethodAudio);

			log.info("manual-transcribed");

		}

		io.swagger.model.License license = mapper.readValue(node.get("license").toPrettyString(),
				io.swagger.model.License.class);

		asrParamsSchema.setLicense(license);
		log.info(collectionDescription);

		log.info("******** Exiting deserializer ********");
		return asrParamsSchema;
	}

}
