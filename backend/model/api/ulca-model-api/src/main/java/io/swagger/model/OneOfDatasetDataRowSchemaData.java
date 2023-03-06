package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfDatasetDataRowSchemaData
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationBenchmarkDatasetRowSchema.class, name = "TranslationBenchmarkDatasetRowSchema"),
  @JsonSubTypes.Type(value = TransliterationBenchmarkDatasetRowSchema.class, name = "TransliterationBenchmarkDatasetRowSchema"),
  @JsonSubTypes.Type(value = AsrBenchmarkDatasetRowSchema.class, name = "AsrBenchmarkDatasetRowSchema"),
  @JsonSubTypes.Type(value = OcrBenchmarkDatasetRowSchema.class, name = "OcrBenchmarkDatasetRowSchema"),
  @JsonSubTypes.Type(value = TxtLangDetectionBenchmarkDatasetRowSchema.class, name = "TxtLangDetectionBenchmarkDatasetRowSchema"),
  @JsonSubTypes.Type(value = NerBenchmarkDatasetRowSchema.class, name = "NerBenchmarkDatasetRowSchema"),
  @JsonSubTypes.Type(value = AudioLangDetectionBenchmarkDatasetRowSchema.class, name = "AudioLangDetectionBenchmarkDatasetRowSchema")
})
public interface OneOfDatasetDataRowSchemaData {

}
