package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * gender description
 */
public enum Gender {
  MALE("male"),
    FEMALE("female"),
    TRANSGENDER("transgender"),
    NON_SPECIFIED("non-specified"),
    OTHERS("others");

  private String value;

  Gender(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static Gender fromValue(String text) {
    for (Gender b : Gender.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
