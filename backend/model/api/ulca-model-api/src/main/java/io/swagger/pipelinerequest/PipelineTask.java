package io.swagger.pipelinerequest;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.model.SupportedTasks;
/**
* PipelineTask
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
//  include = JsonTypeInfo.As.PROPERTY,
		  include = JsonTypeInfo.As.EXISTING_PROPERTY,
		  property = "taskType")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationTask.class, name = "translation"),
  @JsonSubTypes.Type(value = ASRTask.class, name = "asr"),
  @JsonSubTypes.Type(value = TTSTask.class, name = "tts"),
  @JsonSubTypes.Type(value = TransliterationTask.class, name = "transliteration"),
  @JsonSubTypes.Type(value = OCRTask.class, name = "ocr"),
  @JsonSubTypes.Type(value = NERTask.class, name = "ner")
})
public interface PipelineTask {

    String getTaskType();
}
