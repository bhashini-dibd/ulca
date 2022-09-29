package com.ulca.benchmark.model;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Benchmark Process Tracker entity
 */
@Schema(description = "Benchmark Dataset Submit Status entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-08T12:36:29.236Z[GMT]")


@Document(collection = "ulca-bm-submit-status")
public class BenchmarkDatasetSubmitStatus {
	
	@Id
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("userId")
	private String userId = null;
	
	@JsonProperty("benchmarkId")
	private String benchmarkId = null;

	@JsonProperty("serviceRequestNumber")
	@Indexed(unique=true)
	private String serviceRequestNumber = null;

	/**
	 * Type of the service request
	 */
	public enum ServiceRequestTypeEnum {
		
		benchmark("benchmark");

		private String value;

		ServiceRequestTypeEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ServiceRequestTypeEnum fromValue(String text) {
			for (ServiceRequestTypeEnum b : ServiceRequestTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("serviceRequestType")
	
	private ServiceRequestTypeEnum serviceRequestType = null;

	/**
	 * Action being performed on the service request type
	 */
	public enum ServiceRequestActionEnum {
		submit("submit"),

		search("search"),

		abort("abort"),

		delete("delete"),

		update("update");

		private String value;

		ServiceRequestActionEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static ServiceRequestActionEnum fromValue(String text) {
			for (ServiceRequestActionEnum b : ServiceRequestActionEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("serviceRequestAction")
	private ServiceRequestActionEnum serviceRequestAction = null;
	
	
	/**
	 * Status of the process
	 */
	public enum StatusEnum {
		pending("Pending"),

		inprogress("In-Progress"),

		completed("Completed"),

		failed("Failed");

		private String value;

		StatusEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static StatusEnum fromValue(String text) {
			for (StatusEnum b : StatusEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("status")
	private String status = null;

	@JsonProperty("details")
	private Object details = null;

	@JsonProperty("startTime")
	private String startTime = null;

	@JsonProperty("endTime")
	private String endTime = null;

	@JsonProperty("lastModified")
	private String lastModified = null;

	@JsonProperty("error")
	private BenchmarkError error = null;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Id of the user
	 * 
	 * @return userId
	 **/
	@Schema(description = "Id of the user")

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public BenchmarkDatasetSubmitStatus benchmarkId(String benchmarkId) {
		this.benchmarkId = benchmarkId;
		return this;
	}

	/**
	 * Unique identifier of the benchmarkId
	 * 
	 * @return datasetId
	 **/
	@Schema(description = "Unique identifier of the benchmark")

	public String getBenchmarkId() {
		return benchmarkId;
	}

	public void setBenchmarkId(String benchmarkId) {
		this.benchmarkId = benchmarkId;
	}

	public BenchmarkDatasetSubmitStatus serviceRequestNumber(String serviceRequestNumber) {
		this.serviceRequestNumber = serviceRequestNumber;
		return this;
	}

	/**
	 * Unique identifier of the service request
	 * 
	 * @return serviceRequestNumber
	 **/
	@Schema(description = "Unique identifier of the service request")
	
	
	public String getServiceRequestNumber() {
		return serviceRequestNumber;
	}

	public void setServiceRequestNumber(String serviceRequestNumber) {
		this.serviceRequestNumber = serviceRequestNumber;
	}

	public BenchmarkDatasetSubmitStatus serviceRequestType(ServiceRequestTypeEnum serviceRequestType) {
		this.serviceRequestType = serviceRequestType;
		return this;
	}

	/**
	 * Type of the service request
	 * 
	 * @return serviceRequestType
	 **/
	@Schema(description = "Type of the service request")

	public ServiceRequestTypeEnum getServiceRequestType() {
		return serviceRequestType;
	}

	public void setServiceRequestType(ServiceRequestTypeEnum serviceRequestType) {
		this.serviceRequestType = serviceRequestType;
	}

	public BenchmarkDatasetSubmitStatus serviceRequestAction(ServiceRequestActionEnum serviceRequestAction) {
		this.serviceRequestAction = serviceRequestAction;
		return this;
	}

	/**
	 * Action being performed on the service request type
	 * 
	 * @return serviceRequestAction
	 **/
	@Schema(description = "Action being performed on the service request type")

	public ServiceRequestActionEnum getServiceRequestAction() {
		return serviceRequestAction;
	}

	public void setServiceRequestAction(ServiceRequestActionEnum serviceRequestAction) {
		this.serviceRequestAction = serviceRequestAction;
	}
	/**
	 * SearchCriterion of the seach process process
	 * 
	 * @return searchCriterion
	 **/
	
	
	/**
	 * Status of the process
	 * 
	 * @return status
	 **/
	@Schema(description = "Status of the process")

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BenchmarkDatasetSubmitStatus details(Object details) {
		this.details = details;
		return this;
	}

	/**
	 * Details of the curren status of the process
	 * 
	 * @return details
	 **/
	@Schema(description = "Details of the curren status of the process")

	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public BenchmarkDatasetSubmitStatus startTime(String startTime) {
		this.startTime = startTime;
		return this;
	}

	/**
	 * ISO timestamp of the instance of the start of process
	 * 
	 * @return startTime
	 **/
	@Schema(description = "ISO timestamp of the instance of the start of process")

	@Valid
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public BenchmarkDatasetSubmitStatus endTime(String endTime) {
		this.endTime = endTime;
		return this;
	}

	/**
	 * ISO timestamp of the instance of the end of process
	 * 
	 * @return endTime
	 **/
	@Schema(description = "ISO timestamp of the instance of the end of process")

	@Valid
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public BenchmarkDatasetSubmitStatus lastModified(String lastModified) {
		this.lastModified = lastModified;
		return this;
	}

	/**
	 * ISO timestamp of the instance of the end of process
	 * 
	 * @return lastModified
	 **/
	@Schema(description = "ISO timestamp of the instance of the end of process")

	@Valid
	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public BenchmarkDatasetSubmitStatus error(BenchmarkError error) {
		this.error = error;
		return this;
	}

	/**
	 * Get error
	 * 
	 * @return error
	 **/
	@Schema(description = "")

	@Valid
	public BenchmarkError getError() {
		return error;
	}

	public void setError(BenchmarkError error) {
		this.error = error;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BenchmarkDatasetSubmitStatus processTracker = (BenchmarkDatasetSubmitStatus) o;
		return  Objects.equals(this.userId, processTracker.userId)
				&&Objects.equals(this.benchmarkId, processTracker.benchmarkId)
				&& Objects.equals(this.serviceRequestNumber, processTracker.serviceRequestNumber)
				&& Objects.equals(this.serviceRequestType, processTracker.serviceRequestType)
				&& Objects.equals(this.serviceRequestAction, processTracker.serviceRequestAction)
				&& Objects.equals(this.status, processTracker.status)
				&& Objects.equals(this.details, processTracker.details)
				&& Objects.equals(this.startTime, processTracker.startTime)
				&& Objects.equals(this.endTime, processTracker.endTime)
				&& Objects.equals(this.lastModified, processTracker.lastModified)
				&& Objects.equals(this.error, processTracker.error);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId,benchmarkId, serviceRequestNumber, serviceRequestType, serviceRequestAction, status,
				details, startTime, endTime, lastModified, error);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ProcessTracker {\n");
		sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
		sb.append("    benchmarkId: ").append(toIndentedString(benchmarkId)).append("\n");
		sb.append("    serviceRequestNumber: ").append(toIndentedString(serviceRequestNumber)).append("\n");
		sb.append("    serviceRequestType: ").append(toIndentedString(serviceRequestType)).append("\n");
		sb.append("    serviceRequestAction: ").append(toIndentedString(serviceRequestAction)).append("\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("    details: ").append(toIndentedString(details)).append("\n");
		sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
		sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
		sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
		sb.append("    error: ").append(toIndentedString(error)).append("\n");
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
