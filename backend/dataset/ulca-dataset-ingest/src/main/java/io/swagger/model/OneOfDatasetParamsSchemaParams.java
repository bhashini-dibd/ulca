package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfDatasetParamsSchemaParams
*/

// commented as we are deserializing manually to have better control on deserializer
/*
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = ParallelDatasetParamsSchema.class, name = "ParallelDatasetParamsSchema"),
  @JsonSubTypes.Type(value = MonolingualParamsSchema.class, name = "MonolingualParamsSchema"),
  @JsonSubTypes.Type(value = TransliterationDatasetParamsSchema.class, name = "TransliterationDatasetParamsSchema"),
  @JsonSubTypes.Type(value = AsrParamsSchema.class, name = "AsrParamsSchema"),
  @JsonSubTypes.Type(value = TtsParamsSchema.class, name = "TtsParamsSchema"),
  @JsonSubTypes.Type(value = AsrUnlabeledParamsSchema.class, name = "AsrUnlabeledParamsSchema"),
  @JsonSubTypes.Type(value = OcrDatasetParamsSchema.class, name = "OcrDatasetParamsSchema"),
  @JsonSubTypes.Type(value = DocumentLayoutParamsSchema.class, name = "DocumentLayoutParamsSchema"),
  @JsonSubTypes.Type(value = GlossaryDatasetParamsSchema.class, name = "GlossaryDatasetParamsSchema"),
  @JsonSubTypes.Type(value = NerDatasetParamsSchema.class, name = "NerDatasetParamsSchema")
})
*/

public interface OneOfDatasetParamsSchemaParams {

}
