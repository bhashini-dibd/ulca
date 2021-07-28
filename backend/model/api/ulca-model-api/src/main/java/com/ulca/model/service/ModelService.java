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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.response.ModelListByUserIdResponse;
import com.ulca.model.response.ModelSearchResponse;
import com.ulca.model.response.UploadModelResponse;

import io.swagger.model.LanguagePair;
import io.swagger.model.LanguagePair.SourceLanguageEnum;
import io.swagger.model.LanguagePair.TargetLanguageEnum;
import io.swagger.model.LanguagePairs;
import io.swagger.model.Model;
import io.swagger.model.ModelTask;
import io.swagger.model.ModelTask.TypeEnum;
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

	public ModelListByUserIdResponse modelListByUserId(String userId, Integer startPage, Integer endPage) {
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
		
		
		return new ModelListByUserIdResponse("Model list by UserId", list, list.size());
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
	
	public UploadModelResponse uploadModel(MultipartFile file, String modelName, String userId) throws Exception {
		
			String modelFilePath = storeModelFile(file);
			
			Model modelObj = getModel(modelFilePath);
			modelObj.setName(modelName);
			modelObj.setSubmitterId(userId);
			if(modelObj != null) {
				modelDao.save(modelObj);
				
			}
			
			return new UploadModelResponse("Model Saved Successfully", modelObj);
			
		
		
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

	public ModelSearchResponse searchModel( ModelSearchRequest request) {
		
		Model model = new Model();
		if(!request.getTask().isBlank()) {
			ModelTask modelTask = new ModelTask();
			modelTask.setType(TypeEnum.fromValue(request.getTask()));
			model.setTask(modelTask);
		}
		if(!request.getSourceLanguage().isBlank()) {
			LanguagePairs lprs = new LanguagePairs();
			LanguagePair lp = new LanguagePair();
			lp.setSourceLanguage(SourceLanguageEnum.fromValue(request.getSourceLanguage()));
			
			if(!request.getTargetLanguage().isBlank()) {
				lp.setTargetLanguage(TargetLanguageEnum.fromValue(request.getTargetLanguage()));
			}
			lprs.add(lp);
			model.setLanguages(lprs);
		}
		
		Example<Model> example = Example.of(model);
		List<Model> list = modelDao.findAll(example);
		
		return new ModelSearchResponse("Model Search Result", list, list.size());
		

	}

}
