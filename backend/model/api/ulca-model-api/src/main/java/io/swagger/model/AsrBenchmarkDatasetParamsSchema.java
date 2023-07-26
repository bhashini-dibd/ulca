package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AudioBitsPerSample;
import io.swagger.model.AudioChannel;
import io.swagger.model.AudioFormat;
import io.swagger.model.BenchmarkDatasetCommonParamsSchema;
import io.swagger.model.Domain;
import io.swagger.model.License;
import io.swagger.model.ModelTask;
import io.swagger.model.Source;
import io.swagger.model.Submitter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AsrBenchmarkDatasetParamsSchema
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-02T03:59:56.369839514Z[GMT]")


public class AsrBenchmarkDatasetParamsSchema extends BenchmarkDatasetCommonParamsSchema implements OneOfDatasetParamsSchemaParams {
  @JsonProperty("format")
  private AudioFormat format = null;

  @JsonProperty("channel")
  private AudioChannel channel = null;

  @JsonProperty("samplingRate")
  private BigDecimal samplingRate = null;

  @JsonProperty("bitsPerSample")
  private AudioBitsPerSample bitsPerSample = null;

  public AsrBenchmarkDatasetParamsSchema format(AudioFormat format) {
    this.format = format;
    return this;
  }

  /**
   * Get format
   * @return format
   **/
  @Schema(description = "")
  
    @Valid
    public AudioFormat getFormat() {
    return format;
  }

  public void setFormat(AudioFormat format) {
    this.format = format;
  }

  public AsrBenchmarkDatasetParamsSchema channel(AudioChannel channel) {
    this.channel = channel;
    return this;
  }

  /**
   * Get channel
   * @return channel
   **/
  @Schema(description = "")
  
    @Valid
    public AudioChannel getChannel() {
    return channel;
  }

  public void setChannel(AudioChannel channel) {
    this.channel = channel;
  }

  public AsrBenchmarkDatasetParamsSchema samplingRate(BigDecimal samplingRate) {
    this.samplingRate = samplingRate;
    return this;
  }

  /**
   * sample rate of the audio file in kHz
   * @return samplingRate
   **/
  @Schema(example = "44", description = "sample rate of the audio file in kHz")
  
    @Valid
    public BigDecimal getSamplingRate() {
    return samplingRate;
  }

  public void setSamplingRate(BigDecimal samplingRate) {
    this.samplingRate = samplingRate;
  }

  public AsrBenchmarkDatasetParamsSchema bitsPerSample(AudioBitsPerSample bitsPerSample) {
    this.bitsPerSample = bitsPerSample;
    return this;
  }

  /**
   * Get bitsPerSample
   * @return bitsPerSample
   **/
  @Schema(description = "")
  
    @Valid
    public AudioBitsPerSample getBitsPerSample() {
    return bitsPerSample;
  }

  public void setBitsPerSample(AudioBitsPerSample bitsPerSample) {
    this.bitsPerSample = bitsPerSample;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AsrBenchmarkDatasetParamsSchema asrBenchmarkDatasetParamsSchema = (AsrBenchmarkDatasetParamsSchema) o;
    return Objects.equals(this.format, asrBenchmarkDatasetParamsSchema.format) &&
        Objects.equals(this.channel, asrBenchmarkDatasetParamsSchema.channel) &&
        Objects.equals(this.samplingRate, asrBenchmarkDatasetParamsSchema.samplingRate) &&
        Objects.equals(this.bitsPerSample, asrBenchmarkDatasetParamsSchema.bitsPerSample) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(format, channel, samplingRate, bitsPerSample, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AsrBenchmarkDatasetParamsSchema {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    format: ").append(toIndentedString(format)).append("\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
    sb.append("    samplingRate: ").append(toIndentedString(samplingRate)).append("\n");
    sb.append("    bitsPerSample: ").append(toIndentedString(bitsPerSample)).append("\n");
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
