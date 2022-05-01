package com.lats.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenConstants {

    //  1000 * 60 * 60 *24 -> 1 Day
    //  1000 * 60 * 60 *0.5 -> 30 mins

/*  public static long AUTH_TOKEN_EXPIRATION_TIME;
    public static long VERIFY_TOKEN_EXPIRATION_TIME;*/

    public static String TOKEN_ISSUER;
    public static String TOKEN_PREFIX;
    public static Map<String, Map<String, Object>> tokenDetails;
    @Autowired
    public Environment env;

    @Value("${token.prefix}")
    public void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }

    @Value("${token.issuer}")
    public void setTokenIssuer(String tokenIssuer) {
        TOKEN_ISSUER = tokenIssuer;
    }

    @PostConstruct
    public void initializeTokenDetails() {

        System.out.println("");
        System.out.println("=============");
        System.out.println("");
        System.out.println("INITIALIZING TOKEN DETAILS");
        System.out.println("");

        Map<String, Object> auth = new HashMap<>(3);
        auth.put("secret", env.getProperty("token.auth.secret"));
        auth.put("prefix", env.getProperty("token.auth.prefix"));
        auth.put("expireTime", Long.valueOf(env.getProperty("token.auth.expireTime")));

        System.out.println("Auth Token Config Done");
        System.out.println("");

        Map<String, Object> access = new HashMap<>(3);
        access.put("secret", env.getProperty("token.access.secret"));
        access.put("prefix", env.getProperty("token.access.prefix"));


        System.out.println("Access Token Config Done");
        System.out.println("");

        Map<String, Object> verify = new HashMap<>(3);
         verify.put("secret", env.getProperty("token.verify.secret"));
        verify.put("prefix", env.getProperty("token.verify.prefix"));
        verify.put("expireTime", Long.valueOf(env.getProperty("token.verify.expireTime")));

        System.out.println("Verify Token Config Done");
        System.out.println("");


        Map<String, Object> admin = new HashMap<>(3);
        admin.put("secret", env.getProperty("token.admin.secret"));
        admin.put("prefix", env.getProperty("token.admin.prefix"));
        admin.put("expireTime", Long.valueOf(env.getProperty("token.admin.expireTime")));

        System.out.println("Admin Token Config Done");
        System.out.println("");

        tokenDetails = new HashMap<>(3);
        tokenDetails.put("auth", auth);
        tokenDetails.put("access", access);
        tokenDetails.put("verify", verify);
        tokenDetails.put("admin", admin);

        System.out.println("");
        System.out.println("=============");
        System.out.println("");
        System.out.println("TOKEN_ISSUER : " + TOKEN_ISSUER);
        System.out.println("TOKEN_PREFIX : " + TOKEN_PREFIX);
        System.out.println("");
        System.out.println("Token Details");
        System.out.println(tokenDetails);
        System.out.println("");
        System.out.println("=============");
        System.out.println("");

    }

}
