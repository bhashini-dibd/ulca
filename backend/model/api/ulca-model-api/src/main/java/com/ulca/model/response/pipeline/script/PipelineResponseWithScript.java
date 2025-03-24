package com.ulca.model.response.pipeline.script;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.pipelinerequest.PipelineInferenceAPIEndPoint;
import io.swagger.pipelinerequest.TaskSchemaList;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PipelineResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-06-02T05:23:09.392636488Z[GMT]")

public class PipelineResponseWithScript implements Serializable {
	@JsonProperty("languages")
	private PipelineResponseLanguagesListWithScript languages = null;

	@JsonProperty("pipelineResponseConfig")
	private TaskSchemaList pipelineResponseConfig = null;

	@JsonProperty("feedbackUrl")
	private String feedbackUrl = null;

	@JsonProperty("pipelineInferenceAPIEndPoint")
	private PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint = null;

	@JsonProperty("pipelineInferenceSocketEndPoint")
	private PipelineInferenceAPIEndPoint pipelineInferenceSocketEndPoint = null;

	public PipelineResponseWithScript languages(PipelineResponseLanguagesListWithScript languages) {
		this.languages = languages;
		return this;
	}

	/**
	 * Get languages
	 * 
	 * @return languages
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public PipelineResponseLanguagesListWithScript getLanguages() {
		return languages;
	}

	public void setLanguages(PipelineResponseLanguagesListWithScript languages) {
		this.languages = languages;
	}

	public PipelineResponseWithScript pipelineResponseConfig(TaskSchemaList pipelineResponseConfig) {
		this.pipelineResponseConfig = pipelineResponseConfig;
		return this;
	}

	/**
	 * Get pipelineResponseConfig
	 * 
	 * @return pipelineResponseConfig
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public TaskSchemaList getPipelineResponseConfig() {
		return pipelineResponseConfig;
	}

	public void setPipelineResponseConfig(TaskSchemaList pipelineResponseConfig) {
		this.pipelineResponseConfig = pipelineResponseConfig;
	}

	public PipelineResponseWithScript feedbackUrl(String feedbackUrl) {
		this.feedbackUrl = feedbackUrl;
		return this;
	}

	/**
	 * URL to give feedback regarding pipeline
	 * 
	 * @return feedbackUrl
	 **/
	@Schema(description = "URL to give feedback regarding pipeline")

	public String getFeedbackUrl() {
		return feedbackUrl;
	}

	public void setFeedbackUrl(String feedbackUrl) {
		this.feedbackUrl = feedbackUrl;
	}

	public PipelineResponseWithScript pipelineInferenceAPIEndPoint(
			PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint) {
		this.pipelineInferenceAPIEndPoint = pipelineInferenceAPIEndPoint;
		return this;
	}

	/**
	 * Get pipelineInferenceAPIEndPoint
	 * 
	 * @return pipelineInferenceAPIEndPoint
	 **/
	@Schema(description = "")

	@Valid
	public PipelineInferenceAPIEndPoint getPipelineInferenceAPIEndPoint() {
		return pipelineInferenceAPIEndPoint;
	}

	public void setPipelineInferenceAPIEndPoint(PipelineInferenceAPIEndPoint pipelineInferenceAPIEndPoint) {
		this.pipelineInferenceAPIEndPoint = pipelineInferenceAPIEndPoint;
	}

	public PipelineResponseWithScript pipelineInferenceSocketEndPoint(
			PipelineInferenceAPIEndPoint pipelineInferenceSocketEndPoint) {
		this.pipelineInferenceSocketEndPoint = pipelineInferenceSocketEndPoint;
		return this;
	}

	/**
	 * Get pipelineInferenceSocketEndPoint
	 * 
	 * @return pipelineInferenceSocketEndPoint
	 **/
	@Schema(description = "")

	@Valid
	public PipelineInferenceAPIEndPoint getPipelineInferenceSocketEndPoint() {
		return pipelineInferenceSocketEndPoint;
	}

	public void setPipelineInferenceSocketEndPoint(PipelineInferenceAPIEndPoint pipelineInferenceSocketEndPoint) {
		this.pipelineInferenceSocketEndPoint = pipelineInferenceSocketEndPoint;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PipelineResponseWithScript pipelineResponse = (PipelineResponseWithScript) o;
		return Objects.equals(this.languages, pipelineResponse.languages)
				&& Objects.equals(this.pipelineResponseConfig, pipelineResponse.pipelineResponseConfig)
				&& Objects.equals(this.feedbackUrl, pipelineResponse.feedbackUrl)
				&& Objects.equals(this.pipelineInferenceAPIEndPoint, pipelineResponse.pipelineInferenceAPIEndPoint)
				&& Objects.equals(this.pipelineInferenceSocketEndPoint,
						pipelineResponse.pipelineInferenceSocketEndPoint);
	}

	@Override
	public int hashCode() {
		return Objects.hash(languages, pipelineResponseConfig, feedbackUrl, pipelineInferenceAPIEndPoint,
				pipelineInferenceSocketEndPoint);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PipelineResponse {\n");

		sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
		sb.append("    pipelineResponseConfig: ").append(toIndentedString(pipelineResponseConfig)).append("\n");
		sb.append("    feedbackUrl: ").append(toIndentedString(feedbackUrl)).append("\n");
		sb.append("    pipelineInferenceAPIEndPoint: ").append(toIndentedString(pipelineInferenceAPIEndPoint))
				.append("\n");
		sb.append("    pipelineInferenceSocketEndPoint: ").append(toIndentedString(pipelineInferenceSocketEndPoint))
				.append("\n");
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
