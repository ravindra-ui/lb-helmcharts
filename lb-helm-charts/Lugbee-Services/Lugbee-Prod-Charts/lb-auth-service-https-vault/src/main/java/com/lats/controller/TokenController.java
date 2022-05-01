package com.lats.controller;

import com.lats.constants.ServiceAPIs;
import com.lats.rules.AdminTokenRules;
import com.lats.security.TokenAuthenticationService;
import com.lats.service.InstanceService;
import com.lats.service.TokenService;
import com.lats.util.HttpResponse;
import com.lats.util.RestTemplateUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.vault.support.VaultResponseSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.lats.security.TokenConstants.TOKEN_PREFIX;
import static com.lats.security.TokenConstants.tokenDetails;
import static com.lats.util.HttpConstants.*;
import static com.lats.util.SwaggerConstants.DataTypes.STRING;
import static com.lats.util.SwaggerConstants.DataTypes.UUID;
import static com.lats.util.SwaggerConstants.ParamTypes.*;

/**
 * @author Jayendra
 * @version 1.0
 * @since 2018-06-21
 **/

@Validated
@RestController
@Api(description = "It contains APIs which generates auth, access, verify token and validates them")
public class TokenController {


    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private HttpResponse HttpResponse;
    @Autowired
    private InstanceService instanceService;
    @Autowired
    private TokenService tokenService;

    @Value("${vault.auth-path}")
    private String authPath;

    @Value("${vault.access-path}")
    private String accessPath;

    @Value("${vault.verify-path}")
    private String verifyPath;

    /*@Value("${service.userLogIn}")
    private String userLogIn;*/

    @Value("${services.lams.address}" + ":" + "${services.lams.port}" + ServiceAPIs.Lams.userLogIn)
    private String userLogIn;

    /*@Value("${service.hostLogIn}")
    private String hostLogIn;*/

    @Value("${services.lams.address}" + ":" + "${services.lams.port}" + ServiceAPIs.Lams.hostLogIn)
    private String hostLogIn;


    @Autowired
    private RestTemplateUtil restTemplateUtil;


    @Value("${redis.host}")
    private String REDIS_HOST;
    @Value("${redis.port}")
    private int REDIS_PORT;

    @GetMapping(value = {"/"})
    public String index() {


        return "LATS SERVICE";
    }

    @GetMapping(value = {"/redisTest/Add/{key}/{value}"})
    public String redisTestAdd(@PathVariable("key") String key, @PathVariable("value") String value) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("SET REDIS TEST");
        System.out.println("");
        System.out.println("REDIS_HOST : " + REDIS_HOST);
        System.out.println("REDIS_PORT : " + REDIS_PORT);
        System.out.println("");
        System.out.println("key : " + key);
        System.out.println("value : " + value);
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

//        tokenService.addToken(key, value);

        System.out.println("");
        System.out.println("DATA Added IN RESULT");
        System.out.println("");

        return "DATA Added IN RESULT";
    }


    @GetMapping(value = {"/redisTest/{key}"})
    public String redisTest(@PathVariable("key") String key) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("GET REDIS TEST");
        System.out.println("");
        System.out.println("REDIS_HOST : " + REDIS_HOST);
        System.out.println("REDIS_PORT : " + REDIS_PORT);
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        VaultResponseSupport<Map> redisReuslt = tokenService.getToken(key);

        System.out.println("");
        System.out.println("redisResult");
        System.out.println(redisReuslt);
        System.out.println("");

        return "REDIS RESULT : " + redisReuslt;
    }


    @GetMapping(value = "/testAPILocal")
    public String testAPILocal() {

        System.out.println("");
        System.out.println("===============");
        System.out.println("-------------");
        System.out.println("");
        System.out.println("TEST LOCAL API FUNCTION CALLED");
        System.out.println("");
        System.out.println("-------------");
        System.out.println("===============");
        System.out.println("");

        String response = restTemplateUtil.testAPILocal();

        System.out.println("");
        System.out.println("TEST LOCAL API Final Response");
        System.out.println(response);
        System.out.println("");

        return response;
    }


    @GetMapping(value = "/testAPI")
    public String test() {

        System.out.println("");
        System.out.println("===============");
        System.out.println("-------------");
        System.out.println("");
        System.out.println("TEST CUSTOM API FUNCTION CALLED");
        System.out.println("");
        System.out.println("-------------");
        System.out.println("===============");
        System.out.println("");

        String response = restTemplateUtil.testAPI();

        System.out.println("");
        System.out.println("TEST CUSTOM API Final Response");
        System.out.println(response);
        System.out.println("");

        return response;
    }


    @GetMapping(value = "/testCustomAPI/{namespace}/{servicename}")
    public String testCustomAPI(@PathVariable("namespace") String namespace,
                                @PathVariable("servicename") String servicename) {

        System.out.println("");
        System.out.println("===============");
        System.out.println("-------------");
        System.out.println("");
        System.out.println("TEST API FUNCTION CALLED");
        System.out.println("");
        System.out.println("-------------");
        System.out.println("===============");
        System.out.println("");

        System.out.println("");
        System.out.println("Calling NameSpace : " + namespace);
        System.out.println("Service Name : " + servicename);
        System.out.println("");

        String response = restTemplateUtil.testAPI(namespace, servicename);

        System.out.println("");
        System.out.println("Final Response");
        System.out.println(response);
        System.out.println("");

        return response;
    }


    /*
        THIS API creates token on basis of provided input tokenType
        @param tokenType = "auth", "access", "verify"
        @param client_id SessionId or instanceId
        @param client_type  = "web", "mobile"
        @param request_type  = "register", "login"
        @param account_type  = "host", "user"
        @param email_id SessionId or instanceId
        @param account_id uniqueId of user
        @return HttpResponse: see HttpResponse class under util package

        Note:
        In case of access and verify token
        auth token will be validated first

        Note:
        In case of access token and requestType login
        token will be inserted in redis

        */

    @PostMapping(value = "/adminToken", produces = "application/json")
    public Map<String, Object> getAdminToken(@RequestParam("username") @NotBlank String username,
                                             @RequestHeader("password") @NotBlank String password) {

        System.out.println("------------");
        System.out.println("");
        System.out.println("GET ADMIN TOKEN CONTROLLER");
        System.out.println("");
        System.out.println("username : " + username);
        System.out.println("password : " + password);
        System.out.println("");
        System.out.println("------------");


        Map<String, Object> adminLoginResponse = restTemplateUtil.adminLogIn(username, password);
        if ((int) adminLoginResponse.get("status") != SUCCESS_STATUS_CODE) {

            System.out.println("------------");
            System.out.println("");
            System.out.println("adminLoginResponse : NO SUCCESS");
            System.out.println("");
            System.out.println("------------");

            return adminLoginResponse;

        }

        String adminId = null;
        Map<String, Object> adminTokenClaims = new HashMap<>();
        adminTokenClaims.put("token_type", "access");
        adminTokenClaims.put("user_role", "admin");
        Map<String, Object> adminLoginResponseData = (Map<String, Object>) adminLoginResponse.get("data");
        for (int x = 0; x <= AdminTokenRules.payload.length - 1; x++) {

            System.out.println("");
            System.out.println("--------------");
            System.out.println("");
            System.out.println("IN LOOP");
            System.out.println("");
            System.out.println(AdminTokenRules.payload[x]);
            System.out.println("");
            System.out.println(adminLoginResponseData.get(AdminTokenRules.payload[x]));
            System.out.println("");
            System.out.println("--------------");
            System.out.println("");

            if (AdminTokenRules.payload[x].equals("admin_id")) {
                adminId = adminLoginResponseData.get(AdminTokenRules.payload[x]).toString();
                String encryptedAdminId = Base64.getEncoder().encodeToString(adminId.getBytes());
                adminTokenClaims.put(AdminTokenRules.payload[x], encryptedAdminId);
            } else {
                adminTokenClaims.put(AdminTokenRules.payload[x], adminLoginResponseData.get(AdminTokenRules.payload[x]));
            }
        }

        System.out.println("");
        System.out.println("---------------");
        System.out.println("");
        System.out.println("Admin Token Claims");
        System.out.println("");
        System.out.println(adminTokenClaims);
        System.out.println("");
        System.out.println("---------------");
        System.out.println("");

        String token = TokenAuthenticationService.generateToken(adminTokenClaims, "access");
        String tokenPath = tokenService.getTokenPath(accessPath, adminId, "admin");
        tokenService.addToken(tokenPath, token, "access");

        Map<String, Object> data = new HashMap<>(3);
        data.put("token", token);
        data.put("token_type", "admin");
        data.put("admin_id", adminLoginResponseData.get("admin_id"));
        data.put("expires_in", (long) tokenDetails.get("admin").get("expireTime") / 1000);


        return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_GENERATED, data);
    }


    @PostMapping(value = "/{token_type}/token", produces = "application/json")
    @ApiOperation(value = "This API will generate token on the basis of provided details such as client_id , client_type etc" +
            "auth and verify token have expiry Time, Auth token contains account_type, email_id, token_type." +
            "Verify token which is used for email verification contains account_id, account_type and token_type and Access token" +
            "which is used for authenticating user and contains account_id , account_type and token_type "
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token_type", required = true, dataType = STRING, paramType = PATH, value = "tokenType eg: auth, verify, access"),
            @ApiImplicitParam(name = "client_id", required = true, dataType = STRING, paramType = QUERY, value = "id of user's device it is \"\" in case of web"),
            @ApiImplicitParam(name = "auth_type", required = false, dataType = STRING, paramType = QUERY, value = "user authentication type such as fb, google, web (Need incase of access token)"),
            @ApiImplicitParam(name = "client_type", required = true, dataType = STRING, paramType = QUERY, value = "user device type eg : web, mobile"),
            @ApiImplicitParam(name = "account_type", required = true, dataType = STRING, paramType = QUERY, value = "user account Type eg : host, user"),
            @ApiImplicitParam(name = "email_id", required = true, dataType = STRING, paramType = QUERY, value = "Email Id"),
            @ApiImplicitParam(name = "Authorization", required = false, dataType = STRING, paramType = HEADER, value = "auth token (Need in case to generate access, verify token)"),
            @ApiImplicitParam(name = "password", required = false, dataType = STRING, paramType = HEADER, value = "user password (need in case to generate access token and if auth_type is web)"),
            @ApiImplicitParam(name = "account_id", required = false, dataType = UUID, paramType = QUERY, value = "Account Id (Need in case of verify, access token)")

    })
    public Map<String, Object> getToken(@PathVariable(value = "token_type") String tokenType,
                                        @RequestParam(value = "client_id") String clientId,
                                        @RequestParam(value = "auth_type", required = false) String authType,
                                        @RequestParam(value = "client_type") @NotBlank String clientType,
                                        @RequestParam(value = "account_type") @NotBlank String accountType,
                                        @RequestParam(value = "email_id") @NotBlank @Email String emailId,
                                        @RequestParam(value = "account_id", required = false) String accountId, //need in case of tokenType =  verify
                                        HttpServletRequest request
    ) {


        System.out.println("------------");
        System.out.println("");
        System.out.println("GET TOKEN CONTROLLER");
        System.out.println("");
        System.out.println("tokenType : " + tokenType);
        System.out.println("");
        System.out.println("clientId : " + clientId);
        System.out.println("clientType : " + clientType);
        System.out.println("accountType : " + accountType);
        System.out.println("emailId : " + emailId);
        System.out.println("accountId : " + accountId);
        System.out.println("authType : " + authType);
        System.out.println("");
        System.out.println("");
        System.out.println("request");
        System.out.println(request.getHeader("Authorization"));
        System.out.println(request.getHeader("password"));
        System.out.println("");
        System.out.println("------------");


        if (!tokenType.equals("verify") && !tokenType.equals("auth") && !tokenType.equals("access")) {
            System.out.println("Invalid Token : " + tokenType);
            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_TOKEN_TYPE, null);
        }


        if (!accountType.equals("host") && !accountType.equals("user")) {
            System.out.println("Invalid accountType");
            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

        }

        if (clientId == null || clientId.equals("")) {
            System.out.println("Invalid clientId");
            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
        }

//        if (clientId.equals("") && clientType.equals("mobile")) {
//            System.out.println("Invalid clientId for mobile");
//            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
//        }

//        if (!clientId.equals("") && clientType.equals("web")) {
//            System.out.println("Invalid clientId for web");
//            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
//        }

        if (!clientType.equals("web") && !clientType.equals("mobile")) {
            System.out.println("Invalid clientType");
            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
        }

//        String key = clientType.equals("mobile") ? clientId : clientType;
        if (tokenType.equals("auth")) {

          /*  Instance instance;
            if (clientType.equals("web")) {
                instance = instanceService.getInstance(emailId, accountType, "web");
            } else {

                instance = instanceService.getInstance(emailId, accountType, clientId);
            }*/


          /*  System.out.println("---------");
            System.out.println("");
            System.out.println("Instance");
            System.out.println(instance);
            System.out.println("");
            System.out.println("---------");*/

            Map<String, Object> claims = new HashMap<>(4);

            claims.put("user_role", accountType);
            claims.put("email_id", Base64.getEncoder().encodeToString(emailId.getBytes()));
            claims.put("token_type", tokenType);
            claims.put("client_type", clientType);
            claims.put("client_id", clientId);
//            if (clientType.equals("mobile")) {
//                claims.put("client_id", clientId);
//            } else {
//                claims.put("client_id", "");
//
//            }

          /*  if (instance != null) {
                if (instance.getAccount_id() != null) {
                    claims.put("account_id", instance.getAccount_id());
                }
            }*/


            String token = TokenAuthenticationService.generateToken(claims, tokenType);
            String tokenPath = tokenService.getTokenPath(authPath, emailId, clientId);
            tokenService.addToken(tokenPath, token, tokenType);

            Map<String, Object> data = new HashMap<>(3);
            data.put("token", token);
            data.put("token_type", tokenType);
            data.put("expires_in", (long) tokenDetails.get("auth").get("expireTime") / 1000);
            data.put("expiry_ts", TokenAuthenticationService.getTokenPayload(token, tokenType).get("exp"));

            return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_GENERATED, data);


        } else if (tokenType.equals("access")) {

            if ((authType == null) || !authType.equals("fb") && !authType.equals("google") && !authType.equals("email")) {
                System.out.println("Invalid authType");
                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

            }


            String password = request.getHeader("password");
            String oauthToken = request.getHeader("social_oauth_token");

            System.out.println("");
            System.out.println("oauthToken : " + oauthToken);
            System.out.println("password : " + password);
            System.out.println("");

            if (authType.equals("email")) {

                if (password == null || password.equals("")) {

                    System.out.println("");
                    System.out.println("Invalid password");
                    System.out.println(password);
                    System.out.println("");

                    return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

                }
            } else {

                if (oauthToken == null || oauthToken.equals("")) {

                    System.out.println("");
                    System.out.println("Invalid oauthToken");
                    System.out.println(oauthToken);
                    System.out.println("");

                    return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
                }
            }


            String token = request.getHeader("Authorization");
            Map<String, Object> claims = new HashMap<>(4);
            // claims.put("account_id", ((HashMap<String, Object>) accountManagmentServiceResponse.get("data")).get("account_id"));
            claims.put("client_id", clientId);
            claims.put("user_role", accountType);
            claims.put("email_id", emailId);
            claims.put("token_type", "auth");
            claims.put("client_type", clientType);

            String tokenPath = tokenService.getTokenPath(authPath, emailId, clientId);
            VaultResponseSupport<Map> response = tokenService.getToken(tokenPath);

            if (response == null) {
                System.out.println("---------------------------");
                System.out.println("");
                System.out.println("Auth Token is not found in DB");
                System.out.println("");
                System.out.println("---------------------------");

                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
            }

            Map<String, String> tokenData = (Map<String, String>) response.getData().get("data");
            String authToken = tokenData.get("token");

            if (authToken == null || !authToken.equals(token.replace(TOKEN_PREFIX, "").trim())) {
                System.out.println("---------------------------");
                System.out.println("");
                System.out.println("Auth Token is not matched");
                System.out.println("");
                System.out.println("---------------------------");

                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
            }

            boolean token_validate = TokenAuthenticationService.validateToken(token, claims, "auth");

            if (!token_validate) {

                System.out.println("---------------------------");
                System.out.println("");
                System.out.println("Auth Token is not validated");
                System.out.println("");
                System.out.println("---------------------------");

                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
            }


            //ACCOUNT MANAGMENT SERVICE PARAMETERS
            MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
            requestParams.add("email_id", emailId);
            requestParams.add("account_type", accountType);
            requestParams.add("client_id", clientId);
            requestParams.add("auth_type", authType);
            requestParams.add("client_type", clientType);

            //ACCOUNT MANAGMENTHEADER
            HttpHeaders requestHeader = new HttpHeaders();
            requestHeader.add("password", password);
            requestHeader.add("Authorization", request.getHeader("Authorization"));
            requestHeader.add("social_oauth_token", oauthToken);

            HttpEntity<MultiValueMap<String, String>> httpRequest = new HttpEntity<MultiValueMap<String, String>>(requestParams, requestHeader);
            Map<String, Object> accountManagmentServiceResponse = (accountType.equals("user")) ? restTemplate.postForObject(userLogIn, httpRequest, Map.class) : restTemplate.postForObject(hostLogIn, httpRequest, Map.class);


            System.out.println("");
            System.out.println("accountManagmentServiceResponse");
            System.out.println(accountManagmentServiceResponse);
            System.out.println("");


                /*
                 *
                HERE ACCOUNT MANAGMENT SERVICE IS CALLED
                 *
                 */

            if ((int) accountManagmentServiceResponse.get("status") != SUCCESS_STATUS_CODE) {

                return HttpResponse.getHttpResponse((int) accountManagmentServiceResponse.get("status"), accountManagmentServiceResponse.get("message_code").toString(), null);
            } else if (authType.equals("email") && (int) accountManagmentServiceResponse.get("status") == SUCCESS_STATUS_CODE && (int) ((HashMap<String, Object>) accountManagmentServiceResponse.get("data")).get("email_verify") == 0) {

                Map<String, Object> data = new HashMap<>(1);
                data.put("email_verify", 0);
                data.put("account_id", ((HashMap<String, Object>) accountManagmentServiceResponse.get("data")).get("account_id"));

                return HttpResponse.getHttpResponse(FORBIDDEN_STATUS_CODE, EMAIL_NOT_VERIFIED, data);


            }


            String account_id = ((HashMap<String, Object>) accountManagmentServiceResponse.get("data")).get("account_id").toString();

//            claims.remove("client_id");
            claims.remove("email_id");
            claims.put("token_type", tokenType);
//            claims.put("client_type", clientType);
            claims.put("account_id", Base64.getEncoder().encodeToString(account_id.getBytes()));
            //   String token = TokenAuthenticationService.generateToken(claims, tokenType);


//            String tokenKey = account_id + ":" + clientType;
//            if (clientType.equals("mobile")) {
//                tokenKey += ":" + clientId;
//            }


//            System.out.println("");
//            System.out.println("----------");
//            System.out.println("");
//            System.out.println("tokenKey");
//            System.out.println(tokenKey);
//            System.out.println("");
//            System.out.println("----------");
//            System.out.println("");

            Map<String, Object> data = new HashMap<>(4);
            data.put("account_id", ((HashMap<String, Object>) accountManagmentServiceResponse.get("data")).get("account_id"));
            if (accountType.equals("host")) {

                data.put("host_verification", ((HashMap<String, Object>) accountManagmentServiceResponse.get("data")).get("host_verification"));
            }
            data.put("token_type", tokenType);

            String tokenKey = tokenService.getTokenPath(accessPath, account_id, clientId);
            VaultResponseSupport<Map> vaultResponseSupport = tokenService.getToken(tokenKey);

            if (vaultResponseSupport == null) {
                System.out.println("");
                System.out.println("Adding New Token");
                System.out.println("");

                //<account_id>:<client_type>:<client_id>

                String accessToken = TokenAuthenticationService.generateToken(claims, tokenType);

                tokenService.addToken(tokenKey, accessToken, tokenType);
                data.put("token", accessToken);
            } else {
                Map<String, String> tokenMap = (Map<String, String>) vaultResponseSupport.getData().get("data");
                if (tokenMap.get("token") == null) {
                    String accessToken = TokenAuthenticationService.generateToken(claims, tokenType);
                    tokenService.addToken(tokenKey, accessToken, tokenType);
                    data.put("token", accessToken);
                } else {
                    data.put("token", tokenMap.get(clientId));
                }
            }


            return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_GENERATED, data);


        } else if (tokenType.equals("verify")) {

            if (accountId == null || accountId.equals("")) {
                System.out.println("");
                System.out.println("invalid accountId");
                System.out.println("");
                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

            }

            Map<String, Object> claims = new HashMap<>(4);
            //   claims.put("account_id", UUID.fromString(accountId));
            claims.put("client_id", clientId);
            claims.put("client_type", clientType);
            claims.put("user_role", accountType);
            claims.put("email_id", emailId);
            claims.put("token_type", "auth");

            String token = request.getHeader("Authorization");
            String tokenPath = tokenService.getTokenPath(authPath, emailId, clientId);
            VaultResponseSupport<Map> response = tokenService.getToken(tokenPath);

            if (response == null) {
                System.out.println("---------------------------");
                System.out.println("");
                System.out.println("Auth Token is not found in DB");
                System.out.println("");
                System.out.println("---------------------------");

                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
            }

            Map<String, String> tokenData = (Map<String, String>) response.getData().get("data");
            String authToken = tokenData.get("token");

            if (authToken == null || !authToken.equals(token.replace(TOKEN_PREFIX, "").trim())) {

                System.out.println("---------------------------");
                System.out.println("");
                System.out.println("Auth Token is not matched");
                System.out.println("");
                System.out.println("---------------------------");

                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
            }

            boolean token_validate = TokenAuthenticationService.validateToken(token, claims, "auth");


            if (!token_validate) {

                System.out.println("---------------------------");
                System.out.println("");
                System.out.println("Auth Token is not validated");
                System.out.println("");
                System.out.println("---------------------------");
                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);

            }

//            claims.remove("client_id");
            claims.remove("email_id");


            claims.put("token_type", tokenType);
            claims.put("account_id", Base64.getEncoder().encodeToString(accountId.getBytes()));

            String verifyToken = TokenAuthenticationService.generateToken(claims, tokenType);
            String tokenPath1 = tokenService.getTokenPath(verifyPath, accountId, clientId);
            tokenService.addToken(tokenPath1, verifyToken, tokenType);

            Map<String, Object> data = new HashMap<>(3);
            data.put("token", verifyToken);
            data.put("token_type", tokenType);
            data.put("expire_in", (long) tokenDetails.get("verify").get("expireTime") / 1000);

            return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_GENERATED, data);

        }

        return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, NO_TOKEN_CREATED, null);

    }


    /*
    THIS API will validate token on basis of provided input tokenType
    @param tokenType = "auth", "access", "verify"
    @param email_id
    @param client_id : id of user device(if clientType is not web)
    @param account_id  : uniqueId of user
    @return HttpResponse: see HttpResponse class under util package

    Note:
    In case of access token and token will
    be also checked in redis if it exists
    in redis
    */

    @PostMapping(value = "/validateAdminToken")
    public Map<String, Object> validateAdminToken(@RequestHeader("Authorization") String token,
                                                  @RequestParam("admin_id") String adminId) {

        System.out.println("----------");
        System.out.println("");
        System.out.println("VALIDATE ADMIN TOKEN CONTROLLER");
        System.out.println("");
        System.out.println("adminId : " + adminId);
        System.out.println("token : " + token);
        System.out.println("");
        System.out.println("----------");
        System.out.println("");

//        Map<String, Object> adminTokenClaims = new HashMap<>();
//        adminTokenClaims.put("admin_id", adminId);
//        adminTokenClaims.put("token_type", "admin");

        Claims claims = TokenAuthenticationService.getTokenPayload(token, "access");
        String admin_id = new String(Base64.getDecoder().decode(claims.get("admin_id").toString()));

        String tokenPath = tokenService.getTokenPath(accessPath, admin_id, "admin");
        VaultResponseSupport<Map> response = tokenService.getToken(tokenPath);

        if (response == null) {
            System.out.println("-------");
            System.out.println("");
            System.out.println("Token not found in DB");
            System.out.println("");
            System.out.println("-------");
            return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
        }


//        Object validateTokenResponse = TokenAuthenticationService.validateToken(token, adminTokenClaims, "admin");
//
//        System.out.println("validateTokenResponse");
//        System.out.println(validateTokenResponse);
//        System.out.println("");
//
//        if (validateTokenResponse.equals(false)) {
//
//            System.out.println("-------");
//            System.out.println("");
//            System.out.println("Token not validated");
//            System.out.println("");
//            System.out.println("-------");
//            return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
//        }

        claims.put("admin_id", admin_id);
        return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_VERIFIED, (Claims) claims);
    }

    @PostMapping(value = "/{token_type}/validateToken")
    @ApiOperation(value = "This API will validate token on the basis of provided details such as client_id , client_type etc")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = STRING, paramType = HEADER, value = "auth token (Need in case to generate access, verify token)"),
            @ApiImplicitParam(name = "token_type", required = true, dataType = STRING, paramType = PATH, value = "tokenType eg: auth, verify, access"),
            @ApiImplicitParam(name = "client_id", required = false, dataType = STRING, paramType = QUERY, value = "id of user's device it is \"\" in case of web (Need in case of auth token)"),
            @ApiImplicitParam(name = "client_type", required = false, dataType = STRING, paramType = QUERY, value = "user device type eg : web, mobile (Need in case of auth token)"),
            @ApiImplicitParam(name = "email_id", required = false, dataType = STRING, paramType = QUERY, value = "Email Id (Need in case of auth token)"),
            @ApiImplicitParam(name = "account_id", required = false, dataType = STRING, paramType = QUERY, value = "Account Id (Need in case of verify, access token)")

    })
    public Map<String, Object> validateToken(@PathVariable(value = "token_type") String tokenType,
                                             @RequestParam(value = "account_id", required = false) String accountId, // need in case of tokenType = "access" , "verify" , "auth" (incase if it is in token payload)
                                             @RequestParam(value = "client_id", required = false) String clientId, // need in case of auth token
                                             @RequestParam(value = "client_type", required = false) String clientType, // need in case of auth token
                                             @RequestParam(value = "email_id", required = false) @Email String emailId,
                                             HttpServletRequest request
    ) {
        System.out.println("------------");
        System.out.println("");
        System.out.println("VALIDATE TOKEN CONTROLLER");
        System.out.println("");
        System.out.println("tokenType : " + tokenType);
        System.out.println("");
        System.out.println("accountId : " + accountId);
        System.out.println("clientId : " + clientId);
        System.out.println("clientType : " + clientType);
        System.out.println("emailId : " + emailId);
        System.out.println("");


        String token = request.getHeader("Authorization");

        System.out.println("");
        System.out.println("token");
        System.out.println(token);
        System.out.println("");
        if (!tokenType.equals("auth") && !tokenType.equals("access") && !tokenType.equals("verify")) {

            System.out.println("Invalid tokenType");
            System.out.println("");

            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_TOKEN_TYPE, null);

        }

        if (token == null || token.equals("")) {

            System.out.println("Invalid token");
            System.out.println("");
            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

        }

//        if ((tokenType.equals("verify") || tokenType.equals("access")) && (accountId == null || accountId.equals(""))) {
//
//            System.out.println("Invalid accountId");
//            System.out.println("");
//
//
//            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_TOKEN, null);
//        }

        Claims claimBody = TokenAuthenticationService.getTokenPayload(token, tokenType);
        String client_id = claimBody.get("client_id") == null ? "" : claimBody.get("client_id").toString();
        String client_type = claimBody.get("client_type").toString();
//        String key = client_type.equals("mobile") ? client_id : client_type;

//        if (tokenType.equals("access")) {

        String account_id = null;
        String email_id = null;
        String tokenKey = null;
        String tokenPath = null;
        if (tokenType.equals("access") || tokenType.equals("verify")) {
            account_id = new String(Base64.getDecoder().decode(claimBody.get("account_id").toString()));
            tokenKey = account_id;

            tokenPath = tokenType.equals("access") ? accessPath : verifyPath;
        }

        if (tokenType.equals("auth")) {

            email_id = new String(Base64.getDecoder().decode(claimBody.get("email_id").toString()));
            tokenKey = email_id;
            tokenPath = authPath;
        }

        /*    String tokenKey = accountId + ":" + clientType;
            if (clientType.equals("mobile")) {
                tokenKey += ":" + clientId;
            }*/


//            System.out.println("");
//            System.out.println("----------");
//            System.out.println("");
//            System.out.println("tokenKeyPattern");
//            System.out.println(accountId);
//            System.out.println("");
//            System.out.println("----------");
//            System.out.println("");


//            token = token.replace(TOKEN_PREFIX, "").trim();

//            if (tokenService.checkToken(accountId, token) == false) {
//
//                System.out.println("");
//                System.out.println("TOKEN NOT FOUND IN REDIS");
//                System.out.println("");
//
//                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
//            }

        String tokenFullPath = tokenService.getTokenPath(tokenPath, tokenKey, client_id);
        VaultResponseSupport<Map> response = tokenService.getToken(tokenFullPath);

        if (response == null) {
            System.out.println("---------------------------");
            System.out.println("");
            System.out.println("Auth Token is not found in DB");
            System.out.println("");
            System.out.println("---------------------------");

            return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
        }
        Map<String, String> tokenData = (Map<String, String>) response.getData().get("data");
        String token2 = tokenData.get("token");

        if (token2 == null || !token2.equals(token.replace(TOKEN_PREFIX, "").trim())) {
            System.out.println("---------------------------");
            System.out.println("");
            System.out.println("Auth Token is not matched");
            System.out.println("");
            System.out.println("---------------------------");

            return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
        }
//        }

//        if (tokenType.equals("auth")) {


//            if (clientId == null) {
//                System.out.println("Invalid clientId");
//                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
//
//
//            }
//
//            if (clientId.equals("") && clientType.equals("mobile")) {
//                System.out.println("Invalid clientId for mobile");
//                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
//            }
//
//
//            if (!clientId.equals("") && clientType.equals("web")) {
//                System.out.println("Invalid clientId for web");
//                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
//            }
//
//
//            if (!clientType.equals("web") && !clientType.equals("mobile")) {
//                System.out.println("Invalid clientType");
//                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
//
//            }
//
//            if (emailId == null || emailId.equals("")) {
//
//                System.out.println("Invalid emailId");
//                System.out.println("");
//
//                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
//
//            }

//        }

//        HashMap<String, Object> claims = new HashMap<>(3);
//        claims.put("email_id", email_id);
////        if (clientType != null) {
////
////            //  if (clientType.equals("mobile") && tokenType.equals("auth")) {
////            if (tokenType.equals("auth")) {
////                claims.put("client_id", clientId);
////            }
////        }
//        claims.put("account_id", account_id);
//        claims.put("token_type", tokenType);

//        Object validateTokenResponse = TokenAuthenticationService.validateToken(token, claims, tokenType);
//
//        //   if ((boolean) TokenAuthenticationService.validateToken(token, claims, tokenType) == false) {
//        if (validateTokenResponse.equals(false)) {
//
//            System.out.println("-------");
//            System.out.println("");
//            System.out.println("Token not validated");
//            System.out.println("");
//            System.out.println("-------");
//            return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
//        }
        System.out.println("------------");


        if (tokenType.equals("access")) {

            System.out.println("---------");
            System.out.println("");
            System.out.println("ACCESS TOKEN IS VALIDATED");
            System.out.println("");
            System.out.println("PAYLOAD");
            System.out.println("");
            System.out.println(claimBody);
            System.out.println("");
            System.out.println("---------");


            claimBody.put("account_id", account_id);
            return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_VERIFIED, claimBody);
        }

        System.out.println("");
        System.out.println("Final Response");
        System.out.println(HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_VERIFIED, null));
        System.out.println("");
        return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_VERIFIED, null);
    }


    /*
    THIS API will remove access token from redis
    @param tokenType = "auth", "access", "verify"
    @param email_id
    @param account_id  : uniqueId of user
    @return HttpResponse: see HttpResponse class under util package

    Note:
    This API will validate the token first on basis
    of provided input
    */

    @PostMapping(value = "/logout")
    @ApiOperation(value = "This API logout the user from particular device(web , mobile) and it remove access token of that user from redis")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", required = true, dataType = STRING, paramType = HEADER, value = "auth token (Need in case to generate access, verify token)"),
            @ApiImplicitParam(name = "client_id", required = true, dataType = STRING, paramType = QUERY, value = "id of user's device it is \"\" in case of web"),
            @ApiImplicitParam(name = "client_type", required = true, dataType = STRING, paramType = QUERY, value = "user device type eg : web, mobile"),
            @ApiImplicitParam(name = "account_id", required = true, dataType = UUID, paramType = QUERY, value = "Account Id")

    })
    public Map<String, Object> removeAccessToken(
            @RequestParam(value = "account_id") @NotBlank String accountId,
            @RequestParam(value = "client_id", required = false) String clientId,
            @RequestParam(value = "client_type") @NotBlank String clientType,
            @RequestParam(value = "token_type", required = false) String tokenType,
            HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        System.out.println("------------");
        System.out.println("");
        System.out.println("REMOVE ACCESS TOKEN CONTROLLER");
        System.out.println("");
        System.out.println("accountId : " + accountId);
        System.out.println("clientId : " + clientId);
        System.out.println("clientType : " + clientType);
        System.out.println("tokenType : " + tokenType);
        System.out.println("");

        if (!tokenType.equals("verify")) {
            if (token == null || token.equals("")) {

                System.out.println("Invalid token");
                System.out.println("");

                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

            }

            Claims claimBody = TokenAuthenticationService.getTokenPayload(token, "access");
            String client_id = claimBody.get("client_id") == null ? "" : claimBody.get("client_id").toString();

            token = token.replace(TOKEN_PREFIX, "").trim();
            if (accountId == null || accountId.equals("")) {
                System.out.println("Invalid accountId");
                System.out.println("");

                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
            }

            if (!clientType.equals("web") && !clientType.equals("mobile") && !clientType.equals("all")) {
                System.out.println("Invalid clientType");
                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

            }

//        if (clientId == null) {
//            System.out.println("Invalid clientId");
//            return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
//        }


            System.out.println("");
            System.out.println("token");
            System.out.println(token);
            System.out.println("");

            Map<String, Object> claims = new HashMap<>(2);
            claims.put("account_id", accountId);
            claims.put("token_type", "access");

//        boolean token_validate = TokenAuthenticationService.validateToken(request.getHeader("Authorization"), claims, "access");
            Object token_validate = TokenAuthenticationService.validateToken(request.getHeader("Authorization"), claims, "access");

//        if (!token_validate) {
            if (token_validate.equals(false)) {

                System.out.println("---------------------------");
                System.out.println("");
                System.out.println("Auth Token is not validated");
                System.out.println("");
                System.out.println("---------------------------");

                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);

            }

//        Set<String> accessTokenKeys = (Set<String>) tokenService.getTokenByPattern(accountId);

//        token = token.replace(TOKEN_PREFIX, "").trim();

//        String key = clientType.equals("mobile") ? clientId : clientType;
            String tokenKey = tokenService.getTokenPath(accessPath, accountId, client_id);
            VaultResponseSupport<Map> response = tokenService.getToken(tokenKey);
            if (response == null) {
                return HttpResponse.getHttpResponse(CONTENT_NOT_FOUND_STATUS_CODE, NO_TOKEN_FOUND, null);
            }

            Map<String, String> tokenMap = (Map<String, String>) response.getData().get("data");

            System.out.println("");
            System.out.println("accessTokenKeys");
            System.out.println(tokenMap);
            System.out.println("");


//        String tokenKey = accountId + ":" + clientType;
//        if (clientType.equals("mobile")) {
//            tokenKey += ":" + clientId;
//        }

//        System.out.println("");
//        System.out.println("----------");
//        System.out.println("");
//        System.out.println("tokenKey");
//        System.out.println(tokenKey);
//        System.out.println("");
//        System.out.println("----------");
//        System.out.println("");


            if (tokenMap.get("token") == null || !tokenMap.get("token").equals(token)) {

                System.out.println("");
                System.out.println("TOKEN NOT MATCHED");
                System.out.println("");

                return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);

            }

            tokenService.removeToken(tokenKey);
        }

        if (clientType.equals("all")) {

            System.out.println("");
            System.out.println("Remove all Tokens");
            System.out.println("");


            if (clientId == null || !clientId.equals("all")) {

                System.out.println("");
                System.out.println("Invalid clientId for clientType all");
                System.out.println("");

                return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
            }

            String encryptedTokenKey1 = Base64.getEncoder().encodeToString(accountId.getBytes());
            String tokenBasePath = "/secret/data/" + accessPath + "/" + encryptedTokenKey1;
            tokenService.removeAllTokenByAccountId(tokenBasePath, encryptedTokenKey1,accessPath);
//            for (String accessTokenKey : accessTokenKeys) {
//
//                System.out.println("");
//                System.out.println("accessTokenKey");
//                System.out.println(accessTokenKey);
//                System.out.println("");
//
//                tokenService.removeToken(accessTokenKey);
//
//            }

            return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_REMOVED, null);

        }


//        tokenMap.remove(clientId);
//        if (!tokenMap.isEmpty()) {
//            tokenService.addToken(tokenKey, tokenMap);
//        } else {

//        }
//
//        System.out.println("");
//        System.out.println("Token Removed");
//        System.out.println(removedToken);
//        System.out.println("");
//
//        if (removedToken == false) {
//
//            System.out.println("");
//            System.out.println("No Token Removed");
//            System.out.println("");
//            return HttpResponse.getHttpResponse(CONTENT_NOT_FOUND_STATUS_CODE, NO_TOKEN_FOUND, null);
//        }

        return HttpResponse.getHttpResponse(SUCCESS_STATUS_CODE, TOKEN_REMOVED, null);
    }


}

