package com.ulca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
import java.util.zip.GZIPInputStream;


@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, byte[]> redisTemplate;

    public void saveResponse(String requestBody, Object response) throws IOException {
        String hashKey = hashRequestBody(requestBody);
        byte[] compressedResponse = compressResponse(response);
        redisTemplate.opsForValue().set(hashKey, compressedResponse);
    }

    public Object getResponse(String requestBody) throws IOException {
        String hashKey = hashRequestBody(requestBody);
        byte[] compressedResponse = redisTemplate.opsForValue().get(hashKey);
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

