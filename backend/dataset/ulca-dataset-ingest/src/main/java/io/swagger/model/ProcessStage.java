package io.swagger.model;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * various life-cycle stage of model when benchmarking
 */
public enum ProcessStage {
  SUBMITTED("submitted"),
    BENCHMARKED("benchmarked"),
    PUBLISHED("published");

  private String value;

  ProcessStage(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ProcessStage fromValue(String text) {
    for (ProcessStage b : ProcessStage.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
