package io.swagger.pipelinerequest;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* TaskSchema
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationTaskInference.class, name = "TranslationTaskInference"),
  @JsonSubTypes.Type(value = ASRTaskInference.class, name = "ASRTaskInference"),
  @JsonSubTypes.Type(value = TTSTaskInference.class, name = "TTSTaskInference")
})
public interface TaskSchema {

    SupportedTasks getTaskType();
}
