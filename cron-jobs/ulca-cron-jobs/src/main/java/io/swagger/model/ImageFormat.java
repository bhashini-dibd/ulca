package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * image file format description
 */
public enum ImageFormat {
  JPEG("jpeg"),
    BMP("bmp"),
    PNG("png"),
    TIFF("tiff");

  private String value;

  ImageFormat(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ImageFormat fromValue(String text) {
    for (ImageFormat b : ImageFormat.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
