package io.swagger.pipelinerequest;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * supported textual formats
 */
public enum TextFormat1 {
  SRT("srt"),
    TRANSCRIPT("transcript"),
    WEBVTT("webvtt"),
    ALTERNATIVES("alternatives");

  private String value;

  TextFormat1(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static TextFormat1 fromValue(String text) {
    for (TextFormat1 b : TextFormat1.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
