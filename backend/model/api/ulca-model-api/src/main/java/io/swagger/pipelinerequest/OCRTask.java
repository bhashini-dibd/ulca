package io.swagger.pipelinerequest;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.SupportedTasks;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OCRTask
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-03-27T08:49:40.963303725Z[GMT]")

public class OCRTask implements PipelineTask {
	@JsonProperty("taskType")
	private String taskType = "ocr";

	@JsonProperty("config")
	private OCRRequestConfig config = null;

	public OCRTask taskType(String taskType) {
		this.taskType = taskType;
		return this;
	}

	/**
	 * Get taskType
	 * 
	 * @return taskType
	 **/
	@Schema(required = true, description = "")
	@NotNull

	@Valid
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public OCRTask config(OCRRequestConfig config) {
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
	public OCRRequestConfig getConfig() {
		return config;
	}

	public void setConfig(OCRRequestConfig config) {
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
		OCRTask ocRTask = (OCRTask) o;
		return Objects.equals(this.taskType, ocRTask.taskType) && Objects.equals(this.config, ocRTask.config);
	}

	@Override
	public int hashCode() {
		return Objects.hash(taskType, config);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class OCRTask {\n");

		sb.append("    taskType: ").append(toIndentedString(taskType)).append("\n");
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
