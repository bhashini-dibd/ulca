package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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
