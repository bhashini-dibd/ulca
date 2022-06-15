package com.ulca.dataset.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.swagger.model.DatasetType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskTrackerRedisDao {

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	public static final String Prefix = "ServiceRequestNumber_";


	public void intialize(final String serviceRequestNumber, String datasetName, String userId) {

		log.info("******* Entry TaskTrackerRedisDao : intialize ******* ");

		final String key = Prefix + serviceRequestNumber;

		redisTemplate.opsForHash().put(key, "serviceRequestNumber", serviceRequestNumber);
		redisTemplate.opsForHash().put(key, "mode", "real");
		redisTemplate.opsForHash().put(key, "datasetName", datasetName);
		redisTemplate.opsForHash().put(key, "userId", userId);
		redisTemplate.opsForHash().put(key, "ingestComplete", "0");
		redisTemplate.opsForHash().put(key, "count", "0");
		redisTemplate.opsForHash().put(key, "ingestError", "0");
		redisTemplate.opsForHash().put(key, "ingestSuccess", "0");
		redisTemplate.opsForHash().put(key, "validateError", "0");
		redisTemplate.opsForHash().put(key, "validateSuccess", "0");
		redisTemplate.opsForHash().put(key, "publishError", "0");
		redisTemplate.opsForHash().put(key, "publishSuccess", "0");
		
		log.info("******* Exit TaskTrackerRedisDao : intialize ******* ");

	}
	public void intialize(final String serviceRequestNumber, DatasetType datasetType, String datasetName, String userId) {

		log.info("******* Entry TaskTrackerRedisDao : intialize ******* ");

		final String key = Prefix + serviceRequestNumber;

		redisTemplate.opsForHash().put(key, "serviceRequestNumber", serviceRequestNumber);
		redisTemplate.opsForHash().put(key, "mode", "real");
		redisTemplate.opsForHash().put(key, "datasetType", datasetType.toString());
		redisTemplate.opsForHash().put(key, "datasetName", datasetName);
		redisTemplate.opsForHash().put(key, "userId", userId);
		redisTemplate.opsForHash().put(key, "ingestComplete", "0");
		redisTemplate.opsForHash().put(key, "count", "0");
		redisTemplate.opsForHash().put(key, "ingestError", "0");
		redisTemplate.opsForHash().put(key, "ingestSuccess", "0");
		redisTemplate.opsForHash().put(key, "validateError", "0");
		redisTemplate.opsForHash().put(key, "validateSuccess", "0");
		redisTemplate.opsForHash().put(key, "publishError", "0");
		redisTemplate.opsForHash().put(key, "publishSuccess", "0");
		
		if(datasetType.equals(DatasetType.ASR_CORPUS) || datasetType.equals(DatasetType.ASR_UNLABELED_CORPUS)) {
			redisTemplate.opsForHash().put(key, "validateSuccessSeconds", "0");
			redisTemplate.opsForHash().put(key, "validateErrorSeconds", "0");
			redisTemplate.opsForHash().put(key, "publishSuccessSeconds", "0");
			redisTemplate.opsForHash().put(key, "publishErrorSeconds", "0");
		}
		
		log.info("******* Exit TaskTrackerRedisDao : intialize ******* ");

	}
	public void intializePrecheckIngest(final String serviceRequestNumber, String baseLocation, String md5hash, String datasetType, String datasetName, String datasetId, String userId) {

		log.info("******* Entry TaskTrackerRedisDao : intialize ******* ");

		final String key = Prefix + serviceRequestNumber;

		redisTemplate.opsForHash().put(key, "serviceRequestNumber", serviceRequestNumber);
		redisTemplate.opsForHash().put(key, "baseLocation", baseLocation);
		redisTemplate.opsForHash().put(key, "md5hash", md5hash);
		redisTemplate.opsForHash().put(key, "datasetType", datasetType);
		redisTemplate.opsForHash().put(key, "datasetName", datasetName);
		redisTemplate.opsForHash().put(key, "datasetId", datasetId);
		redisTemplate.opsForHash().put(key, "userId", userId);
		redisTemplate.opsForHash().put(key, "mode", "precheck");
		redisTemplate.opsForHash().put(key, "ingestComplete", "0");
		redisTemplate.opsForHash().put(key, "count", "0");
		redisTemplate.opsForHash().put(key, "ingestError", "0");
		redisTemplate.opsForHash().put(key, "ingestSuccess", "0");
		redisTemplate.opsForHash().put(key, "validateError", "0");
		redisTemplate.opsForHash().put(key, "validateSuccess", "0");
		redisTemplate.opsForHash().put(key, "publishError", "0");
		redisTemplate.opsForHash().put(key, "publishSuccess", "0");
		
		log.info("******* Exit TaskTrackerRedisDao : intialize ******* ");

	}

	public void increment(String serviceRequestNumber, String key) {
		
		redisTemplate.opsForHash().increment(Prefix + serviceRequestNumber, key, 1);

	}

	public void setCountOnIngestComplete(String serviceRequestNumber, int count) {

		redisTemplate.opsForHash().put(Prefix + serviceRequestNumber, "count", String.valueOf(count));
		redisTemplate.opsForHash().put(Prefix + serviceRequestNumber, "ingestComplete", String.valueOf(1));

	}
	public void updateCountOnIngestFailure(String serviceRequestNumber) {

		String ingestError = (String) redisTemplate.opsForHash().get(Prefix + serviceRequestNumber, "ingestError");
		String ingestSuccess = (String) redisTemplate.opsForHash().get(Prefix + serviceRequestNumber, "ingestSuccess");
		
		String count = String.valueOf(Integer.parseInt(ingestSuccess)+Integer.parseInt(ingestError));
		
		redisTemplate.opsForHash().put(Prefix + serviceRequestNumber, "count", count);
		redisTemplate.opsForHash().put(Prefix + serviceRequestNumber, "ingestComplete", String.valueOf(1));

	}

}
