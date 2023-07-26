package io.swagger.pipelinerequest;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* AnyOfPipelineInferenceAPIEndPointSchema
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = InferenceSchema.class, name = "InferenceSchema"),
  @JsonSubTypes.Type(value = InferenceSchemaArray.class, name = "InferenceSchemaArray")
})
public interface AnyOfPipelineInferenceAPIEndPointSchema {

}
