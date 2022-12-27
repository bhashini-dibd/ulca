package com.ulca.model.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.swagger.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulca.model.service.exception.ModelComputeException;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
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
public class ModelInferenceEndPointService {

    @Autowired
    WebClient.Builder builder;

    public InferenceAPIEndPoint validateSyncCallBackUrl(InferenceAPIEndPoint inferenceAPIEndPoint)
            throws URISyntaxException, IOException, KeyManagementException, NoSuchAlgorithmException {

        String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
        OneOfInferenceAPIEndPointSchema schema = inferenceAPIEndPoint.getSchema();

        if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationInference")) {
            io.swagger.model.TranslationInference translationInference = (io.swagger.model.TranslationInference) schema;
            TranslationRequest request = translationInference.getRequest();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(request);

            // OkHttpClient client = new OkHttpClient();
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();

            RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
            Request httpRequest = new Request.Builder().url(callBackUrl).post(body).build();

            Response httpResponse = client.newCall(httpRequest).execute();
            // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            // false);
            String responseJsonStr = httpResponse.body().string();

            TranslationResponse response = objectMapper.readValue(responseJsonStr, TranslationResponse.class);
            translationInference.setResponse(response);
            schema = translationInference;

        } else if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.ASRInference")) {


            io.swagger.model.ASRInference asrInference = (io.swagger.model.ASRInference) schema;
            ASRRequest request = asrInference.getRequest();

            ASRResponse response = null;
            SslContext sslContext;
            try {
                sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

                HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

                response = builder.clientConnector(new ReactorClientHttpConnector(httpClient)).build().post()
                        .uri(callBackUrl).body(Mono.just(request), ASRRequest.class).retrieve()
                        .bodyToMono(ASRResponse.class).block();

            } catch (SSLException e) {
                e.printStackTrace();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            log.info("logging asr inference point response" + objectMapper.writeValueAsString(response));
            asrInference.setResponse(response);
            schema = asrInference;


        } else if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.OCRInference")) {
            io.swagger.model.OCRInference ocrInference = (io.swagger.model.OCRInference) schema;
            OCRRequest request = ocrInference.getRequest();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(request);

            // OkHttpClient client = new OkHttpClient();
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).build();

            RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
            Request httpRequest = new Request.Builder().url(callBackUrl).post(body).build();

            Response httpResponse = client.newCall(httpRequest).execute();
            // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            // false);
            String responseJsonStr = httpResponse.body().string();
            OCRResponse response = objectMapper.readValue(responseJsonStr, OCRResponse.class);
            ocrInference.setResponse(response);
            schema = ocrInference;

            log.info("logging ocr inference point response" + responseJsonStr);


        } else if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TTSInference")) {
            io.swagger.model.TTSInference ttsInference = (io.swagger.model.TTSInference) schema;
            TTSRequest request = ttsInference.getRequest();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(request);

            // OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
            Request httpRequest = new Request.Builder().url(callBackUrl).post(body).build();

            OkHttpClient newClient = getTrustAllCertsClient();

            Response httpResponse = newClient.newCall(httpRequest).execute();

            // Response httpResponse = client.newCall(httpRequest).execute();
            // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            // false);
            String responseJsonStr = httpResponse.body().string();
            TTSResponse response = objectMapper.readValue(responseJsonStr, TTSResponse.class);
            ttsInference.setResponse(response);
            schema = ttsInference;

            log.info("logging tts inference point response" + responseJsonStr);

        } else if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TransliterationInference")) {
            io.swagger.model.TransliterationInference transliterationInference = (io.swagger.model.TransliterationInference) schema;
            TransliterationRequest request = transliterationInference.getRequest();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(request);

            // OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
            Request httpRequest = new Request.Builder().url(callBackUrl).post(body).build();

            OkHttpClient newClient = getTrustAllCertsClient();

            Response httpResponse = newClient.newCall(httpRequest).execute();

            String responseJsonStr = httpResponse.body().string();
            TransliterationResponse response = objectMapper.readValue(responseJsonStr, TransliterationResponse.class);
            transliterationInference.setResponse(response);
            schema = transliterationInference;

            log.info("logging TransliterationInference point response" + responseJsonStr);

        } else if (schema.getClass().getName().equalsIgnoreCase("io.swagger.model.TxtLangDetectionInference")) {
            io.swagger.model.TxtLangDetectionInference txtLangDetectionInference = (io.swagger.model.TxtLangDetectionInference) schema;
            TxtLangDetectionRequest request = txtLangDetectionInference.getRequest();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(request);


            RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
            Request httpRequest = new Request.Builder().url(callBackUrl).post(body).build();

            OkHttpClient newClient = getTrustAllCertsClient();

            Response httpResponse = newClient.newCall(httpRequest).execute();

            String responseJsonStr = httpResponse.body().string();
            TxtLangDetectionResponse response = objectMapper.readValue(responseJsonStr, TxtLangDetectionResponse.class);
            txtLangDetectionInference.setResponse(response);
            schema = txtLangDetectionInference;

            log.info("logging TransliterationInference point response" + responseJsonStr);
        }

        inferenceAPIEndPoint.setSchema(schema);
        return inferenceAPIEndPoint;

    }

    public InferenceAPIEndPoint validateAsyncUrl(InferenceAPIEndPoint inferenceAPIEndPoint) throws URISyntaxException,
            IOException, KeyManagementException, NoSuchAlgorithmException, InterruptedException {

        String callBackUrl = inferenceAPIEndPoint.getCallbackUrl();
        AsyncApiDetails asyncApiDetails = inferenceAPIEndPoint.getAsyncApiDetails();
        String pollingUrl = asyncApiDetails.getPollingUrl();
        Integer pollInterval = asyncApiDetails.getPollInterval();

        OneOfAsyncApiDetailsAsyncApiSchema asyncApiSchema = asyncApiDetails.getAsyncApiSchema();
        OneOfAsyncApiDetailsAsyncApiPollingSchema asyncApiPollingSchema = asyncApiDetails.getAsyncApiPollingSchema();

        if (asyncApiSchema.getClass().getName().equalsIgnoreCase("io.swagger.model.TranslationAsyncInference")) {
            io.swagger.model.TranslationAsyncInference translationAsyncInference = (io.swagger.model.TranslationAsyncInference) asyncApiSchema;
            TranslationRequest request = translationAsyncInference.getRequest();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(request);

            Response httpResponse = okHttpClientPostCall(requestJson, callBackUrl);
            // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            // false);
            String responseJsonStr = httpResponse.body().string();

            PollingRequest response = objectMapper.readValue(responseJsonStr, PollingRequest.class);
            translationAsyncInference.setResponse(response);

            while (true) {
                Thread.sleep(pollInterval);
                String pollRequestJson = objectMapper.writeValueAsString(response);
                Response pollHttpResponse = okHttpClientPostCall(pollRequestJson, pollingUrl);
                if (pollHttpResponse.code() == 202) {
                    continue;
                } else if (pollHttpResponse.code() == 200) {

                    String pollResponseJsonStr = pollHttpResponse.body().string();
                    log.info(pollResponseJsonStr);
                    TranslationResponse translationResponse = objectMapper.readValue(pollResponseJsonStr,
                            TranslationResponse.class);
                    io.swagger.model.TranslationAsyncPollingInference translationAsyncPollingInference = (io.swagger.model.TranslationAsyncPollingInference) asyncApiPollingSchema;

                    translationAsyncPollingInference.setRequest(response);
                    translationAsyncPollingInference.setResponse(translationResponse);

                    asyncApiDetails.setAsyncApiPollingSchema(translationAsyncPollingInference);

                    break;

                } else {
                    throw new ModelComputeException("Model Submit Failed", "Model Submit Failed",
                            HttpStatus.INTERNAL_SERVER_ERROR);

                }
            }
            asyncApiDetails.asyncApiSchema(translationAsyncInference);
            inferenceAPIEndPoint.setAsyncApiDetails(asyncApiDetails);

        }
        return inferenceAPIEndPoint;
    }

    public Response okHttpClientPostCall(String requestJson, String url)
            throws IOException, KeyManagementException, NoSuchAlgorithmException {

        // OkHttpClient client = new OkHttpClient();

        /*
         * OkHttpClient client = new OkHttpClient.Builder() .readTimeout(60,
         * TimeUnit.SECONDS) .build();
         */

        RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
        Request httpRequest = new Request.Builder().url(url).post(body).build();

        OkHttpClient newClient = getTrustAllCertsClient();
        Response httpResponse = newClient.newCall(httpRequest).execute();

        return httpResponse;
    }

    public InferenceAPIEndPoint validateCallBackUrl(InferenceAPIEndPoint inferenceAPIEndPoint)
            throws URISyntaxException, IOException, KeyManagementException, NoSuchAlgorithmException,
            InterruptedException {

        if (inferenceAPIEndPoint.isIsSyncApi()!= null && !inferenceAPIEndPoint.isIsSyncApi()) {
        	inferenceAPIEndPoint = validateAsyncUrl(inferenceAPIEndPoint);
            
        } else {
        	inferenceAPIEndPoint = validateSyncCallBackUrl(inferenceAPIEndPoint);
        }

        return inferenceAPIEndPoint;

    }


    public static OkHttpClient getTrustAllCertsClient() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
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
        }};

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        newBuilder.hostnameVerifier((hostname, session) -> true);
        return newBuilder.readTimeout(60, TimeUnit.SECONDS).build();
    }

}
