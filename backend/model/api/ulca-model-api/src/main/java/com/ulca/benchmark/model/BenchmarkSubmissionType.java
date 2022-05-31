package com.ulca.benchmark.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * benchmark submitted status
 */
public enum BenchmarkSubmissionType {
    SUBMITTED("Submitted"),
    COMPLETED("Completed"),
    FAILED("Failed");


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

