package com.ulca.dataset.model;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ulca.dataset.request.SearchCriteria;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Process Tracker entity
 */
@Schema(description = "Process Tracker entity")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-08T12:36:29.236Z[GMT]")


@Document(collection = "ulca-pt-processes")
public class ProcessTracker {
	
	@Id
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("userId")
	private String userId = null;

	@JsonProperty("datasetId")
	private String datasetId = null;

	@JsonProperty("serviceRequestNumber")
	@Indexed(unique=true)
	private String serviceRequestNumber = null;

	/**
	 * Type of the service request
	 */
	public enum ServiceRequestTypeEnum {
		dataset("datatset"),

		model("model"),

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
	
	
	@JsonProperty("searchCriteria")
	private SearchCriteria searchCriteria = null;

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
	private Long startTime;

	@JsonProperty("endTime")
	private Long endTime;

	@JsonProperty("lastModified")
	private Long lastModified;

	@JsonProperty("error")
	private Error error = null;

	public ProcessTracker userId(String userId) {
		this.userId = userId;
		return this;
	}

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

	public ProcessTracker datasetId(String datasetId) {
		this.datasetId = datasetId;
		return this;
	}

	/**
	 * Unique identifier of the dataset
	 * 
	 * @return datasetId
	 **/
	@Schema(description = "Unique identifier of the dataset")

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public ProcessTracker serviceRequestNumber(String serviceRequestNumber) {
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

	public ProcessTracker serviceRequestType(ServiceRequestTypeEnum serviceRequestType) {
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

	public ProcessTracker serviceRequestAction(ServiceRequestActionEnum serviceRequestAction) {
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
	
	public SearchCriteria getSearchCriterion() {
		return searchCriteria;
	}

	public void setSearchCriterion(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public ProcessTracker status(String status) {
		this.status = status;
		return this;
	}

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

	public ProcessTracker details(Object details) {
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

	public ProcessTracker startTime(Long startTime) {
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
	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public ProcessTracker endTime(Long endTime) {
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
	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public ProcessTracker lastModified(Long lastModified) {
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
	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	public ProcessTracker error(Error error) {
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
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
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
		ProcessTracker processTracker = (ProcessTracker) o;
		return Objects.equals(this.userId, processTracker.userId)
				&& Objects.equals(this.datasetId, processTracker.datasetId)
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
		return Objects.hash(userId, datasetId, serviceRequestNumber, serviceRequestType, serviceRequestAction, status,
				details, startTime, endTime, lastModified, error);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ProcessTracker {\n");

		sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
		sb.append("    datasetId: ").append(toIndentedString(datasetId)).append("\n");
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
