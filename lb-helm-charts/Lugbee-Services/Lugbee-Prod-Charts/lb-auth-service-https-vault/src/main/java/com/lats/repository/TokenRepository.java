package com.lats.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultResponseSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;

@Repository
public class TokenRepository {

    //REDIS TOKEN CONSTANTS
    private static final String TOKEN_KEY = "accessTokens";
    @Autowired
    VaultOperations vaultOperations;

    @Value("${vault.auth-expireTime}")
    private String AUTH_EXPIRETIME;

    @Value("${vault.verify-expireTime}")
    private String VERIFY_EXPIRETIME;

//    @Autowired
//    private RedisTemplate redisTemplate;
//    private ValueOperations valueOperations;

//    public TokenRepository(RedisTemplate redisTemplate) {
//
//        this.redisTemplate = redisTemplate;
//        valueOperations = redisTemplate.opsForValue();
//
//    }

    //Update by Chetna Joshi on 02-12-20
    public void addToken(String path, Map<String, Map<String, String>> token, String tokenType) {

        String metadataPath = path.replaceAll("data","metadata");
        Map<String, Object> metadataMap = new HashMap<>(3);
        metadataMap.put("cas_required", false);

        if (tokenType.equals("auth")) {
            metadataMap.put("delete_version_after", AUTH_EXPIRETIME);
            vaultOperations.write(metadataPath, metadataMap);
        } else if (tokenType.equals("verify")) {
            metadataMap.put("delete_version_after", VERIFY_EXPIRETIME);
            vaultOperations.write(metadataPath, metadataMap);
        }

        VaultResponse vaultResponse = vaultOperations.write(path, token);

        System.out.println("token added, ");
        System.out.println(vaultResponse.getData());
    }

    public List<String> getListOfKeys(String key, String accessPath) {
        return vaultOperations.list("/secret/metadata/" + accessPath + "/" + key);
    }

    public void removeAllTokenByAccountId(String basePath, String tokenKey, String accessPath) {
        List<String> keys = getListOfKeys(tokenKey, accessPath);
        for (String key : keys) {
            String path = basePath + "/" + key;
            vaultOperations.delete(path);
        }
    }

//    public void addToken(String tokenKey, String token) {
//
//        valueOperations.set(tokenKey, token);
//    }

    public VaultResponseSupport<Map> getToken(String path) {
        VaultResponseSupport<Map> vaultResponse = vaultOperations.read(path, Map.class);
        return vaultResponse;
    }
//    public String getToken(String tokenKey) {
//        return (String) valueOperations.get(tokenKey);
//    }

    public void removeToken(String path) {
        vaultOperations.delete(path);
    }
//    public boolean removeToken(String tokenKey) {
//
//        return redisTemplate.delete(tokenKey);
//    }

//    public Set checkTokens(String tokenKeyPattern) {
//        return redisTemplate.keys(tokenKeyPattern + "*");
//    }

  /*  public void addToken(String tokenKey, String token) {

        hashOperations.put(TOKEN_KEY, tokenKey, token);
    }

    public String getToken(String tokenKey) {

        return (String) hashOperations.get(TOKEN_KEY, tokenKey);

    }

    public long removeToken(String tokenKey) {

        return hashOperations.delete(TOKEN_KEY, tokenKey);
    }
*/

}
