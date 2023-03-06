package com.ulca.dataset.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

				String serviceRequestNumber = redisTemplate.opsForHash().hasKey(key, "serviceRequestNumber")
						? String.valueOf(redisTemplate.opsForHash().get(key, "serviceRequestNumber"))
						: null;
				String mode = redisTemplate.opsForHash().get(key, "mode") + "";
				String datasetName = redisTemplate.opsForHash().get(key, "datasetName") + "";
				String datasetType = redisTemplate.opsForHash().get(key, "datasetType") + "";
				String datasetId = redisTemplate.opsForHash().get(key, "datasetId") + "";
				String userId = redisTemplate.opsForHash().get(key, "userId") + "";
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

				String publishSuccessSeconds = redisTemplate.opsForHash().hasKey(key, "publishSuccessSeconds")
						? String.valueOf(redisTemplate.opsForHash().get(key, "publishSuccessSeconds"))
						: null;
				String publishErrorSeconds = redisTemplate.opsForHash().hasKey(key, "publishErrorSeconds")
						? String.valueOf(redisTemplate.opsForHash().get(key, "publishErrorSeconds"))
						: null;
				String validateSuccessSeconds = redisTemplate.opsForHash().hasKey(key, "validateSuccessSeconds")
						? String.valueOf(redisTemplate.opsForHash().get(key, "validateSuccessSeconds"))
						: null;
				String validateErrorSeconds = redisTemplate.opsForHash().hasKey(key, "validateErrorSeconds")
						? String.valueOf(redisTemplate.opsForHash().get(key, "validateErrorSeconds"))
						: null;

				Map<String, String> innerMap = new HashMap<String, String>();

				innerMap.put("serviceRequestNumber", serviceRequestNumber);
				innerMap.put("mode", mode);
				innerMap.put("datasetName", datasetName);
				innerMap.put("datasetType", datasetType);
				innerMap.put("datasetId", datasetId);
				innerMap.put("userId", userId);
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

				if (publishSuccessSeconds != null) {
					innerMap.put("publishSuccessSeconds", publishSuccessSeconds);
				}
				if (publishErrorSeconds != null) {
					innerMap.put("publishErrorSeconds", publishErrorSeconds);
				}
				if (validateSuccessSeconds != null) {
					innerMap.put("validateSuccessSeconds", validateSuccessSeconds);
				}
				if (validateErrorSeconds != null) {
					innerMap.put("validateErrorSeconds", validateErrorSeconds);
				}

				map.put(serviceRequestNumber, innerMap);
			} catch (Exception e) {
				log.info("Problem while retrieving data from redis. Please check the redis. key :: " + key);
				log.info("Exception occured :: " + e.getMessage());
			}

		}

		return map;

	}

	public void delete(String serviceRequestNumber) {
		log.info("******* TaskTrackerRedisDao : delete ******* ");
		redisTemplate.delete(Prefix + serviceRequestNumber);
	}
}
