package com.ulca.dataset.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "dataset-kafka-transaction-error-log")
public class DatasetKafkaTransactionErrorLog {

	@Id
	private String id;
	private String serviceRequestNumber;
	private String stage;
	private String data;
	private Integer attempt;
	private boolean failed;
	private boolean success;
	private List<String> errors;
	private String createdOn;
	private String lastModifiedOn;
	
	
}
