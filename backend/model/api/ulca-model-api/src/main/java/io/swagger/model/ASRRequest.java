package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ASRConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ASRRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-07-26T10:42:04.802Z[GMT]")


public class ASRRequest   {
  @JsonProperty("audioUri")
  @Valid
  private List<String> audioUri = new ArrayList<String>();

  @JsonProperty("config")
  private ASRConfig config = null;

  public ASRRequest audioUri(List<String> audioUri) {
    this.audioUri = audioUri;
    return this;
  }

  public ASRRequest addAudioUriItem(String audioUriItem) {
    this.audioUri.add(audioUriItem);
    return this;
  }

  /**
   * list of paths on gcp/s3 bucket or https url
   * @return audioUri
   **/
  @Schema(example = "gs://bucket/audio.wav", required = true, description = "list of paths on gcp/s3 bucket or https url")
      @NotNull

    public List<String> getAudioUri() {
    return audioUri;
  }

  public void setAudioUri(List<String> audioUri) {
    this.audioUri = audioUri;
  }

  public ASRRequest config(ASRConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public ASRConfig getConfig() {
    return config;
  }

  public void setConfig(ASRConfig config) {
    this.config = config;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ASRRequest asRRequest = (ASRRequest) o;
    return Objects.equals(this.audioUri, asRRequest.audioUri) &&
        Objects.equals(this.config, asRRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioUri, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ASRRequest {\n");
    
    sb.append("    audioUri: ").append(toIndentedString(audioUri)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
