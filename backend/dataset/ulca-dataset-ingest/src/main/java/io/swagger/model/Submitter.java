package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Contributors;
import io.swagger.model.OAuthIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the schema of data file uploaded should adhere to this specified structure.
 */
@Schema(description = "the schema of data file uploaded should adhere to this specified structure.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-22T12:21:00.339Z[GMT]")


public class Submitter   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("oauthId")
  private OAuthIdentity oauthId = null;

  @JsonProperty("aboutMe")
  private String aboutMe = null;

  @JsonProperty("team")
  private Contributors team = null;

  public Submitter name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the Submitter
   * @return name
   **/
  @Schema(required = true, description = "Name of the Submitter")
      @NotNull

  @Size(min=5,max=50)   public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Submitter oauthId(OAuthIdentity oauthId) {
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

  public Submitter aboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  /**
   * Describing the Submitter
   * @return aboutMe
   **/
  @Schema(description = "Describing the Submitter")
  
    public String getAboutMe() {
    return aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public Submitter team(Contributors team) {
    this.team = team;
    return this;
  }

  /**
   * Get team
   * @return team
   **/
  @Schema(description = "")
  
    @Valid
    public Contributors getTeam() {
    return team;
  }

  public void setTeam(Contributors team) {
    this.team = team;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Submitter submitter = (Submitter) o;
    return Objects.equals(this.name, submitter.name) &&
        Objects.equals(this.oauthId, submitter.oauthId) &&
        Objects.equals(this.aboutMe, submitter.aboutMe) &&
        Objects.equals(this.team, submitter.team);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, oauthId, aboutMe, team);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Submitter {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    oauthId: ").append(toIndentedString(oauthId)).append("\n");
    sb.append("    aboutMe: ").append(toIndentedString(aboutMe)).append("\n");
    sb.append("    team: ").append(toIndentedString(team)).append("\n");
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
