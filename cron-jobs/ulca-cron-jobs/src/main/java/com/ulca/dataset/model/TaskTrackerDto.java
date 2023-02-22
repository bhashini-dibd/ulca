package com.ulca.dataset.model;

import java.util.Objects;

public class TaskTrackerDto {
	

	  private String serviceRequestNumber = null;

	  public enum ToolEnum {
	    download("download"),
	    precheck("Pre-Check"),
	    ingest("ingest"),
	    
	    validate("validate"),
	    
	    publish("publish"),
	    
	    search("search"),
	    
	    delete("delete");

	    private String value;

	    ToolEnum(String value) {
	      this.value = value;
	    }

	   
	    public String toString() {
	      return String.valueOf(value);
	    }

	 
	    public static ToolEnum fromValue(String text) {
	      for (ToolEnum b : ToolEnum.values()) {
	        if (String.valueOf(b.value).equals(text)) {
	          return b;
	        }
	      }
	      return null;
	    }
	  }
	
	  private String tool = null;

	  /**
	   * Status of the task
	   */
	  public enum StatusEnum {
	    pending("Pending"),
	    
	    inprogress("In-Progress"),
	    
	    completed("Completed"),
	    
	    failed("Failed"),
		na("N/A");

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
	  
	  
	
	  private String id = null;
	  
	 
	  private String status = null;

	  
	  private String details = null;

	
	  private Object startTime;

	
	  private Object endTime;

	
	  private Object lastModified;

	
	  private Error error = null;
	  
	  
	  public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	  public TaskTrackerDto serviceRequestNumber(String serviceRequestNumber) {
	    this.serviceRequestNumber = serviceRequestNumber;
	    return this;
	  }

	 
	  
	    public String getServiceRequestNumber() {
	    return serviceRequestNumber;
	  }

	  public void setServiceRequestNumber(String serviceRequestNumber) {
	    this.serviceRequestNumber = serviceRequestNumber;
	  }

	  public TaskTrackerDto tool(String tool) {
	    this.tool = tool;
	    return this;
	  }


	  
	    public String getTool() {
	    return tool;
	  }

	  public void setTool(String tool) {
	    this.tool = tool;
	  }

	  public TaskTrackerDto status(String status) {
	    this.status = status;
	    return this;
	  }

	 
	  
	    public String getStatus() {
	    return status;
	  }

	  public void setStatus(String status) {
	    this.status = status;
	  }

	  public TaskTrackerDto details(String details) {
	    this.details = details;
	    return this;
	  }

	  
	    public String getDetails() {
	    return details;
	  }

	  public void setDetails(String details) {
	    this.details = details;
	  }

	  public TaskTrackerDto startTime(Object startTime) {
	    this.startTime = startTime;
	    return this;
	  }

	
	    public Object getStartTime() {
	    return startTime;
	  }

	  public void setStartTime(Object startTime) {
	    this.startTime = startTime;
	  }

	  public TaskTrackerDto endTime(Object endTime) {
	    this.endTime = endTime;
	    return this;
	  }


	    public Object getEndTime() {
	    return endTime;
	  }

	  public void setEndTime(Object endTime) {
	    this.endTime = endTime;
	  }

	  public TaskTrackerDto lastModified(Object lastModified) {
	    this.lastModified = lastModified;
	    return this;
	  }

	
	    public Object getLastModified() {
	    return lastModified;
	  }

	  public void setLastModified(Object lastModified) {
	    this.lastModified = lastModified;
	  }

	  public TaskTrackerDto error(Error error) {
	    this.error = error;
	    return this;
	  }

	  
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
	    TaskTrackerDto taskTracker = (TaskTrackerDto) o;
	    return Objects.equals(this.serviceRequestNumber, taskTracker.serviceRequestNumber) &&
	        Objects.equals(this.tool, taskTracker.tool) &&
	        Objects.equals(this.status, taskTracker.status) &&
	        Objects.equals(this.details, taskTracker.details) &&
	        Objects.equals(this.startTime, taskTracker.startTime) &&
	        Objects.equals(this.endTime, taskTracker.endTime) &&
	        Objects.equals(this.lastModified, taskTracker.lastModified) &&
	        Objects.equals(this.error, taskTracker.error);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(serviceRequestNumber, tool, status, details, startTime, endTime, lastModified, error);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class TaskTracker {\n");
	    
	    sb.append("    serviceRequestNumber: ").append(toIndentedString(serviceRequestNumber)).append("\n");
	    sb.append("    tool: ").append(toIndentedString(tool)).append("\n");
	    sb.append("    status: ").append(toIndentedString(status)).append("\n");
	    sb.append("    details: ").append(toIndentedString(details)).append("\n");
	    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
	    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
	    sb.append("    lastModified: ").append(toIndentedString(lastModified)).append("\n");
	    sb.append("    error: ").append(toIndentedString(error)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  
	  private String toIndentedString(Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }

}
