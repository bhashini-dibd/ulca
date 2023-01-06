package com.ulca.model.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "model-health-status")
public class ModelHealthStatus {

	@Id
	String modelId;
	String modelName;
	String taskType;



	Boolean isSyncApi;
	String callbackUrl;
	String pollingUrl;
	String status;

	Long lastStatusUpdate;
	Long nextStatusUpdateTiming;
	
	
}

