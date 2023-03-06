package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfModelBenchmarksPerformance
*/
/*
 * @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include =
 * JsonTypeInfo.As.PROPERTY, property = "type")
 * 
 * @JsonSubTypes({
 * 
 * @JsonSubTypes.Type(value = TranslationBenchmarkResult.class, name =Gg
 * "TranslationBenchmarkResult"),
 * 
 * @JsonSubTypes.Type(value = TransliterationBenchmarkResult.class, name =
 * "TransliterationBenchmarkResult"),
 * 
 * @JsonSubTypes.Type(value = DocumentLayoutBenchmarkResult.class, name =
 * "DocumentLayoutBenchmarkResult"),
 * 
 * @JsonSubTypes.Type(value = DocumentOCRBenchmark.class, name =
 * "DocumentOCRBenchmark"),
 * 
 * @JsonSubTypes.Type(value = ASRBenchmarkResult.class, name =
 * "ASRBenchmarkResult"),
 * 
 * @JsonSubTypes.Type(value = TxtLangDetectionBenchmarkResult.class, name =
 * "TxtLangDetectionBenchmarkResult") })
 */
public interface OneOfModelBenchmarksPerformance {

}