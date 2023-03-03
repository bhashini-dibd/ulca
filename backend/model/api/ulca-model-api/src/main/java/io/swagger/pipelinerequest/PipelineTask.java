package io.swagger.pipelinerequest;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* PipelineTask
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationTask.class, name = "TranslationTask"),
  @JsonSubTypes.Type(value = ASRTask.class, name = "ASRTask"),
  @JsonSubTypes.Type(value = TTSTask.class, name = "TTSTask")
})
public interface PipelineTask {

    SupportedTasks getType();
}
