package com.ulca.model.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ulca.model.request.ModelSearchRequest;
import com.ulca.model.service.ModelService;

import io.swagger.model.Model;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/ulca/apis/v0/model")
public class ModelController {

	@Autowired
	ModelService modelService;

	@PostMapping("/submit")
	public Model submitModel(@Valid @RequestBody Model request) {

		log.info("******** Entry ModelController:: modelSubmit *******");
		return modelService.modelSubmit(request);
	}

	@GetMapping("/listByUserId")
	public List<Model> listByUserId(@RequestParam String userId, @RequestParam(required = false) Integer startPage,
			@RequestParam(required = false) Integer endPage) {
		log.info("******** Entry ModelController:: listByUserId *******");

		return modelService.modelListByUserId(userId, startPage, endPage);
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(required = true) String modelName,@RequestParam(required = true) String userId) throws Exception {
		 modelService.uploadModel(file, modelName, userId);

		return "success";

	}
	
	@PostMapping("/search")
	public List<Model> searchModel(@Valid @RequestBody ModelSearchRequest request) {

		log.info("******** Entry ModelController:: modelSeach *******");
		return modelService.searchModel(request);
	}

}
