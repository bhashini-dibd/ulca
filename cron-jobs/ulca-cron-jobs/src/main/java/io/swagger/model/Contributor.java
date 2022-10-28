package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Contributor
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-02T06:46:17.068Z[GMT]")


public class Contributor   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("oauthId")
  private OAuthIdentity oauthId = null;

  @JsonProperty("aboutMe")
  private String aboutMe = null;

  public Contributor name(String name) {
    this.name = name;
    return this;
  }

  /**
   * human name of the contributor
   * @return name
   **/
  @Schema(required = true, description = "human name of the contributor")
      @NotNull

  @Size(min=5,max=50)   public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Contributor oauthId(OAuthIdentity oauthId) {
    this.oauthId = oauthId;
    return this;
  }

  /**
   * Get oauthId
   * @return oauthId
   **/
  @Schema(description = "")
  
    @Valid
    public OAuthIdentity getOauthId() {
    return oauthId;
  }

  public void setOauthId(OAuthIdentity oauthId) {
    this.oauthId = oauthId;
  }

  public Contributor aboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  /**
   * describing the contributor
   * @return aboutMe
   **/
  @Schema(description = "describing the contributor")
  
    public String getAboutMe() {
    return aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Contributor contributor = (Contributor) o;
    return Objects.equals(this.name, contributor.name) &&
        Objects.equals(this.oauthId, contributor.oauthId) &&
        Objects.equals(this.aboutMe, contributor.aboutMe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, oauthId, aboutMe);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Contributor {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    oauthId: ").append(toIndentedString(oauthId)).append("\n");
    sb.append("    aboutMe: ").append(toIndentedString(aboutMe)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
