package io.swagger.model;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * type of image on which text area is annotated.
 */
public enum ImageTextType {
  SCENE_TEXT("scene-text"),
    TYPEWRITER_TYPED_TEXT("typewriter-typed-text"),
    COMPUTER_TYPED_TEXT("computer-typed-text"),
    HANDWRITTEN_TEXT("handwritten-text");

  private String value;

  ImageTextType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ImageTextType fromValue(String text) {
    for (ImageTextType b : ImageTextType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
