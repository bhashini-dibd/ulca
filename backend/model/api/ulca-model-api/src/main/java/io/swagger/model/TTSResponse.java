package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ASRConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * the response for translation.  Standard http status codes to be used.
 */
@Schema(description = "the response for translation.  Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-08-02T06:46:17.068Z[GMT]")


public class TTSResponse   {
  @JsonProperty("audioUri")
  private String audioUri = null;

  @JsonProperty("config")
  private ASRConfig config = null;

  public TTSResponse audioUri(String audioUri) {
    this.audioUri = audioUri;
    return this;
  }

  /**
   * path on gcp/s3 bucket or https url
   * @return audioUri
   **/
  @Schema(example = "gs://bucket/audio.wav", required = true, description = "path on gcp/s3 bucket or https url")
      @NotNull

    public String getAudioUri() {
    return audioUri;
  }

  public void setAudioUri(String audioUri) {
    this.audioUri = audioUri;
  }

  public TTSResponse config(ASRConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Get config
   * @return config
   **/
  @Schema(description = "")
  
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
    TTSResponse ttSResponse = (TTSResponse) o;
    return Objects.equals(this.audioUri, ttSResponse.audioUri) &&
        Objects.equals(this.config, ttSResponse.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioUri, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TTSResponse {\n");
    
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
