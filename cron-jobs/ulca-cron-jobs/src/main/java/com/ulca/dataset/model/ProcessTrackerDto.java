package com.ulca.dataset.model;


import com.ulca.dataset.request.SearchCriteria;


import java.util.Objects;



public class ProcessTrackerDto {
	
	
	
	private String id = null;

	
	private String userId = null;

	
	private String datasetId = null;

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

	
		public String toString() {
			return String.valueOf(value);
		}

	
		public static ServiceRequestTypeEnum fromValue(String text) {
			for (ServiceRequestTypeEnum b : ServiceRequestTypeEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	
	
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

	
		public String toString() {
			return String.valueOf(value);
		}

		
		public static ServiceRequestActionEnum fromValue(String text) {
			for (ServiceRequestActionEnum b : ServiceRequestActionEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	
	private ServiceRequestActionEnum serviceRequestAction = null;
	
	
	
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

		
		public String toString() {
			return String.valueOf(value);
		}

		
		public static StatusEnum fromValue(String text) {
			for (StatusEnum b : StatusEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}


	private String status = null;

	
	private Object details = null;

	
	private Object startTime;


	private Object endTime;

	
	private Object lastModified;

	
	private Error error = null;

	public ProcessTrackerDto userId(String userId) {
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


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ProcessTrackerDto datasetId(String datasetId) {
		this.datasetId = datasetId;
		return this;
	}

	/**
	 * Unique identifier of the dataset
	 * 
	 * @return datasetId
	 **/
	

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public ProcessTrackerDto serviceRequestNumber(String serviceRequestNumber) {
		this.serviceRequestNumber = serviceRequestNumber;
		return this;
	}

	/**
	 * Unique identifier of the service request
	 * 
	 * @return serviceRequestNumber
	 **/
	
	
	
	public String getServiceRequestNumber() {
		return serviceRequestNumber;
	}

	public void setServiceRequestNumber(String serviceRequestNumber) {
		this.serviceRequestNumber = serviceRequestNumber;
	}

	public ProcessTrackerDto serviceRequestType(ServiceRequestTypeEnum serviceRequestType) {
		this.serviceRequestType = serviceRequestType;
		return this;
	}

	/**
	 * Type of the service request
	 * 
	 * @return serviceRequestType
	 **/


	public ServiceRequestTypeEnum getServiceRequestType() {
		return serviceRequestType;
	}

	public void setServiceRequestType(ServiceRequestTypeEnum serviceRequestType) {
		this.serviceRequestType = serviceRequestType;
	}

	public ProcessTrackerDto serviceRequestAction(ServiceRequestActionEnum serviceRequestAction) {
		this.serviceRequestAction = serviceRequestAction;
		return this;
	}

	/**
	 * Action being performed on the service request type
	 * 
	 * @return serviceRequestAction
	 **/
	

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

	public ProcessTrackerDto status(String status) {
		this.status = status;
		return this;
	}

	/**
	 * Status of the process
	 * 
	 * @return status
	 **/
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ProcessTrackerDto details(Object details) {
		this.details = details;
		return this;
	}

	/**
	 * Details of the curren status of the process
	 * 
	 * @return details
	 **/


	public Object getDetails() {
		return details;
	}

	public void setDetails(Object details) {
		this.details = details;
	}

	public ProcessTrackerDto startTime(Object startTime) {
		this.startTime = startTime;
		return this;
	}

	/**
	 * ISO timestamp of the instance of the start of process
	 * 
	 * @return startTime
	 **/
	


	public Object getStartTime() {
		return startTime;
	}

	public void setStartTime(Object startTime) {
		this.startTime = startTime;
	}

	public ProcessTrackerDto endTime(Object endTime) {
		this.endTime = endTime;
		return this;
	}

	/**
	 * ISO timestamp of the instance of the end of process
	 * 
	 * @return endTime
	 **/
	

	
	public Object getEndTime() {
		return endTime;
	}

	public void setEndTime(Object endTime) {
		this.endTime = endTime;
	}

	public ProcessTrackerDto lastModified(Object lastModified) {
		this.lastModified = lastModified;
		return this;
	}

	/**
	 * ISO timestamp of the instance of the end of process
	 * 
	 * @return lastModified
	 **/
	

	
	public Object getLastModified() {
		return lastModified;
	}

	public void setLastModified(Object lastModified) {
		this.lastModified = lastModified;
	}

	public ProcessTrackerDto error(Error error) {
		this.error = error;
		return this;
	}

	/**
	 * Get error
	 * 
	 * @return error
	 **/



	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProcessTrackerDto processTracker = (ProcessTrackerDto) o;
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
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
