package org.ulca.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.ulca.cache.ZuulConfigCache;
import org.ulca.models.*;
import org.ulca.utils.ExceptionUtils;
import org.ulca.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import static org.ulca.constants.RequestContextConstants.*;

/**
 * 3rd filter to execute in the request flow.
 * Checks if the user is authorised to access the API, throws exception otherwise.
 * for the given auth token checks if there's a valid user, valid roles and valid actions in the system.
 * Performs authorisation level checks on the request.
 *
 */
public class RbacFilter extends ZuulFilter {

    public ResourceLoader resourceLoader;

    @Autowired
    public UserUtils userUtils;

    @Value("${ulca.app.host}")
    private String appHost;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    public RbacFilter(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${ulca.superuser.role.code}")
    private String superUserCode;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ROUTING_TO_PROTECTED_ENDPOINT_RESTRICTED_MESSAGE = "Routing to protected endpoint {} restricted -- authorization check failed!";
    private static final String UNAUTHORIZED_USER_MESSAGE = "You are not authorised to access this resource";
    private static final String PROCEED_ROUTING_MESSAGE = "Routing to protected endpoint: {} - authorization check passed!";
    private static final String INVALID_ROLES_MESSAGE = "This user contains an invalid/inactive role!";
    private static final String INVALID_ROLES_ACTIONS_MESSAGE = "This user doesn't have access to the action.";
    private static final String RETRIEVING_USER_FAILED_MESSAGE = "Retrieving user failed";
    public static final String SKIP_RBAC = "RBAC check skipped - whitelisted endpoint | {}";

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String uri = (String) ctx.get(ACTION_URI);
        List<String> openEndpointsWhitelist = ZuulConfigCache.whiteListEndpoints;
        if (openEndpointsWhitelist.contains(uri)) {
            ctx.set(RBAC_BOOLEAN_FLAG_NAME, false);
            logger.info(SKIP_RBAC, uri);
            return null;
        }
        Boolean isUserAuthorised = verifyAuthorization(ctx, uri);
        if (isUserAuthorised){
            logger.info(PROCEED_ROUTING_MESSAGE, uri);
            return null;
        }
        else {
            logger.info(ROUTING_TO_PROTECTED_ENDPOINT_RESTRICTED_MESSAGE, uri);
            ExceptionUtils.raiseCustomException(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_USER_MESSAGE);
            return null;
        }
    }

    /**
     * Verifies if the user has the necessary authorization for the resource.
     * @param ctx
     * @param uri
     * @return
     */
    public Boolean verifyAuthorization(RequestContext ctx, String uri) {
        try {
            User user = (User) ctx.get(USER_INFO_KEY);
            Boolean sigVerify = verifySignature(ctx.get(SIG_KEY).toString(), user.getPrivateKey(), (String) ctx.get(PAYLOAD_KEY));
            if(!sigVerify) {
                logger.info("The signature doesn't match with the public key!");
                return false;
            }
            List<String> roleCodes = user.getRoles();
            if(roleCodes.contains(superUserCode)) return true;
            Boolean isRolesCorrect = verifyRoles(user.getRoles());
            if(isRolesCorrect)
                return verifyRoleActions(user.getRoles(), uri);
            else return false;
        } catch (Exception ex) {
            logger.error(RETRIEVING_USER_FAILED_MESSAGE, ex);
            return false;
        }
    }

    /**
     * Formats the url with queryparam
     * @param context
     * @param builder
     */
    private String appendQueryParams(RequestContext context, StringBuilder builder) {
        List<String> queryParams = new LinkedList<>();
        context.getRequestQueryParams()
                .forEach((key, values) -> values
                        .forEach(value -> queryParams.add(key + "=" + value)));

        builder.append("?").append(String.join("&", queryParams));
        return builder.toString();
    }


    /**
     * Verifies signature with private key
     * @param signature
     * @param privateKey
     * @param payload
     * @return
     */
    public Boolean verifySignature(String signature, String privateKey, String payload) {
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            String sigHash = privateKey.trim() + "|" + payload;
            String hash = bytesToHex(digest.digest(sigHash.trim().getBytes(StandardCharsets.UTF_8)));
            Boolean sig = hash.equals(signature.trim());
            if(!sig)
                logger.info("The signature doesn't match!");
            return sig;
        }catch (Exception e) {
            logger.error("Exception while verifying signature: ", e);
            return false;
        }

    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Verifies if the user has valid roles.
     * @param userRoles
     * @return
     */
    public Boolean verifyRoles(List<String> userRoles) {
        try{
            List<String> configRoles = ZuulConfigCache.roleCodes;
            if (CollectionUtils.isEmpty(configRoles)){
                logger.info("Roles couldn't be fetched from config");
                return false;
            }
            for(String role: userRoles){
                if (!configRoles.contains(role)) {
                    logger.info(INVALID_ROLES_MESSAGE);
                    return false;
                }
            }
            return true;
        }catch (Exception e) {
            logger.error("Exception while verifying roles: ", e);
            return false;
        }

    }

    /**
     * Verifies if the the user has access to the action being accessed.
     * @param userRoles
     * @param uri
     * @return
     */
    public Boolean verifyRoleActions(List<String> userRoles, String uri) {
        try{
            Map<String, List<String>> roleActions = ZuulConfigCache.roleActionMap;
            int fail = 0;
            for (String role: userRoles){
                List<String> actionList = roleActions.get(role);
                if (CollectionUtils.isEmpty(actionList)) fail = fail + 1;
                else{
                    if(!actionList.contains(uri)) fail += 1;
                    else break;
                }
            }
            if (fail == userRoles.size()){
                logger.info(INVALID_ROLES_ACTIONS_MESSAGE);
                return false;
            }
            else return true;
        }catch (Exception e) {
            logger.error("Exception while verifying role-actions: ", e);
            return false;
        }
    }
}
