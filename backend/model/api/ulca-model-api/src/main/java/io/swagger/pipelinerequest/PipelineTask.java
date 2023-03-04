package io.swagger.pipelinerequest;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.model.SupportedTasks;
/**
* PipelineTask
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "taskType")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationTask.class, name = "TRANSLATION"),
  @JsonSubTypes.Type(value = ASRTask.class, name = "ASR"),
  @JsonSubTypes.Type(value = TTSTask.class, name = "TTS")
})
public interface PipelineTask {

    SupportedTasks getTaskType();
}
