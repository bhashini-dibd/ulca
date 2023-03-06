package org.ulca.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import org.ulca.models.UMSResponse;
import org.ulca.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.ulca.constants.RequestContextConstants.CORRELATION_ID_HEADER_NAME;
import static org.ulca.constants.RequestContextConstants.CORRELATION_ID_KEY;

@Service
public class UserUtils {

    private static final String RETRIEVING_USER_FAILED_MESSAGE = "Retrieving user failed";
    private static final String USER_UNAVAILABLE_MESSAGE = "There's no user associated with this public key, please re-check: {}";

    @Value("${ulca.ums.host}")
    private String umsHost;

    @Value("${ulca.ums.key.search}")
    private String umsSearchWithToken;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        objectMapper = new ObjectMapper();
    }

    /**
     * Fetches user from the UMS via API.
     * @param publicKey
     * @param ctx
     * @return
     */
    public User getUser(String publicKey, RequestContext ctx) {
        String authURL = String.format("%s%s", umsHost, umsSearchWithToken);
        Map<String, String> req_body = new HashMap<>();
        req_body.put("key", publicKey);
        final HttpHeaders headers = new HttpHeaders();
        headers.add(CORRELATION_ID_HEADER_NAME, (String) ctx.get(CORRELATION_ID_KEY));
        final HttpEntity<Object> httpEntity = new HttpEntity<>(req_body, headers);
        try{
            UMSResponse userServiceRes = restTemplate.postForObject(authURL, httpEntity, UMSResponse.class);
            if (null != userServiceRes){
                if(null != userServiceRes.getData()){
                    return userServiceRes.getData();
                }
                else{
                    logger.info(USER_UNAVAILABLE_MESSAGE, publicKey);
                    return null;
                }
            }
            else{
                logger.info("The UMS service is down -- " + authURL);
                return null;
            }
        }catch (Exception e){
            logger.error("URI: " + authURL);
            logger.error(RETRIEVING_USER_FAILED_MESSAGE, e);
            return null;
        }
    }
}
