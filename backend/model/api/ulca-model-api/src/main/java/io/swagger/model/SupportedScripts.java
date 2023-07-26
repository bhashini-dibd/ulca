package io.swagger.model;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This attribute defines the  Script codes(ISO 15924) supported under ULCA
 */
public enum SupportedScripts {
  BENG("Beng"),
    DEVA("Deva"),
    THAA("Thaa"),
    GUJR("Gujr"),
    ARAN("Aran"),
    ORYA("Orya"),
    GURU("Guru"),
    ARAB("Arab"),
    SINH("Sinh"),
    KNDA("Knda"),
    MLYM("Mlym"),
    TAML("Taml"),
    TELU("Telu"),
    MTEI("Mtei"),
    OLCK("Olck"),
    LATN("Latn");

  private String value;

  SupportedScripts(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static SupportedScripts fromValue(String text) {
    for (SupportedScripts b : SupportedScripts.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
