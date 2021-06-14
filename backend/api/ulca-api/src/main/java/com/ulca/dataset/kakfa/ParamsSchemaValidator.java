package com.ulca.dataset.kakfa;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.model.ParallelDatasetParamsSchema;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ParamsSchemaValidator {
	
	
	public ParallelDatasetParamsSchema validateParamsSchema(String filePath) throws JsonParseException, JsonMappingException, IOException {
		
		log.info("************ Entry ParamsSchemaValidator :: validateParamsSchema *********");
		log.info("validing file :: against params schema");
		log.info(filePath);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDefaultTyping(null);

	   
			ParallelDatasetParamsSchema paramsSchema = mapper.readValue(new File(filePath), ParallelDatasetParamsSchema.class);
			
			return paramsSchema;
	}

}
