package com.ulca.dataset.kakfa;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.model.ParallelDatasetParamsSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParamsSchemaValidator {
	
	
	public ParallelDatasetParamsSchema validateParamsSchema(String filePath) {
		
		log.info("************ Entry ParamsSchemaValidator :: validateParamsSchema *********");
		log.info("validing file :: against params schema");
		log.info(filePath);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDefaultTyping(null);

	    //JSON file to Java object
		try {
			
			ParallelDatasetParamsSchema obj = mapper.readValue(new File(filePath), ParallelDatasetParamsSchema.class);
			
			log.info("params validation success ");
			if(obj == null) {
				log.info("ParallelDatasetParamsSchema not created");
			}
			
			return obj;
		} catch (IOException e) {
			
			log.info("params validation failed ");
			e.printStackTrace();
		}
		return null;
	}

}
