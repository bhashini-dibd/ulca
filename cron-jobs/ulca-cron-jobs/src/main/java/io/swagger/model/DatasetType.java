package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * supported dataset type under language initiatives
 */
public enum DatasetType {
  PARALLEL_CORPUS("parallel-corpus"),
    MONOLINGUAL_CORPUS("monolingual-corpus"),
    TRANSLITERATION_CORPUS("transliteration-corpus"),
    ASR_CORPUS("asr-corpus"),
    TTS_CORPUS("tts-corpus"),
    ASR_UNLABELED_CORPUS("asr-unlabeled-corpus"),
    OCR_CORPUS("ocr-corpus"),
    DOCUMENT_LAYOUT_CORPUS("document-layout-corpus"),
    GLOSSARY_CORPUS("glossary-corpus");

  private String value;

  DatasetType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static DatasetType fromValue(String text) {
    for (DatasetType b : DatasetType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
