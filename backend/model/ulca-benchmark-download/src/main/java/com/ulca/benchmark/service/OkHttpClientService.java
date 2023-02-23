package com.ulca.benchmark.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.benchmark.request.AsrComputeRequest;
import com.ulca.benchmark.request.AsrComputeResponse;

import io.swagger.model.OCRRequest;
import io.swagger.model.OCRResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OkHttpClientService {

    @Autowired
    WebClient.Builder builder;

    @Value("${asrcomputeurl}")
    private String asrcomputeurl;


    public String ocrCompute(String callBackUrl, OCRRequest request) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        //OkHttpClient client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
        Request httpRequest = new Request.Builder()
                .url(callBackUrl)
                .post(body)
                .build();

        Response httpResponse = client.newCall(httpRequest).execute();
        String responseJsonStr = httpResponse.body().string();
        log.info("logging ocr inference point response" + responseJsonStr);

        OCRResponse response = objectMapper.readValue(responseJsonStr, OCRResponse.class);

        if(response != null && response.getOutput() != null && response.getOutput().size() > 0) {
            return response.getOutput().get(0).getSource();
        }

        return null;
    }

    public String asrComputeInternal(AsrComputeRequest request) {
    	log.info("request ::"+request);
    	

        AsrComputeResponse response = builder.build().post().uri(asrcomputeurl)
                .body(Mono.just(request), AsrComputeRequest.class).retrieve().bodyToMono(AsrComputeResponse.class)
                .block();

        if(response != null && response.getData() != null) {
            return response.getData().getSource();
        }

        return null;
    }


    public Response okHttpClientAsyncPostCall(String requestJson, String url) throws NoSuchAlgorithmException, KeyManagementException, IOException {

        RequestBody body = RequestBody.create(requestJson,MediaType.parse("application/json"));
        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        OkHttpClient newClient = getTrustAllCertsClient();
        return newClient.newCall(httpRequest).execute();



    }
    public String okHttpClientPostCall(String requestJson, String url) throws IOException, KeyManagementException, NoSuchAlgorithmException{

        //OkHttpClient client = new OkHttpClient();

		/*
		 OkHttpClient client = new OkHttpClient.Builder()
		 		 .readTimeout(60, TimeUnit.SECONDS)
			      .build();
		*/


        RequestBody body = RequestBody.create(requestJson,MediaType.parse("application/json"));
        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        OkHttpClient newClient = getTrustAllCertsClient();
        Response httpResponse = newClient.newCall(httpRequest).execute();

        return httpResponse.body().string();
    }

    public static OkHttpClient getTrustAllCertsClient() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());


        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        newBuilder.hostnameVerifier((hostname, session) -> true);
        return newBuilder.readTimeout(60, TimeUnit.SECONDS).build();
    }

}
