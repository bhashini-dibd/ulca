package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * encoding used for representing the input/output binary data
 */
public enum Encoding {
  BASE64("base64"),
    BASE85("base85"),
    BASE36("base36"),
    BASE32("base32"),
    ASCII("ascii"),
    ASCII85("ascii85");

  private String value;

  Encoding(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static Encoding fromValue(String text) {
    for (Encoding b : Encoding.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
