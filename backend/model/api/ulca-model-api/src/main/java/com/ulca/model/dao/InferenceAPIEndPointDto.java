package com.ulca.model.dao;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.model.AsyncApiDetails;

import io.swagger.model.OneOfInferenceAPIEndPointSchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class InferenceAPIEndPointDto {

	  private String callbackUrl;


	  private OneOfInferenceAPIEndPointSchema schema;

	
	  private Boolean isSyncApi = true;


	  private AsyncApiDetails asyncApiDetails;

	  public InferenceAPIEndPointDto callbackUrl(String callbackUrl) {
	    this.callbackUrl = callbackUrl;
	    return this;
	  }

	

	    public String getCallbackUrl() {
	    return callbackUrl;
	  }

	  public void setCallbackUrl(String callbackUrl) {
	    this.callbackUrl = callbackUrl;
	  }

	 

	 

	  public InferenceAPIEndPointDto schema(OneOfInferenceAPIEndPointSchema schema) {
	    this.schema = schema;
	    return this;
	  }

	

	    public OneOfInferenceAPIEndPointSchema getSchema() {
	    return schema;
	  }

	  public void setSchema(OneOfInferenceAPIEndPointSchema schema) {
	    this.schema = schema;
	  }

	  public InferenceAPIEndPointDto isSyncApi(Boolean isSyncApi) {
	    this.isSyncApi = isSyncApi;
	    return this;
	  }

	  
	    public Boolean isIsSyncApi() {
	    return isSyncApi;
	  }

	  public void setIsSyncApi(Boolean isSyncApi) {
	    this.isSyncApi = isSyncApi;
	  }

	  public InferenceAPIEndPointDto asyncApiDetails(AsyncApiDetails asyncApiDetails) {
	    this.asyncApiDetails = asyncApiDetails;
	    return this;
	  }

	
	    public AsyncApiDetails getAsyncApiDetails() {
	    return asyncApiDetails;
	  }

	  public void setAsyncApiDetails(AsyncApiDetails asyncApiDetails) {
	    this.asyncApiDetails = asyncApiDetails;
	  }


		
		  @Override public boolean equals(java.lang.Object o) { if (this == o) { return
		  true; } if (o == null || getClass() != o.getClass()) { return false; }
		  InferenceAPIEndPointDto inferenceAPIEndPoint = (InferenceAPIEndPointDto) o;
		  return Objects.equals(this.callbackUrl, inferenceAPIEndPoint.callbackUrl) &&
		  
		  Objects.equals(this.schema, inferenceAPIEndPoint.schema) &&
		  Objects.equals(this.isSyncApi, inferenceAPIEndPoint.isSyncApi) &&
		  Objects.equals(this.asyncApiDetails, inferenceAPIEndPoint.asyncApiDetails); }
		 

		
		  @Override public int hashCode() { return Objects.hash(callbackUrl, schema,
		  isSyncApi, asyncApiDetails); }
		 

		
		  @Override public String toString() { StringBuilder sb = new StringBuilder();
		  sb.append("class InferenceAPIEndPoint {\n");
		  
		  sb.append("    callbackUrl: ").append(toIndentedString(callbackUrl)).append(
		  "\n");
		  
		  sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
		  sb.append("    isSyncApi: ").append(toIndentedString(isSyncApi)).append("\n")
		  ;
		  sb.append("    asyncApiDetails: ").append(toIndentedString(asyncApiDetails)).
		  append("\n"); sb.append("}"); return sb.toString(); }
		 

	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
		
		  private String toIndentedString(java.lang.Object o) { if (o == null) { return
		  "null"; } return o.toString().replace("\n", "\n    "); }
		 
}
