package com.ulca.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping(value = "/ulca/apis/v0/cron")
public class CronMonitorController {

	
	@GetMapping("/healthCheck")
	public  ResponseEntity<Object> healthCheck() {
		
		log.info("******** Entry CronController:: healthCheck *******");
		return new ResponseEntity("Success", HttpStatus.OK);
			
	
	}
	
}
