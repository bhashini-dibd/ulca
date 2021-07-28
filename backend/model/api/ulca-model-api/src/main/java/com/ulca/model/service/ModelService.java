package com.ulca.model.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.ulca.model.dao.ModelDao;

import io.swagger.model.Model;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ModelService {
	
	private int  PAGE_SIZE = 10;
	
	@Autowired
	ModelDao modelDao;
	
	public Model modelSubmit(Model model) {
		
		modelDao.save(model);
		return model;
	}

	public List<Model> modelListByUserId(String userId, Integer startPage, Integer endPage) {
		log.info("******** Entry ModelService:: modelListByUserId *******" );
		List<Model> list = new ArrayList<Model>(); 
		
		if( startPage != null) {
			int startPg = startPage - 1;
			for(int i= startPg; i< endPage; i++) {
				Pageable paging = PageRequest.of(i, PAGE_SIZE);
				Page<Model> modelList = modelDao.findBySubmitterId(userId,paging);
				list.addAll(modelList.toList());
			}
		} else {
			list = modelDao.findBySubmitterId(userId);
		}
		
		
		return list;
	}
	
	
	public String storeModelFile(MultipartFile file) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Paths.get("/tmp/saroj/model")
                    .toAbsolutePath().normalize();
            
            try {
                Files.createDirectories(targetLocation);
            } catch (Exception ex) {
                throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
            }
            Path filePath = targetLocation.resolve(fileName);
            
            log.info("filePath :: " + filePath.toAbsolutePath());
            
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toAbsolutePath().toString();
        } catch (IOException ex) {
            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
	
	public void uploadModel(MultipartFile file, String modelName, String userId) {
		try {
			String modelFilePath = storeModelFile(file);
			
			Model modelObj = getModel(modelFilePath);
			modelObj.setName(modelName);
			modelObj.setSubmitterId(userId);
			if(modelObj != null) {
				modelDao.save(modelObj);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Model getModel(String modelFilePath) {
		
		Model modelObj = null;
		
		
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File(modelFilePath);
			
			try {
				modelObj = objectMapper.readValue(file, Model.class);
				return modelObj;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		
		return modelObj;
		
	}

}
