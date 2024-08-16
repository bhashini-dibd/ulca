package io.swagger.pipelinerequest;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.model.SupportedTasks;
/**
* TaskSchema
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.EXISTING_PROPERTY,
  property = "taskType")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationTaskInference.class, name = "translation"),
  @JsonSubTypes.Type(value = ASRTaskInference.class, name = "asr"),
  @JsonSubTypes.Type(value = TTSTaskInference.class, name = "tts"),
  @JsonSubTypes.Type(value = TransliterationTaskInference.class, name = "transliteration"),
  @JsonSubTypes.Type(value = OCRTaskInference.class, name = "ocr")
})
public interface TaskSchema {

    String getTaskType();
}
