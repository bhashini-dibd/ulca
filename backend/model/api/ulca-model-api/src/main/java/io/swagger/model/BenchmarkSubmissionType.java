package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * supported dataset type under language initiatives
 */
public enum BenchmarkSubmissionType {
    SUBMITTED("Submitted"),
    VERIFIED("Verified"),
    FAILED("failed"),
   

  private String value;

	BenchmarkSubmissionType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static BenchmarkSubmissionType fromValue(String text) {
    for (BenchmarkSubmissionType b : BenchmarkSubmissionType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
