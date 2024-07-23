package com.ulca;

import okhttp3.OkHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OkHttpClientConfig {

	/*
	 * @Value("${client.ssl.keystore}") private String keystore;
	 * 
	 * @Value("${client.ssl.keystore-password}") private String keystorePassword;
	 */
    
    @Value("${client.ssl.truststore}")
    private String truststore;

    @Value("${client.ssl.truststore-password}")
    private String truststorePassword;
    
  

    
    
	/*
	 * public OkHttpClient getSslOkHttpClient() throws Exception {
	 * log.info("keystore file path :: "+keystore);
	 * log.info("keystorePassword ::"+keystorePassword);
	 * log.info("truststore file path :: "+truststore);
	 * log.info("truststorePassword :: "+truststorePassword);
	 * 
	 * // Load the keystore containing the client certificate KeyStore keyStore =
	 * KeyStore.getInstance("PKCS12"); try (InputStream in =
	 * getResourceAsStream(keystore)) { if (in == null) { throw new
	 * IllegalArgumentException("Keystore file not found: " + keystore); }
	 * keyStore.load(in, keystorePassword.toCharArray()); }
	 * 
	 * // Initialize KeyManagerFactory with the client keystore KeyManagerFactory
	 * kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	 * kmf.init(keyStore, keystorePassword.toCharArray());
	 * 
	 * // Load the trust store KeyStore trustStore =
	 * KeyStore.getInstance(KeyStore.getDefaultType()); try (InputStream in =
	 * getResourceAsStream(truststore)) { if (in == null) { throw new
	 * IllegalArgumentException("Truststore file not found: " + truststore); }
	 * trustStore.load(in, truststorePassword.toCharArray()); }
	 * 
	 * // Initialize TrustManagerFactory with the trust store TrustManagerFactory
	 * tmf =
	 * TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	 * tmf.init(trustStore);
	 * 
	 * // Initialize SSLContext SSLContext sslContext =
	 * SSLContext.getInstance("SSL"); sslContext.init(kmf.getKeyManagers(),
	 * tmf.getTrustManagers(), new SecureRandom());
	 * 
	 * return new OkHttpClient.Builder()
	 * .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager)
	 * tmf.getTrustManagers()[0]) .build(); }
	 * 
	 * private InputStream getResourceAsStream(String resourcePath) { if
	 * (resourcePath.startsWith("classpath:")) { resourcePath =
	 * resourcePath.substring("classpath:".length()); } InputStream in =
	 * Thread.currentThread().getContextClassLoader().getResourceAsStream(
	 * resourcePath); if (in == null) { System.err.println("Resource not found: " +
	 * resourcePath); } else { System.out.println("Resource found: " +
	 * resourcePath); } return in; }
	 */
    
    public OkHttpClient getSslOkHttpClient() throws Exception {
    	
    	log.info("truststore :: "+truststore);
    	log.info("truststorePassword :: "+truststorePassword);
        // Load the trust store
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (InputStream trustStoreStream = getResourceAsStream(truststore)) {
            trustStore.load(trustStoreStream, truststorePassword.toCharArray());
        }

        // Initialize the TrustManagerFactory
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // Create an SSLContext with the trust managers
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Create and return the OkHttpClient with the custom SSL socket factory and timeouts
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
    }
    private InputStream getResourceAsStream(String resourcePath) {
        if (resourcePath.startsWith("classpath:")) {
            resourcePath = resourcePath.substring("classpath:".length());
        }
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
        if (in == null) {
            System.err.println("Resource not found: " + resourcePath);
        } else {
            System.out.println("Resource found: " + resourcePath);
        }
        return in;
    }
}

