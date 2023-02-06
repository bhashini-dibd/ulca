package com.ulca.model.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import com.ulca.dataset.service.ProcessTaskTrackerService;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class ModelInferenceEndPointServiceTest {

	

	 @InjectMocks
	 ModelInferenceEndPointService modelInferenceEndPointService;
	
	 
	
	
	
}
