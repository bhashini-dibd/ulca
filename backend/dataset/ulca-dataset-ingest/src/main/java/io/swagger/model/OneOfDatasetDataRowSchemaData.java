package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfDatasetDataRowSchemaData
*/
//commented as we are deserializing manually to have better control on deserializer
/*
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = ParallelDatasetRowSchema.class, name = "ParallelDatasetRowSchema"),
  @JsonSubTypes.Type(value = MonolingualRowSchema.class, name = "MonolingualRowSchema"),
  @JsonSubTypes.Type(value = TransliterationDatasetRowSchema.class, name = "TransliterationDatasetRowSchema"),
  @JsonSubTypes.Type(value = AsrRowSchema.class, name = "AsrRowSchema"),
  @JsonSubTypes.Type(value = TtsRowSchema.class, name = "TtsRowSchema"),
  @JsonSubTypes.Type(value = AsrUnlabeledRowSchema.class, name = "AsrUnlabeledRowSchema"),
  @JsonSubTypes.Type(value = OcrDatasetRowSchema.class, name = "OcrDatasetRowSchema"),
  @JsonSubTypes.Type(value = DocumentLayoutRowSchema.class, name = "DocumentLayoutRowSchema"),
  @JsonSubTypes.Type(value = GlossaryDatasetRowSchema.class, name = "GlossaryDatasetRowSchema"),
  @JsonSubTypes.Type(value = NerDatasetRowSchema.class, name = "NerDatasetRowSchema")
})
*/
public interface OneOfDatasetDataRowSchemaData {

}
