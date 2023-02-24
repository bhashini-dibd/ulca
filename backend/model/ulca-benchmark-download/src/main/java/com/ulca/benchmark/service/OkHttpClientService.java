package com.ulca.benchmark.service;

import java.io.IOException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mervick.aes_everywhere.Aes256;
import com.ulca.benchmark.request.AsrComputeRequest;
import com.ulca.benchmark.request.AsrComputeResponse;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.swagger.model.ASRRequest;
import io.swagger.model.ASRResponse;
import io.swagger.model.InferenceAPIEndPoint;
import io.swagger.model.InferenceAPIEndPointInferenceApiKey;
import io.swagger.model.OCRRequest;
import io.swagger.model.OCRResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Service
public class OkHttpClientService {

	@Autowired
	WebClient.Builder builder;

	//@Value("${asrcomputeurl}")
	//private String asrcomputeurl;
	

	@Value("${aes.model.apikey.secretkey}")
	private String SECRET_KEY;

	public String ocrCompute(InferenceAPIEndPoint inferenceAPIEndPoint, OCRRequest request) throws IOException {
		
		
		

		ObjectMapper objectMapper = new ObjectMapper();
		String requestJson = objectMapper.writeValueAsString(request);

		// OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient.Builder().readTimeout(120, TimeUnit.SECONDS).build();

		RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
		//Request httpRequest = new Request.Builder().url(callBackUrl).post(body).build();
		Request httpRequest =checkInferenceApiKeyValue(inferenceAPIEndPoint,body);

		Response httpResponse = client.newCall(httpRequest).execute();
		String responseJsonStr = httpResponse.body().string();
		log.info("logging ocr inference point response" + responseJsonStr);

		OCRResponse response = objectMapper.readValue(responseJsonStr, OCRResponse.class);

		if (response != null && response.getOutput() != null && response.getOutput().size() > 0) {
			return response.getOutput().get(0).getSource();
		}

		return null;
	}

	/*
	 * public String asrComputeInternal(AsrComputeRequest request) {
	 * log.info("request ::" + request); String requestJson = null; ObjectMapper
	 * objectMapper = new ObjectMapper(); try { requestJson =
	 * objectMapper.writeValueAsString(request); } catch (JsonProcessingException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); }
	 * log.info("requestJson :: " + requestJson); AsrComputeResponse response =
	 * builder.build().post().uri(asrcomputeurl) .body(Mono.just(requestJson),
	 * AsrComputeRequest.class).retrieve().bodyToMono(AsrComputeResponse.class)
	 * .block();
	 * 
	 * if (response != null && response.getData() != null) { return
	 * response.getData().getSource(); }
	 * 
	 * return null; }
	 */

	public Response okHttpClientAsyncPostCall(String requestJson, String url)
			throws NoSuchAlgorithmException, KeyManagementException, IOException {

		RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
		Request httpRequest = new Request.Builder().url(url).post(body).build();

		OkHttpClient newClient = getTrustAllCertsClient();
		return newClient.newCall(httpRequest).execute();

	}

	public String okHttpClientPostCall(String requestJson, InferenceAPIEndPoint inferenceAPIEndPoint)
			throws IOException, KeyManagementException, NoSuchAlgorithmException {

		// OkHttpClient client = new OkHttpClient();

		/*
		 * OkHttpClient client = new OkHttpClient.Builder() .readTimeout(60,
		 * TimeUnit.SECONDS) .build();
		 */

		RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
		//Request httpRequest = new Request.Builder().url(url).post(body).build();
		Request httpRequest =checkInferenceApiKeyValue(inferenceAPIEndPoint,body);
		OkHttpClient newClient = getTrustAllCertsClient();
		Response httpResponse = newClient.newCall(httpRequest).execute();

		return httpResponse.body().string();
	}

	public static OkHttpClient getTrustAllCertsClient() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}
		} };

		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

		OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
		newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
		newBuilder.hostnameVerifier((hostname, session) -> true);
		return newBuilder.readTimeout(60, TimeUnit.SECONDS).build();
	}

	public String asrCompute(InferenceAPIEndPoint inferenceAPIEndPoint, ASRRequest request) throws IOException {

		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();

		ASRResponse response = null;
		SslContext sslContext;
		try {
			sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

			HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

			
			if (inferenceAPIEndPoint.getInferenceApiKey() != null) {
				InferenceAPIEndPointInferenceApiKey inferenceAPIEndPointInferenceApiKey = inferenceAPIEndPoint
						.getInferenceApiKey();

				if (inferenceAPIEndPointInferenceApiKey.getValue() != null) {

					String encryptedInferenceApiKeyName = inferenceAPIEndPointInferenceApiKey.getName();
					String encryptedInferenceApiKeyValue = inferenceAPIEndPointInferenceApiKey.getValue();
					log.info("encryptedInferenceApiKeyName : " + encryptedInferenceApiKeyName);
					log.info("encryptedInferenceApiKeyValue : " + encryptedInferenceApiKeyValue);
					log.info("SECRET_KEY ::"+SECRET_KEY);

					
					
					
					String originalInferenceApiKeyName=null ;

					String originalInferenceApiKeyValue=null;
					try {
						originalInferenceApiKeyValue = Aes256.decrypt(encryptedInferenceApiKeyValue, SECRET_KEY);
						originalInferenceApiKeyName = Aes256.decrypt(encryptedInferenceApiKeyName, SECRET_KEY);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					log.info("originalInferenceApiKeyName : "+originalInferenceApiKeyName);
					log.info("originalInferenceApiKeyValue : "+originalInferenceApiKeyValue);
					
					
					response = builder.clientConnector(new ReactorClientHttpConnector(httpClient)).build().post()
							.uri(callBackUrl).header(originalInferenceApiKeyName, originalInferenceApiKeyValue)
							.body(Mono.just(request), ASRRequest.class).retrieve().bodyToMono(ASRResponse.class)
							.block();
					log.info("response : " + response);
				}
			} else {
				response = builder.clientConnector(new ReactorClientHttpConnector(httpClient)).build().post()
						.uri(callBackUrl).body(Mono.just(request), ASRRequest.class).retrieve()
						.bodyToMono(ASRResponse.class).block();
				log.info("response : " + response);

			}

		} catch (SSLException e) {
			e.printStackTrace();
		}
		
	
           
		 log.info("asrResponse :: "+response);
		 
		if (response != null && response.getOutput() != null && response.getOutput().size() > 0) {
			return response.getOutput().get(0).getSource();
		}

		return null;
	}

	public  Request checkInferenceApiKeyValue(InferenceAPIEndPoint inferenceAPIEndPoint, RequestBody body) {
		Request httpRequest = null;
		String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();

		if (inferenceAPIEndPoint.getInferenceApiKey() != null) {

			InferenceAPIEndPointInferenceApiKey inferenceAPIEndPointInferenceApiKey = inferenceAPIEndPoint
					.getInferenceApiKey();
			log.info("callBackUrl : " + callBackUrl);
			if (inferenceAPIEndPointInferenceApiKey.getValue() != null) {

				String encryptedInferenceApiKeyName = inferenceAPIEndPointInferenceApiKey.getName();
				String encryptedInferenceApiKeyValue = inferenceAPIEndPointInferenceApiKey.getValue();
				log.info("encryptedInferenceApiKeyName : " + encryptedInferenceApiKeyName);
				log.info("encryptedInferenceApiKeyValue : " + encryptedInferenceApiKeyValue);
				log.info("SECRET_KEY ::"+SECRET_KEY);
				
				
				
				String originalInferenceApiKeyName=null ;

				String originalInferenceApiKeyValue=null;
				try {
					originalInferenceApiKeyValue = Aes256.decrypt(encryptedInferenceApiKeyValue, SECRET_KEY);
					originalInferenceApiKeyName = Aes256.decrypt(encryptedInferenceApiKeyName, SECRET_KEY);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				log.info("originalInferenceApiKeyName : "+originalInferenceApiKeyName);
				log.info("originalInferenceApiKeyValue : "+originalInferenceApiKeyValue);
				
				
				
				
				
				
				
				
				
				
				httpRequest = new Request.Builder().url(callBackUrl)
						.addHeader(originalInferenceApiKeyName, originalInferenceApiKeyValue).post(body).build();
				log.info("httpRequest : " + httpRequest.toString());

			}
		} else {
			httpRequest = new Request.Builder().url(callBackUrl).post(body).build();
			log.info("httpRequest : " + httpRequest.toString());

		}

		return httpRequest;
	}

}
