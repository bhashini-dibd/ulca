package io.swagger.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * the response for translation. Standard http status codes to be used.
 */
@Schema(description = "the response for translation. Standard http status codes to be used.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-04-10T05:17:35.492966412Z[GMT]")
public class TTSResponse {
    @JsonProperty("audio")
    private AudioFiles audio = null;

    @JsonProperty("config")
    private TTSResponseConfig config = null;

    @JsonProperty("taskType")
    private SupportedTasks taskType = null;

    @JsonProperty("detail")
    private List<Detail> detail = null;

    public TTSResponse audio(AudioFiles audio) {
        this.audio = audio;
        return this;
    }

    /**
     * Get audio
     *
     * @return audio
     **/
    @Schema(required = true, description = "")
    @NotNull
    @Valid
    public AudioFiles getAudio() {
        return audio;
    }

    public void setAudio(AudioFiles audio) {
        this.audio = audio;
    }

    public TTSResponse config(TTSResponseConfig config) {
        this.config = config;
        return this;
    }

    /**
     * Get config
     *
     * @return config
     **/
    @Schema(description = "")
    @Valid
    public TTSResponseConfig getConfig() {
        return config;
    }

    public void setConfig(TTSResponseConfig config) {
        this.config = config;
    }

    public TTSResponse taskType(SupportedTasks taskType) {
        this.taskType = taskType;
        return this;
    }

    /**
     * Get taskType
     *
     * @return taskType
     **/
    @Schema(description = "")
    @Valid
    public SupportedTasks getTaskType() {
        return taskType;
    }

    public void setTaskType(SupportedTasks taskType) {
        this.taskType = taskType;
    }

    public TTSResponse detail(List<Detail> detail) {
        this.detail = detail;
        return this;
    }

    /**
     * Get detail
     *
     * @return detail
     **/
    @Schema(description = "")
    @Valid
    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
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
        return Objects.equals(this.audio, ttSResponse.audio) &&
                Objects.equals(this.config, ttSResponse.config) &&
                Objects.equals(this.taskType, ttSResponse.taskType) &&
                Objects.equals(this.detail, ttSResponse.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(audio, config, taskType, detail);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TTSResponse {\n");

        sb.append("    audio: ").append(toIndentedString(audio)).append("\n");
        sb.append("    config: ").append(toIndentedString(config)).append("\n");
        sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
        sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
