package com.ulca.dataset.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObjectStoreFileUploadRequest {

	private String fileLocation;
	private String storageFolder;
	private String fileName;
	
}
