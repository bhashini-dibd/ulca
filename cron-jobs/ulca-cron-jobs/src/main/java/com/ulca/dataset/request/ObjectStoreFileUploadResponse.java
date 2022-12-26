package com.ulca.dataset.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObjectStoreFileUploadResponse {

	private String message;
	private String data;
	private Integer count;
	
}
