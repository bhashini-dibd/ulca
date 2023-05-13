package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SearchParams
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-02-11T16:20:36.722064280Z[GMT]")


public class SearchParams   {
  /**
   * Gets or Sets key
   */
  public enum KeyEnum {
    LANGUAGES("languages"),
    
    DOMAIN("domain"),
    
    COLLECTIONMETHOD("collectionMethod"),
    
    COLLECTIONSOURCE("collectionSource");

    private String value;

    KeyEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static KeyEnum fromValue(String text) {
      for (KeyEnum b : KeyEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("key")
  private KeyEnum key = null;

  @JsonProperty("params")
  private OneOfSearchParamsParams params = null;

  public SearchParams key(KeyEnum key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   * @return key
   **/
  @Schema(description = "")
  
    public KeyEnum getKey() {
    return key;
  }

  public void setKey(KeyEnum key) {
    this.key = key;
  }

  public SearchParams params(OneOfSearchParamsParams params) {
    this.params = params;
    return this;
  }

  /**
   * Get params
   * @return params
   **/
  @Schema(description = "")
  
    public OneOfSearchParamsParams getParams() {
    return params;
  }

  public void setParams(OneOfSearchParamsParams params) {
    this.params = params;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchParams searchParams = (SearchParams) o;
    return Objects.equals(this.key, searchParams.key) &&
        Objects.equals(this.params, searchParams.params);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, params);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchParams {\n");
    
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    params: ").append(toIndentedString(params)).append("\n");
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
