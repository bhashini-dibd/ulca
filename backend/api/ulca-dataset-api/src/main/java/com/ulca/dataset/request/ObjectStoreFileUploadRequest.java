package com.ulca.dataset.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
