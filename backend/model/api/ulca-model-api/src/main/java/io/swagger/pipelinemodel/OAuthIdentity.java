package io.swagger.pipelinemodel;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OAuthIdentity
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T06:06:14.793576134Z[GMT]")


public class OAuthIdentity   {
  @JsonProperty("identifier")
  private String identifier = null;

  @JsonProperty("oauthId")
  private String oauthId = null;

  /**
   * user authentication provider
   */
  public enum ProviderEnum {
    CUSTOM("custom"),
    
    GITHUB("github"),
    
    FACEBOOK("facebook"),
    
    INSTAGRAM("instagram"),
    
    GOOGLE("google"),
    
    YAHOO("yahoo");

    private String value;

    ProviderEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ProviderEnum fromValue(String text) {
      for (ProviderEnum b : ProviderEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("provider")
  private ProviderEnum provider = null;

  public OAuthIdentity identifier(String identifier) {
    this.identifier = identifier;
    return this;
  }

  /**
   * system identification for the contributor
   * @return identifier
   **/
  @Schema(description = "system identification for the contributor")
  
    public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public OAuthIdentity oauthId(String oauthId) {
    this.oauthId = oauthId;
    return this;
  }

  /**
   * popular social network identifier, typically identifier returned after auth
   * @return oauthId
   **/
  @Schema(description = "popular social network identifier, typically identifier returned after auth")
  
    public String getOauthId() {
    return oauthId;
  }

  public void setOauthId(String oauthId) {
    this.oauthId = oauthId;
  }

  public OAuthIdentity provider(ProviderEnum provider) {
    this.provider = provider;
    return this;
  }

  /**
   * user authentication provider
   * @return provider
   **/
  @Schema(description = "user authentication provider")
  
    public ProviderEnum getProvider() {
    return provider;
  }

  public void setProvider(ProviderEnum provider) {
    this.provider = provider;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OAuthIdentity oauthIdentity = (OAuthIdentity) o;
    return Objects.equals(this.identifier, oauthIdentity.identifier) &&
        Objects.equals(this.oauthId, oauthIdentity.oauthId) &&
        Objects.equals(this.provider, oauthIdentity.provider);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier, oauthId, provider);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthIdentity {\n");
    
    sb.append("    identifier: ").append(toIndentedString(identifier)).append("\n");
    sb.append("    oauthId: ").append(toIndentedString(oauthId)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
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
