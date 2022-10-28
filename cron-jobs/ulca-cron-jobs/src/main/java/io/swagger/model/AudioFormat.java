package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * audio format of the audio file
 */
public enum AudioFormat {
  WAV("wav"),
    PCM("pcm"),
    MP3("mp3"),
    FLAC("flac");

  private String value;

  AudioFormat(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static AudioFormat fromValue(String text) {
    for (AudioFormat b : AudioFormat.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
