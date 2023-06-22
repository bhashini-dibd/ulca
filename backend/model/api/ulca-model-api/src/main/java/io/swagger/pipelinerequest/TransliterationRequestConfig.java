package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.model.LanguagePair;

/**
 * TransliterationRequestConfig
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-21T11:26:25.131163956Z[GMT]")


public class TransliterationRequestConfig   {
  @JsonProperty("language")
  private LanguagePair language = null;

  public TransliterationRequestConfig language(LanguagePair language) {
    this.language = language;
    return this;
  }

  /**
   * Get language
   * @return language
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public LanguagePair getLanguage() {
    return language;
  }

  public void setLanguage(LanguagePair language) {
    this.language = language;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransliterationRequestConfig transliterationRequestConfig = (TransliterationRequestConfig) o;
    return Objects.equals(this.language, transliterationRequestConfig.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(language);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransliterationRequestConfig {\n");
    
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
