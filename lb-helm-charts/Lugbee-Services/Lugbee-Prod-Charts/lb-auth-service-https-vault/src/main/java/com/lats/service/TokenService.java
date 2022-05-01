package com.lats.service;

import com.lats.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.support.VaultResponseSupport;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenService {


    @Autowired
    private TokenRepository tokenRepository;

    public String getTokenPath(String tokenPath, String tokenKey1, String tokenKey2) {
        String encryptedTokenKey1 = Base64.getEncoder().encodeToString(tokenKey1.getBytes());
        return "/secret/data/" + tokenPath + "/" + encryptedTokenKey1 + "/" + tokenKey2;
    }

    public void addToken(String path, String token, String tokenType) {

        Map<String, String> tokenMap = new HashMap<>(1);
        tokenMap.put("token", token);
        Map<String, Map<String, String>> tokenDataMap = new HashMap<>(1);
        tokenDataMap.put("data", tokenMap);

        tokenRepository.addToken(path, tokenDataMap, tokenType);
    }

    public void addToken(String path, Map<String, String> tokenMap) {
        Map<String, Map<String, String>> tokenDataMap = new HashMap<>(1);
        tokenDataMap.put("data", tokenMap);

        tokenRepository.addToken(path, tokenDataMap, "");
    }

    public void removeAllTokenByAccountId(String basePath, String tokenKey,String accessPath) {
        tokenRepository.removeAllTokenByAccountId(basePath, tokenKey,accessPath);
    }

    public VaultResponseSupport<Map> getToken(String path) {

        return tokenRepository.getToken(path);
    }

    public void removeToken(String path) {

        tokenRepository.removeToken(path);
    }

//    public boolean checkToken(String tokenKeyPattern, String token) {
//
//        Set<String> tokenKeys = tokenRepository.checkTokens(tokenKeyPattern);
//
//        System.out.println("");
//        System.out.println("-------------");
//        System.out.println("");
//        System.out.println("TOKEN TO CHECK");
//        System.out.println(token);
//        System.out.println("");
//        System.out.println("CHECK TOKEN KEYS");
//        System.out.println("");
//        System.out.println(tokenKeys);
//        System.out.println("");
//        System.out.println("-------------");
//        System.out.println("");
//
//        for (String tokenKey : tokenKeys) {
//            String redisToken = tokenRepository.getToken(tokenKey);
//            System.out.println("");
//            System.out.println("redisToken");
//            System.out.println(redisToken);
//            System.out.println("");
//
//            if (redisToken.equals(token)) {
//
//                System.out.println("----------");
//                System.out.println("");
//                System.out.println("Token Matched");
//                System.out.println("");
//                System.out.println("----------");
//
//                return true;
//            }
//        }
//
//        return false;
//    }


//    public Set getTokenByPattern(String tokenPattern) {
//        return tokenRepository.checkTokens(tokenPattern);
//    }

}
