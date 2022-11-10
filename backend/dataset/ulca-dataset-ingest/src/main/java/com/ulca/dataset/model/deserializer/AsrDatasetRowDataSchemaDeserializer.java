package com.ulca.dataset.model.deserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.swagger.model.*;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ulca.dataset.util.DateUtil;

import io.swagger.model.AudioQualityEvaluation.MethodTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsrDatasetRowDataSchemaDeserializer extends StdDeserializer<AsrRowSchema> {

	protected AsrDatasetRowDataSchemaDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	public AsrDatasetRowDataSchemaDeserializer() {
		this(null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public AsrRowSchema deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		
		ObjectMapper mapper = new ObjectMapper();
		AsrRowSchema asrRowSchema = new AsrRowSchema();
		JsonNode node = p.readValueAsTree();

		ArrayList<String> errorList = new ArrayList<String>();

		JSONObject obj = new JSONObject(node.toPrettyString());

		Set<String> keys = obj.keySet();

		for (String k : keys) {
			try {
				AsrRowDataSchemaKeys key = AsrRowDataSchemaKeys.valueOf(k);
			} catch (Exception ex) {
				log.info("AsrRowDataSchemaKeys not valid ");
				errorList.add(k + " unknown property ");
			}

		}
		// required

		if (!node.has("audioFilename")) {
			errorList.add("audioFilename field should be present");
		} else if (!node.get("audioFilename").isTextual()) {
			errorList.add("audioFilename field should be String");
		} else {

			String audioFilename = node.get("audioFilename").asText();
			asrRowSchema.setAudioFilename(audioFilename);

		}

		if (!node.has("text")) {
			errorList.add("text field should be present");
		} else if (!node.get("text").isTextual()) {
			errorList.add("text field should be String");
		} else {

			String text = node.get("text").asText();
			asrRowSchema.setText(text);

		}
		
		// optional params
		if (!node.has("state")) {
			errorList.add("state field should be present");
		} else if (!node.get("state").isTextual()) {
			errorList.add("state field should be String");
		} else {

			String state = node.get("state").asText();
			asrRowSchema.setState(state);

		}


		if (!node.has("district")) {
			errorList.add("district field should be present");
		} else if (!node.get("district").isTextual()) {
			errorList.add("district field should be String");
		} else {

			String district = node.get("district").asText();
			asrRowSchema.setDistrict(district);

		}

		if (!node.has("education")) {
			errorList.add("education field should be present");
		} else if (!node.get("education").isTextual()) {
			errorList.add("education field should be String");
		} else {

			String education = node.get("education").asText();
			asrRowSchema.setEducation(education);

		}

		if (!node.has("socioEconomic")) {
			errorList.add("socioEconomic field should be present");
		} else if (!node.get("socioEconomic").isTextual()) {
			errorList.add("socioEconomic field should be String");
		} else {

			String socioEconomic = node.get("socioEconomic").asText();
			asrRowSchema.setSocioEconomic(socioEconomic);

		}

		if (!node.has("imageFilename")) {
			errorList.add("imageFilename field should be present");
		} else if (!node.get("imageFilename").isTextual()) {
			errorList.add("imageFilename field should be String");
		} else {

			String imageFilename = node.get("imageFilename").asText();
			asrRowSchema.setImageFilename(imageFilename);

		}
		if (!node.has("assertLanguage")) {
			errorList.add("assertLanguage field should be present");
		} else if (!node.get("assertLanguage").isTextual()) {
			errorList.add("assertLanguage field should be String");
		} else {

			String assertLanguage = node.get("assertLanguage").asText();
			asrRowSchema.setAssertLanguage(assertLanguage);

		}

		if (!node.has("languagesSpoken")) {
			errorList.add("languagesSpoken field should be present");
		} else if (!node.get("languagesSpoken").isArray()) {
			errorList.add("languagesSpoken field should be String array");
		} else {
			try {
				AsrlanguagesSpoken languagesSpoken = mapper.readValue(node.get("languagesSpoken").toPrettyString(),AsrlanguagesSpoken.class);
                if(languagesSpoken.size() < 0){
					errorList.add("languagesSpoken array size should be > 0 ");
				} else {
					asrRowSchema.setLanguagesSpoken(languagesSpoken);
				}
			} catch (Exception e) {
				errorList.add("languagesSpoken field value not proper.");
				e.printStackTrace();
			}
		}




		
		if (node.has("duration")) {
			if (!node.get("duration").isNumber()) {
				errorList.add("duration field should be Number");
			} else {
				BigDecimal duration = node.get("duration").decimalValue();
				asrRowSchema.setDuration(duration);

			}

		}

		if (node.has("pinCode")) {
			if (!node.get("pinCode").isNumber()) {
				errorList.add("pinCode field should be Number");
			} else {
				BigDecimal pinCode = node.get("pinCode").decimalValue();
				asrRowSchema.setPinCode(pinCode);

			}

		}

		if (node.has("stayYears")) {
			if (!node.get("stayYears").isNumber()) {
				errorList.add("stayYears field should be Number");
			} else {
				BigDecimal stayYears = node.get("stayYears").decimalValue();
				asrRowSchema.setStayYears(stayYears);

			}

		}




		if (node.has("exactAge")) {
			if (!node.get("exactAge").isNumber()) {
				errorList.add("exactAge field should be Number");
			} else {
				BigDecimal exactAge = node.get("exactAge").decimalValue();
				asrRowSchema.setExactAge(exactAge);

			}

		}

		if (node.has("speaker")) {
			if (!node.get("speaker").isTextual()) {
				errorList.add("speaker field should be String");
			} else {
				String speaker = node.get("speaker").asText();
				asrRowSchema.setSpeaker(speaker);
			}
		} 
		
		if(node.has("collectionSource")) {
			
			if (!node.get("collectionSource").isArray()) {
				errorList.add("collectionSource field should be String array");
			} else {

				try {
					Source collectionSource = mapper.readValue(node.get("collectionSource").toPrettyString(), Source.class);
					if(collectionSource.size() > 10 || collectionSource.size() < 0) {
						errorList.add("collectionSource array size should be > 0 and <= 10");
					}else {
						asrRowSchema.setCollectionSource(collectionSource);
					}
					
				} catch (Exception e) {
					errorList.add("collectionSource field value not proper.");
					e.printStackTrace();
				}
			}
		}

		if (node.has("endTime")) {
			if (!node.get("endTime").isTextual()) {
				errorList.add("endTime field should be String");
			} else {
				String endTime = node.get("endTime").asText();
				if(DateUtil.timeInHhMmSsFormat(endTime)) {
					asrRowSchema.setEndTime(endTime);
				}else {
					errorList.add("endTime should be in hh:mm:ss format");
				}
			}
		} 

		if (node.has("startTime")) {
			if (!node.get("startTime").isTextual()) {
				errorList.add("startTime field should be String");
			} else {
				String startTime = node.get("startTime").asText();
				if(DateUtil.timeInHhMmSsFormat(startTime)) {
					asrRowSchema.setStartTime(startTime);
				}else {
					errorList.add("startTime should be in hh:mm:ss format");
				}
			}
		}

		if (node.has("channel")) {
			if (!node.get("channel").isTextual()) {
				errorList.add("channel field should be String");
			} else {
				String channel = node.get("channel").asText();
				AudioChannel audioChannel = AudioChannel.fromValue(channel);
				if (audioChannel != null) {
					asrRowSchema.setChannel(audioChannel);
				} else {
					errorList.add("channel not among one of specified");
				}
			}
		}

		if (node.has("samplingRate")) {
			if (!node.get("samplingRate").isNumber()) {
				errorList.add("samplingRate field should be Number");
			} else {
				BigDecimal samplingRate = node.get("samplingRate").decimalValue();
				asrRowSchema.setSamplingRate(samplingRate);
			}
		}

		if (node.has("bitsPerSample")) {
			if (!node.get("bitsPerSample").isTextual()) {
				errorList.add("bitsPerSample field should be String");
			} else {
				String bitsPerSample = node.get("bitsPerSample").asText();

				AudioBitsPerSample audioBitsPerSample = AudioBitsPerSample.fromValue(bitsPerSample);
				if (audioBitsPerSample != null) {
					asrRowSchema.setBitsPerSample(audioBitsPerSample);

				} else {
					errorList.add("bitsPerSample not among one of specified");
				}
			}
		}

		if (node.has("gender")) {
			if (!node.get("gender").isTextual()) {
				errorList.add("gender field should be String");
			} else {
				String gender = node.get("gender").asText();

				Gender genderenum = Gender.fromValue(gender);

				if (genderenum != null) {
					asrRowSchema.setGender(genderenum);

				} else {
					errorList.add("gender not among one of specified values");
				}
			}
		}

		if (node.has("age")) {
			if (!node.get("age").isTextual()) {
				errorList.add("age field should be String");
			} else {
				String age = node.get("age").asText();

				AsrRowSchema.AgeEnum ageEnum = AsrRowSchema.AgeEnum.fromValue(age);

				if (ageEnum != null) {
					asrRowSchema.setAge(ageEnum);

				} else {
					errorList.add("age not among one of specified values");
				}
			}
		}


		if (node.has("dialect")) {
			if (!node.get("dialect").isTextual()) {
				errorList.add("dialect field should be String");
			} else {
				String dialect = node.get("dialect").asText();
				AsrRowSchema.DialectEnum dialectEnum = AsrRowSchema.DialectEnum.fromValue(dialect);

				if (dialectEnum != null) {
					asrRowSchema.setDialect(dialectEnum);

				} else {
					errorList.add("dialect not among one of specified values");
				}
			}
		}

		if (node.has("snr")) {
			if (!node.get("snr").has("methodType")) {
				errorList.add("methodType should be present");
			} else if (!node.get("snr").get("methodType").isTextual()) {
				errorList.add("methodType should be String");
			} else {
				String methodType = node.get("snr").get("methodType").asText();
				if (methodType.equals("WadaSnr")) {
					WadaSnr wadaSnr = mapper.readValue(node.get("snr").get("methodDetails").toPrettyString(),
							WadaSnr.class);
					AudioQualityEvaluation audioQualityEvaluation = new AudioQualityEvaluation();
					audioQualityEvaluation.setMethodType(MethodTypeEnum.WADASNR);
					audioQualityEvaluation.setMethodDetails(wadaSnr);
					asrRowSchema.setSnr(audioQualityEvaluation);

				} else {
					errorList.add("methodType is not one of specified values");
				}
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
						CollectionMethodAudio.CollectionDescriptionEnum collectionDescriptionEnum = CollectionMethodAudio.CollectionDescriptionEnum
								.fromValue(collectionDescription);

						CollectionMethodAudio collectionMethodAudio = new CollectionMethodAudio();
						List<CollectionMethodAudio.CollectionDescriptionEnum> list = new ArrayList<CollectionMethodAudio.CollectionDescriptionEnum>();
						list.add(collectionDescriptionEnum);
						collectionMethodAudio.setCollectionDescription(list);

						asrRowSchema.setCollectionMethod(collectionMethodAudio);

						switch (collectionDescriptionEnum) {
						case AUTO_ALIGNED:
							if (node.get("collectionMethod").get("collectionDetails").has("alignmentTool")) {
								if (node.get("collectionMethod").get("collectionDetails").get("alignmentTool")
										.isTextual()) {
									String alignmentTool = node.get("collectionMethod").get("collectionDetails")
											.get("alignmentTool").asText();
									CollectionDetailsAudioAutoAligned.AlignmentToolEnum alignmentToolEnum = CollectionDetailsAudioAutoAligned.AlignmentToolEnum
											.fromValue(alignmentTool);
									if (alignmentToolEnum != null) {

										CollectionDetailsAudioAutoAligned collectionDetailsAudioAutoAligned = mapper
												.readValue(
														node.get("collectionMethod").get("collectionDetails")
																.toPrettyString(),
														CollectionDetailsAudioAutoAligned.class);

										collectionMethodAudio.setCollectionDetails(collectionDetailsAudioAutoAligned);
										asrRowSchema.setCollectionMethod(collectionMethodAudio);

									} else {
										errorList.add("alignmentTool should be from one of specified values");
									}

								} else {
									errorList.add("alignmentTool should be String");
								}

							} else {
								errorList.add("alignmentTool should be present");
							}

							break;

						case MACHINE_GENERATED_TRANSCRIPT:

							CollectionDetailsMachineGeneratedTranscript collectionDetailsMachineGeneratedTranscript = new CollectionDetailsMachineGeneratedTranscript();
							TranscriptionEvaluationMethod1 transcriptionEvaluationMethod1 = mapper
									.readValue(
											node.get("collectionMethod").get("collectionDetails")
													.get("evaluationMethod").toPrettyString(),
											TranscriptionEvaluationMethod1.class);

							collectionDetailsMachineGeneratedTranscript
									.setEvaluationMethod(transcriptionEvaluationMethod1);

							collectionDetailsMachineGeneratedTranscript.setAsrModel(
									node.get("collectionMethod").get("collectionDetails").get("asrModel").asText());
							collectionMethodAudio.setCollectionDetails(collectionDetailsMachineGeneratedTranscript);

							String evaluationMethodType = node.get("collectionMethod").get("collectionDetails")
									.get("evaluationMethodType").asText();

							CollectionDetailsMachineGeneratedTranscript.EvaluationMethodTypeEnum evaluationMethodTypeEnum = CollectionDetailsMachineGeneratedTranscript.EvaluationMethodTypeEnum
									.fromValue(evaluationMethodType);
							collectionDetailsMachineGeneratedTranscript
									.setEvaluationMethodType(evaluationMethodTypeEnum);

							collectionDetailsMachineGeneratedTranscript.setAsrModelVersion(node.get("collectionMethod")
									.get("collectionDetails").get("asrModelVersion").asText());

							asrRowSchema.setCollectionMethod(collectionMethodAudio);

							log.info("machine-generated-transcript");

							break;

						case MANUAL_TRANSCRIBED:

							CollectionDetailsManualTranscribed collectionDetailsManualTranscribed = mapper.readValue(
									node.get("collectionMethod").get("collectionDetails").toPrettyString(),
									CollectionDetailsManualTranscribed.class);
							collectionMethodAudio.setCollectionDetails(collectionDetailsManualTranscribed);
							asrRowSchema.setCollectionMethod(collectionMethodAudio);

							log.info("manual-transcribed");

							break;

						}

					} catch (Exception e) {
						log.info("collection method not proper");
						errorList.add("collectionMethod field value not proper.");
						log.info("tracing the error");

						e.printStackTrace();
					}
				}
			}
		}

		if (!errorList.isEmpty())
			throw new IOException(errorList.toString());

		return asrRowSchema;
	}

}
