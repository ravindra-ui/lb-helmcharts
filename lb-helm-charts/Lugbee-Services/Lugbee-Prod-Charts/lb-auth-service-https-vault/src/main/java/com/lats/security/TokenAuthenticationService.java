package com.lats.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static com.lats.security.TokenConstants.*;

public class TokenAuthenticationService {


    public static String generateToken(Map<String, Object> claims, String tokenType) {


        Map<String, Object> tokenDetails = TokenConstants.tokenDetails.get(tokenType);
        System.out.println("");
        System.out.println("tokenDetails");
        System.out.println("");
        System.out.println(tokenDetails);
        System.out.println("");

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(TOKEN_ISSUER)
                .setExpiration((!tokenType.equals("access")) ? new Date(System.currentTimeMillis() + (long) tokenDetails.get("expireTime")) : null)
            //    .setExpiration((!tokenType.equals("access")) ? new Date(System.currentTimeMillis() + Long.valueOf(tokenDetails.get("expireTime").toString())) : null)
                .signWith(SignatureAlgorithm.HS512, (String) tokenDetails.get("secret")).compact();


        System.out.println("---------");
        System.out.println("");
        System.out.println("claims");
        System.out.println(claims);
        System.out.println("");
        System.out.println("Generated Token");
        System.out.println(token);
        System.out.println("");
        System.out.println("---------");

        return token;
    }


    public static Claims getTokenPayload(String token, String tokenType) {

        System.out.println("");
        System.out.println("----------------");
        System.out.println("");
        System.out.println("GETTING TOKEN PYLOAD OF " + tokenType + " token");
        System.out.println("");
        System.out.println("----------------");
        System.out.println("");

        Map<String, Object> tokenDetails = TokenConstants.tokenDetails.get(tokenType);
        Claims claimBody = Jwts.parser().setSigningKey((String) tokenDetails.get("secret")).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

        System.out.println("");
        System.out.println("Token Payload");
        System.out.println(claimBody);
        System.out.println("");

        return claimBody;

    }

    public static <T> T validateToken(String token, Map<String, Object> claims, String tokenType) {

        System.out.println("---------");
        System.out.println("");
        System.out.println("Token to validate");
        System.out.println(token);

        Map<String, Object> tokenDetails = TokenConstants.tokenDetails.get(tokenType);
        Claims claimBody = Jwts.parser().setSigningKey((String) tokenDetails.get("secret")).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();

        System.out.println("");
        System.out.println("claimBody");
        System.out.println(claimBody);
        System.out.println("");
        System.out.println("claims");
        System.out.println(claims);
        System.out.println("");
        System.out.println("---------");


        if (!claims.get("token_type").equals(claimBody.get("token_type"))) {

            System.out.println("");
            System.out.println("Token token_type not matched");
            System.out.println("");

            return (T) (Boolean) false;
        }

        if (claims.get("admin_id") != null) {
            if (!claims.get("admin_id").equals(new String(Base64.getDecoder().decode(claimBody.get("admin_id").toString())))) {
                System.out.println("");
                System.out.println("Token admin_id not matched");
                System.out.println("");

                return (T) (Boolean) false;

            }
        }

        if (claims.get("email_id") != null) {

            if (!claims.get("email_id").equals(new String(Base64.getDecoder().decode(claimBody.get("email_id").toString())))) {
                System.out.println("");
                System.out.println("Token email_id not matched");
                System.out.println("");

                return (T) (Boolean) false;
            }
        }

        if (claims.get("account_id") != null) {

            if (!new String(Base64.getDecoder().decode(claimBody.get("account_id").toString())).equals(claims.get("account_id").toString())) {
                System.out.println("");
                System.out.println("Token accountId not matched");
                System.out.println("");
                return (T) (Boolean) false;
            }
        }

        if (claims.get("client_id") != null) {

            if (claimBody.get("client_id") == null) {
                return (T) (Boolean) false;
            }

            if (!claimBody.get("client_id").equals(claims.get("client_id"))) {
                System.out.println("");
                System.out.println("Token instanceId not matched");
                System.out.println("");
                return (T) (Boolean) false;
            }
        }


        if (tokenType.equals("access") || tokenType.equals("admin")) {

            return (T) (Claims) claimBody;
        }

        return (T) (Boolean) true;
    }


}
