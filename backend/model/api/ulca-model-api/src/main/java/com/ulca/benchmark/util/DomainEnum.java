package com.ulca.benchmark.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum DomainEnum {
	
	GENERAL("general"),
	NEWS("news"), 
	EDUCATION("education"), 
	LEGAL("legal"), 
	GOVERNMENT_PRESS_RELEASE("government-press-release"), 
	HEALTHCARE("healthcare"), 
	AGRICULTURE("agriculture"), 
	AUTOMOBILE("automobile"), 
	TOURISM("tourism"), 
	FINANCIAL("financial"), 
	MOVIES("movies"), 
	SUBTITLES("subtitles"), 
	SPORTS("sports"), 
	TECHNOLOGY("technology"), 
	LIFESTYLE("lifestyle"), 
	ENTERTAINMENT("entertainment"), 
	PARLIAMENTARY("parliamentary"), 
	ART_AND_CULTURE("art-and-culture"),
	ECONOMY("economy"), 
	HISTORY("history"), 
	PHILOSOPHY("philosophy"),
	RELIGION("religion"), 
	NATIONAL_SECURITY_AND_DEFENCE("national-security-and-defence"),
	LITERATURE("literature"), 
	GEOGRAPHY("geography"),
	GOVERNANCE_AND_POLICY("governance-and-policy"),
	SCIENCE_AND_TECHNOLOGY("science-and-technology");
	  private String value;

	  DomainEnum(String value) {
	    this.value = value;
	  }

	  @Override
	  @JsonValue
	  public String toString() {
	    return String.valueOf(value);
	  }

	  @JsonCreator
	  public static DomainEnum fromValue(String text) {
	    for (DomainEnum b : DomainEnum.values()) {
	      if (String.valueOf(b.value).equals(text)) {
	        return b;
	      }
	    }
	    return null;
	  }
}
