package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * dpi of the image file
 */
public enum ImageDPI {
  _300_DPI("300_dpi"),
    _72_DPI("72_dpi");

  private String value;

  ImageDPI(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ImageDPI fromValue(String text) {
    for (ImageDPI b : ImageDPI.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
