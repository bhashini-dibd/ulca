package io.swagger.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
* OneOfDatasetParamsSchemaParams
*/
/*
 * @JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include =
 * JsonTypeInfo.As.PROPERTY, property = "type")
 * 
 * @JsonSubTypes({
 * 
 * @JsonSubTypes.Type(value = TranslationBenchmarkDatasetParamsSchema.class,
 * name = "TranslationBenchmarkDatasetParamsSchema"),
 * 
 * @JsonSubTypes.Type(value = TransliterationBenchmarkDatasetParamsSchema.class,
 * name = "TransliterationBenchmarkDatasetParamsSchema"),
 * 
 * @JsonSubTypes.Type(value = AsrBenchmarkDatasetParamsSchema.class, name =
 * "AsrBenchmarkDatasetParamsSchema"),
 * 
 * @JsonSubTypes.Type(value = OcrBenchmarkDatasetParamsSchema.class, name =
 * "OcrBenchmarkDatasetParamsSchema"),
 * 
 * @JsonSubTypes.Type(value =
 * TxtLangDetectionBenchmarkDatasetParamsSchema.class, name =
 * "TxtLangDetectionBenchmarkDatasetParamsSchema"),
 * 
 * @JsonSubTypes.Type(value = NerBenchmarkDatasetParamsSchema.class, name =
 * "NerBenchmarkDatasetParamsSchema"),
 * 
 * @JsonSubTypes.Type(value =
 * AudioLangDetectionBenchmarkDatasetParamsSchema.class, name =
 * "AudioLangDetectionBenchmarkDatasetParamsSchema") })
 */
public interface OneOfDatasetParamsSchemaParams {

}
