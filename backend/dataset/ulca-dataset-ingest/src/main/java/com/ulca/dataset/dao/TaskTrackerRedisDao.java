package com.ulca.dataset.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

	public Map<String, Map<String, String>> findAll() {

		
		Set<String> keyList = redisTemplate.keys(Prefix + "*");
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

		for (String key : keyList) {

			try {
				String serviceRequestNumber = redisTemplate.opsForHash().get(key, "serviceRequestNumber") + "";
				String mode = redisTemplate.opsForHash().get(key, "mode") + "";
				String baseLocation = redisTemplate.opsForHash().get(key, "baseLocation") + "";
				String md5hash = redisTemplate.opsForHash().get(key, "md5hash") + "";
				
				String ingestComplete = redisTemplate.opsForHash().get(key, "ingestComplete") + "";
				String count = redisTemplate.opsForHash().get(key, "count") + "";
				String ingestSuccess = redisTemplate.opsForHash().get(key, "ingestSuccess") + "";
				String ingestError = redisTemplate.opsForHash().get(key, "ingestError") + "";
				String publishSuccess = redisTemplate.opsForHash().get(key, "publishSuccess") + "";
				String publishError = redisTemplate.opsForHash().get(key, "publishError") + "";
				String validateError = redisTemplate.opsForHash().get(key, "validateError") + "";
				String validateSuccess = redisTemplate.opsForHash().get(key, "validateSuccess") + "";

				Map<String, String> innerMap = new HashMap<String, String>();

				innerMap.put("serviceRequestNumber", serviceRequestNumber);
				innerMap.put("mode", mode);
				innerMap.put("baseLocation", baseLocation);
				innerMap.put("md5hash", md5hash);
				
				innerMap.put("ingestComplete", ingestComplete);
				innerMap.put("count", count);
				innerMap.put("ingestSuccess", ingestSuccess);
				innerMap.put("ingestError", ingestError);
				innerMap.put("publishSuccess", publishSuccess);
				innerMap.put("publishError", publishError);
				innerMap.put("validateError", validateError);
				innerMap.put("validateSuccess", validateSuccess);

				map.put(serviceRequestNumber, innerMap);
			} catch( Exception e) {
				log.info("Problem while retrieving data from redis. Please check the redis. key :: " + key);
				log.info("Exception occured :: " + e.getMessage());
			}
			


		}

		return map;

	}

	public void intialize(final String serviceRequestNumber) {

		log.info("******* Entry TaskTrackerRedisDao : intialize ******* ");

		final String key = Prefix + serviceRequestNumber;

		redisTemplate.opsForHash().put(key, "serviceRequestNumber", serviceRequestNumber);
		redisTemplate.opsForHash().put(key, "mode", "real");
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
	public void intialize(final String serviceRequestNumber, DatasetType datasetType) {

		log.info("******* Entry TaskTrackerRedisDao : intialize ******* ");

		final String key = Prefix + serviceRequestNumber;

		redisTemplate.opsForHash().put(key, "serviceRequestNumber", serviceRequestNumber);
		redisTemplate.opsForHash().put(key, "mode", "real");
		redisTemplate.opsForHash().put(key, "datasetType", datasetType.toString());
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
	public void intializePseudoIngest(final String serviceRequestNumber, String baseLocation, String md5hash) {

		log.info("******* Entry TaskTrackerRedisDao : intialize ******* ");

		final String key = Prefix + serviceRequestNumber;

		redisTemplate.opsForHash().put(key, "serviceRequestNumber", serviceRequestNumber);
		redisTemplate.opsForHash().put(key, "baseLocation", baseLocation);
		redisTemplate.opsForHash().put(key, "md5hash", md5hash);
		redisTemplate.opsForHash().put(key, "mode", "pseudo");
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

	public void delete(String serviceRequestNumber) {
		log.info("******* TaskTrackerRedisDao : delete ******* ");
		redisTemplate.delete(Prefix + serviceRequestNumber);
	}
}
