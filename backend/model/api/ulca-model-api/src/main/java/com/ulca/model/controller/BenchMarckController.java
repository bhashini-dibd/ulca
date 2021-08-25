package com.ulca.model.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ulca.model.dao.BenchMarkModel;
import com.ulca.model.service.BechMarkService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/ulca/apis/")
public class BenchMarckController {
	private static final Logger log= LoggerFactory.getLogger(BenchMarckController.class);
	@Autowired
	BechMarkService bechmarkService;
	
	@PostMapping("/submit")
	public ResponseEntity<BenchMarkModel> submitmodel(@Valid @RequestBody BenchMarkModel request) {

		log.info("******** Entry BenchMarkController:: Submit *******");
		BenchMarkModel= bechmarkService.Submitmodel(request);
		
		return new ResponseEntity<>(BenchMarkModel,HttpStatus.OK);
	}

}
