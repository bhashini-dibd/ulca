package io.swagger.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ulca.dataset.model.deserializer.OneOfAudioQualityEvaluationMethodDetailsDeserializer;

/**
* OneOfAudioQualityEvaluationMethodDetails
*/
@JsonDeserialize(using = OneOfAudioQualityEvaluationMethodDetailsDeserializer.class)
public interface OneOfAudioQualityEvaluationMethodDetails {

}
