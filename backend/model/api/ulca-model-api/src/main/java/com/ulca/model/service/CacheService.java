package com.ulca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ulca.model.response.pipeline.script.PipelineResponseWithScript;

import io.swagger.pipelinerequest.PipelineResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CacheService {

	@Autowired
	@Qualifier("redisDb1")
	private RedisTemplate<String, PipelineResponse> redisTemplate;

	@Autowired
	@Qualifier("redisDb2")
	private RedisTemplate<String, PipelineResponse> redisTemplateDb2;

	@Autowired
    @Qualifier("redisDb3")
	private RedisTemplate<String, PipelineResponseWithScript> redisTemplateDb3;

	public void saveResponse(String requestBody, PipelineResponse response) throws IOException {
		log.info("saving response to cache");
		String hashKey = hashRequestBody(requestBody);
		log.info("hashkey :: " + hashKey);
		// byte[] compressedResponse = compressResponse(response);
		// log.info("after compressedResponse ");
		// redisTemplate.opsForValue().set(hashKey, compressedResponse, 60,
		// TimeUnit.MINUTES);
		redisTemplate.opsForValue().set(hashKey, response);

	}

	public void saveResponse2(String requestBody, PipelineResponse response) throws IOException {
		log.info("saving response to cache");
		String hashKey = hashRequestBody(requestBody);
		log.info("hashkey :: " + hashKey);
		// byte[] compressedResponse = compressResponse(response);
		// log.info("after compressedResponse ");
		// redisTemplate.opsForValue().set(hashKey, compressedResponse, 60,
		// TimeUnit.MINUTES);
		redisTemplateDb2.opsForValue().set(hashKey, response);

	}
	public void saveResponse3(String requestBody, PipelineResponseWithScript response) throws IOException {
		log.info("saving response to cache");
		String hashKey = hashRequestBody(requestBody);
		log.info("hashkey :: " + hashKey);
		// byte[] compressedResponse = compressResponse(response);
		// log.info("after compressedResponse ");
		// redisTemplate.opsForValue().set(hashKey, compressedResponse, 60,
		// TimeUnit.MINUTES);
		redisTemplateDb3.opsForValue().set(hashKey, response);

	}

	public PipelineResponse getResponse(String requestBody) throws IOException {
		log.info("Start get response ");
		String hashKey = hashRequestBody(requestBody);
		log.info("hashKey :: " + hashKey);
		log.info("after get hash");
		return (PipelineResponse) redisTemplate.opsForValue().get(hashKey);
		// byte[] compressedResponse =
		// Base64.getDecoder().decode(base64EncodedResponse);
		// log.info("after get Compressed response");
		// return decompressResponse(compressedResponse);
	}

	public PipelineResponse getResponse2(String requestBody) throws IOException {
		log.info("Start get response ");
		String hashKey = hashRequestBody(requestBody);
		log.info("hashKey :: " + hashKey);
		log.info("after get hash");
		return (PipelineResponse) redisTemplateDb2.opsForValue().get(hashKey);
		// byte[] compressedResponse =
		// Base64.getDecoder().decode(base64EncodedResponse);
		// log.info("after get Compressed response");
		// return decompressResponse(compressedResponse);
	}
	
	public PipelineResponseWithScript getResponse3(String requestBody) throws IOException {
		log.info("Start get response ");
		String hashKey = hashRequestBody(requestBody);
		log.info("hashKey :: " + hashKey);
		log.info("after get hash");
		return (PipelineResponseWithScript) redisTemplateDb3.opsForValue().get(hashKey);
		// byte[] compressedResponse =
		// Base64.getDecoder().decode(base64EncodedResponse);
		// log.info("after get Compressed response");
		// return decompressResponse(compressedResponse);
	}

	public boolean isCached(String requestBody) {
		String hashKey = hashRequestBody(requestBody);
		return redisTemplate.hasKey(hashKey);
	}

	public boolean isCached2(String requestBody) {
		String hashKey = hashRequestBody(requestBody);
		return redisTemplateDb2.hasKey(hashKey);
	}

	public boolean isCached3(String requestBody) {
		String hashKey = hashRequestBody(requestBody);
		log.info(hashKey);
		return redisTemplateDb3.hasKey(hashKey);
	}

	private String hashRequestBody(String requestBody) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(requestBody.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error hashing request body", e);
		}
	}

}
