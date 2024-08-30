package com.ulca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
public class CacheService {
	
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveResponse(String requestBody, Object response) throws IOException {
        log.info("saving response to cache");
        String hashKey = hashRequestBody(requestBody);
        log.info("hashkey :: "+hashKey);
        byte[] compressedResponse = compressResponse(response);
        log.info("after compressedResponse ");
        redisTemplate.opsForValue().set(hashKey, compressedResponse, 60, TimeUnit.MINUTES);
    }

    public Object getResponse(String requestBody) throws IOException {
    	log.info("Start get response ");
        String hashKey = hashRequestBody(requestBody);
        log.info("hashKey :: "+hashKey);
        log.info("after get hash");
        String base64EncodedResponse = (String)redisTemplate.opsForValue().get(hashKey);
        byte[] compressedResponse = Base64.getDecoder().decode(base64EncodedResponse);
        log.info("after get Compressed response");
        return decompressResponse(compressedResponse);
    }

    public boolean isCached(String requestBody) {
        String hashKey = hashRequestBody(requestBody);
        return redisTemplate.hasKey(hashKey);
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

    private byte[] compressResponse(Object response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
        }
        return byteArrayOutputStream.toByteArray();
    }

    private Object decompressResponse(byte[] compressedResponse) throws IOException {
    	log.info("Start decompression");
    	log.info("compressedResponse :: "+compressedResponse);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedResponse);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        }
    }
}

