package com.ulca.benchmark.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum SupportedBertTgtLangs {
	     EN("en"),
	     BN("bn"),
	     KN("kn"),
         HI("hi"),
	     TA("ta"),
	     TE("te"),
	     PA("pa"),
         MR("mr"),
	    UR("ur"),
	    NE("ne"),
	    ML("ml");


	  private String value;

	  SupportedBertTgtLangs(String value) {
	    this.value = value;
	  }

	  @Override
	  @JsonValue
	  public String toString() {
	    return String.valueOf(value);
	  }

	  @JsonCreator
	  public static SupportedBertTgtLangs fromValue(String text) {
	    for (SupportedBertTgtLangs b : SupportedBertTgtLangs.values()) {
	      if (String.valueOf(b.value).equals(text)) {
	        return b;
	      }
	    }
	    return null;
	  }
	}