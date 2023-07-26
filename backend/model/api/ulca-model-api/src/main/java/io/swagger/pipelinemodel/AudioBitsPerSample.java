package io.swagger.pipelinemodel;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * number of bits per sample
 */
public enum AudioBitsPerSample {
  SIXTEEN("sixteen"),
    EIGHT("eight");

  private String value;

  AudioBitsPerSample(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static AudioBitsPerSample fromValue(String text) {
    for (AudioBitsPerSample b : AudioBitsPerSample.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
