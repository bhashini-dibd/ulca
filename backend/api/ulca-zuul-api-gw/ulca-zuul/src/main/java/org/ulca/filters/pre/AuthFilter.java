package org.ulca.filters.pre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.ulca.cache.ZuulConfigCache;
import org.ulca.models.Action;
import org.ulca.models.User;
import org.ulca.utils.ExceptionUtils;
import org.ulca.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ulca.constants.RequestContextConstants.*;

/**
 * 2nd filter to execute in the request flow.
 * Checks if the auth token is available, throws exception otherwise.
 * for the given auth token checks if there's a valid user in the sysTem, throws exception otherwise.
 * Performs authentication level checks on the request.
 *
 */
@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
    public UserUtils userUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PK_RETRIEVE_FAILURE_MESSAGE = "Couldn't find public key in the request.";
    private static final String SIG_RETRIEVE_FAILURE_MESSAGE = "Couldn't find signature in the request.";
    private static final String PAYLOAD_RETRIEVE_FAILURE_MESSAGE = "Couldn't find payload in the request.";
    private static final String SKIP_AUTH_CHECK = "Auth check skipped - whitelisted endpoint | {}";
    private static final String ROUTING_TO_PROTECTED_ENDPOINT_RESTRICTED_MESSAGE = "Routing to protected endpoint {} restricted - Invalid public key";
    private static final String PROCEED_ROUTING_MESSAGE = "Routing to protected endpoint: {} - authentication check passed!";
    private static final String UNAUTH_USER_MESSAGE = "You don't have access to this resource - authentication check failed.";
    private static final String INVALID_ENDPOINT_MSG = "You're trying to access an invalid/inactive resource";


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    } // Second filter

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        Map<String, String> headerMap = new HashMap<>();
        RequestContext ctx = RequestContext.getCurrentContext();
        List<String> openEndpointsWhitelist = ZuulConfigCache.whiteListEndpoints;
        String uri = getRequestURI();
        if (openEndpointsWhitelist.contains(uri)) {
            setShouldDoAuth(false);
            logger.info(SKIP_AUTH_CHECK, uri);
            ctx.set(REQ_URI, uri);
            ctx.set(ACTION_URI, uri);
            return null;
        }
        if (!isURIValid(uri, ctx)){
            logger.info("Invalid resource: {}", uri);
            ExceptionUtils.raiseCustomException(HttpStatus.NOT_FOUND, INVALID_ENDPOINT_MSG);
        }
        try {
            headerMap = getHeadersFromRequestHeader();
        } catch (Exception e) {
            logger.error(PK_RETRIEVE_FAILURE_MESSAGE, e);
            ExceptionUtils.raiseCustomException(HttpStatus.BAD_REQUEST, PK_RETRIEVE_FAILURE_MESSAGE);
            return null;
        }
        if (headerMap.get("PK") == null) {
            logger.info(PK_RETRIEVE_FAILURE_MESSAGE);
            ExceptionUtils.raiseCustomException(HttpStatus.BAD_REQUEST, PK_RETRIEVE_FAILURE_MESSAGE);
        }
        else if (headerMap.get("SIG") == null) {
            logger.info(SIG_RETRIEVE_FAILURE_MESSAGE);
            ExceptionUtils.raiseCustomException(HttpStatus.BAD_REQUEST, SIG_RETRIEVE_FAILURE_MESSAGE);
        }
        else if (headerMap.get("PAYLOAD") == null){
            logger.info(PAYLOAD_RETRIEVE_FAILURE_MESSAGE);
            ExceptionUtils.raiseCustomException(HttpStatus.BAD_REQUEST, SIG_RETRIEVE_FAILURE_MESSAGE);
        }
        else {
            String publicKey = headerMap.get("PK");
            String sig = headerMap.get("SIG");
            String payload = headerMap.get("PAYLOAD");
            ctx.set(PUBLIC_KEY, publicKey);
            ctx.set(SIG_KEY, sig);
            ctx.set(PAYLOAD_KEY, payload);
            User user = verifyAuthenticity(ctx, publicKey);
            if (null == user){
                logger.info(ROUTING_TO_PROTECTED_ENDPOINT_RESTRICTED_MESSAGE, ctx.get(ACTION_URI));
                ExceptionUtils.raiseCustomException(HttpStatus.UNAUTHORIZED, UNAUTH_USER_MESSAGE);
            }
            else {
                logger.info(PROCEED_ROUTING_MESSAGE, ctx.get(ACTION_URI));
                ctx.addZuulRequestHeader(ZUUL_USER_ID_HEADER_KEY, user.getUserID());
                List<String> roles = new ArrayList<>(user.getRoles());
                String roleCodes = String.join(",", roles);
                ctx.addZuulRequestHeader(ZUUL_ROLES_HEADER_KEY, roleCodes);
                setShouldDoAuth(true);
            }
        }
        return null;
    }


    /**
     * Verifies if the URI is valid.
     * @return
     */
    public Boolean isURIValid(String uri, RequestContext ctx){
        boolean isValid = false;
        for(Object obj: ZuulConfigCache.actions){
            Action action = objectMapper.convertValue(obj, Action.class);
            if (action.getActive()){
                if (uri.equals(action.getUri())){
                    isValid = true;
                    ctx.set(PATH_PARAM_URI, false);
                    ctx.set(REQ_URI, uri);
                    ctx.set(ACTION_URI, uri);
                }
                else if (action.getUri().endsWith("/*")){
                    String actionURI = action.getUri().substring(0, (action.getUri().length() - 2));
                    if (uri.contains(actionURI)){
                        isValid = true;
                        ctx.set(PATH_PARAM_URI, true);
                        ctx.set(REQ_URI, uri);
                        ctx.set(ACTION_URI, action.getUri());
                    }
                }
            }
        }
        return isValid;
    }

    /**
     * Verifies if the authToken belongs to a valid user in the system.
     * @param ctx
     * @param publicKey
     * @return
     */
    public User verifyAuthenticity(RequestContext ctx, String publicKey) {
        User user = userUtils.getUser(publicKey, ctx);
        if (null != user){
            if (!CollectionUtils.isEmpty(user.getRoles())) {
                ctx.set(USER_INFO_KEY, user);
                return user;
            }
            else{
                logger.error("The user doesn't contain any roles!");
                return null;
            }
        }
        else return null;
    }

    /**
     * Fetches URI from the request
     * @return
     */
    private String getRequestURI() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest().getRequestURI();
    }

    /**
     * Sets context auth prop.
     * @param enableAuth
     */
    private void setShouldDoAuth(boolean enableAuth) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set(AUTH_BOOLEAN_FLAG_NAME, enableAuth);
    }

    /**
     * Fetches auth token from the request header.
     * @return
     */
    private Map<String, String> getHeadersFromRequestHeader() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Map<String, String> headers = new HashMap<>();
        headers.put("PK", ctx.getRequest().getHeader(PUBLIC_KEY_HEADER));
        headers.put("SIG", ctx.getRequest().getHeader(SIG_HEADER));
        headers.put("PAYLOAD", ctx.getRequest().getHeader(PAYLOAD_HEADER));
        return headers;
    }

}

