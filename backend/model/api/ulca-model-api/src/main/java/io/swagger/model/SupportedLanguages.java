package io.swagger.model;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This attribute defines the  language codes(iso-639-1, iso 639-2) supported under ULCA
 */
public enum SupportedLanguages {
  EN("en"),
    HI("hi"),
    MR("mr"),
    TA("ta"),
    TE("te"),
    KN("kn"),
    GU("gu"),
    PA("pa"),
    BN("bn"),
    ML("ml"),
    AS("as"),
    BRX("brx"),
    DOI("doi"),
    KS("ks"),
    KOK("kok"),
    MAI("mai"),
    MNI("mni"),
    NE("ne"),
    OR("or"),
    SD("sd"),
    SI("si"),
    UR("ur"),
    SAT("sat"),
    LUS("lus"),
    NJZ("njz"),
    PNR("pnr"),
    KHA("kha"),
    GRT("grt"),
    SA("sa"),
    RAJ("raj"),
    BHO("bho"),
    GOM("gom"),
    AWA("awa"),
    HNE("hne"),
    MAG("mag"),
    MWR("mwr"),
    SJP("sjp"),
    GBM("gbm"),
    TCY("tcy"),
    HLB("hlb"),
    BIH("bih"),
    ANP("anp"),
    BNS("bns"),
    BRA("bra"),
    GON("gon"),
    HC("hc"),
    TC("tc"),
    XR("xr"),
    AW("aw"),
    MIXED("mixed"),
    UNKNOWN("unknown");

  private String value;

  SupportedLanguages(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static SupportedLanguages fromValue(String text) {
    for (SupportedLanguages b : SupportedLanguages.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
