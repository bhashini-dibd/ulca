package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.ModelProcessingType;
import io.swagger.model.SupportedTasks;
import io.swagger.model.TTSRequest;
import io.swagger.model.TTSResponse;
import io.swagger.model.VoiceTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TTSInference
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-15T09:06:13.583587252Z[GMT]")

public class TTSInference implements OneOfInferenceAPIEndPointSchema {
	/*
	 * @JsonProperty("taskType") private SupportedTasks taskType = null;
	 */

	@JsonProperty("modelProcessingType")
	private ModelProcessingType modelProcessingType = null;

	@JsonProperty("supportedVoices")
	@Valid
	private List<VoiceTypes> supportedVoices = new ArrayList<VoiceTypes>();

	@JsonProperty("request")
	private TTSRequest request = null;

	@JsonProperty("response")
	private TTSResponse response = null;

	/*
	 * public TTSInference taskType(SupportedTasks taskType) { this.taskType =
	 * taskType; return this; }
	 * 
	 *//**
		 * Get taskType
		 * 
		 * @return taskType
		 **//*
			 * @Schema(required = true, description = "")
			 * 
			 * @NotNull
			 * 
			 * @Valid public SupportedTasks getTaskType() { return taskType; }
			 * 
			 * public void setTaskType(SupportedTasks taskType) { this.taskType = taskType;
			 * }
			 */
	public TTSInference modelProcessingType(ModelProcessingType modelProcessingType) {
		this.modelProcessingType = modelProcessingType;
		return this;
	}

	/**
	 * Get modelProcessingType
	 * 
	 * @return modelProcessingType
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public ModelProcessingType getModelProcessingType() {
		return modelProcessingType;
	}

	public void setModelProcessingType(ModelProcessingType modelProcessingType) {
		this.modelProcessingType = modelProcessingType;
	}

	public TTSInference supportedVoices(List<VoiceTypes> supportedVoices) {
		this.supportedVoices = supportedVoices;
		return this;
	}

	public TTSInference addSupportedVoicesItem(VoiceTypes supportedVoicesItem) {
		this.supportedVoices.add(supportedVoicesItem);
		return this;
	}

	/**
	 * list of
	 * 
	 * @return supportedVoices
	 **/
	@Schema(example = "[\"male\",\"female\"]", required = true, description = "list of")
	@NotNull
	@Valid
	public List<VoiceTypes> getSupportedVoices() {
		return supportedVoices;
	}

	public void setSupportedVoices(List<VoiceTypes> supportedVoices) {
		this.supportedVoices = supportedVoices;
	}

	public TTSInference request(TTSRequest request) {
		this.request = request;
		return this;
	}

	/**
	 * Get request
	 * 
	 * @return request
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public TTSRequest getRequest() {
		return request;
	}

	public void setRequest(TTSRequest request) {
		this.request = request;
	}

	public TTSInference response(TTSResponse response) {
		this.response = response;
		return this;
	}

	/**
	 * Get response
	 * 
	 * @return response
	 **/
	@Schema(description = "")

	@Valid
	public TTSResponse getResponse() {
		return response;
	}

	public void setResponse(TTSResponse response) {
		this.response = response;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TTSInference ttSInference = (TTSInference) o;
		return 
			 Objects.equals(this.modelProcessingType, ttSInference.modelProcessingType)
				&& Objects.equals(this.supportedVoices, ttSInference.supportedVoices)
				&& Objects.equals(this.request, ttSInference.request)
				&& Objects.equals(this.response, ttSInference.response);
	}

	@Override
	public int hashCode() {
		return Objects.hash( modelProcessingType, supportedVoices, request, response);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class TTSInference {\n");

		//sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
		sb.append("    modelProcessingType: ").append(toIndentedString(modelProcessingType)).append("\n");
		sb.append("    supportedVoices: ").append(toIndentedString(supportedVoices)).append("\n");
		sb.append("    request: ").append(toIndentedString(request)).append("\n");
		sb.append("    response: ").append(toIndentedString(response)).append("\n");
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
