package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfBenchmarksItems
*/
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = TranslationBenchmark.class, name = "TranslationBenchmark"),
  @JsonSubTypes.Type(value = TransliterationBenchmark.class, name = "TransliterationBenchmark"),
  @JsonSubTypes.Type(value = DocumentLayoutBenchmark.class, name = "DocumentLayoutBenchmark"),
  @JsonSubTypes.Type(value = DocumentOCRBenchmark.class, name = "DocumentOCRBenchmark"),
  @JsonSubTypes.Type(value = ASRBenchmark.class, name = "ASRBenchmark"),
  @JsonSubTypes.Type(value = TxtLangDetectionBenchmark.class, name = "TxtLangDetectionBenchmark")
})
public interface OneOfBenchmarksItems {

}
