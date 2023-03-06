package io.swagger.pipelinemodel;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * the category of model under which it has been released or trained
 */
public enum SupportedTasks {
  TRANSLATION("translation"),
    TRANSLITERATION("transliteration"),
    TTS("tts"),
    ASR("asr"),
    DOCUMENT_LAYOUT("document-layout"),
    OCR("ocr"),
    GLOSSARY("glossary"),
    NER("ner"),
    TXT_LANG_DETECTION("txt-lang-detection"),
    AUDIO_LANG_DETECTION("audio-lang-detection");

  private String value;

  SupportedTasks(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static SupportedTasks fromValue(String text) {
    for (SupportedTasks b : SupportedTasks.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
