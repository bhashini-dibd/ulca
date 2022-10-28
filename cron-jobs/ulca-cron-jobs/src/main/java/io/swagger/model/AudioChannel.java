package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * number of channel in the audio
 */
public enum AudioChannel {
  MONO("mono"),
    STEREO("stereo");

  private String value;

  AudioChannel(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static AudioChannel fromValue(String text) {
    for (AudioChannel b : AudioChannel.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
