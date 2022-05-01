package com.lats.util;

import com.lats.constants.ServiceAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class RestTemplateUtil {

    @Autowired
    private RestTemplate restTemplate;

    /*@Value("${service.adminLogIn}")
    private String adminLogIn;*/

    @Value("${services.ltms.address}" + ":" + "${services.ltms.port}" + ServiceAPIs.Ltms.adminLogIn)
    private String adminLogIn;

    @Value("${services.ltms.address}" + ":" + "${services.ltms.port}")
    private String testLocalAPI;

    public Map<String, Object> adminLogIn(String username, String password) {

        MultiValueMap<String, String> adminLogInParams = new LinkedMultiValueMap();
        adminLogInParams.add("username", username);

        HttpHeaders adminLogInHeader = new HttpHeaders();
        adminLogInHeader.add("password", password);

        HttpEntity<MultiValueMap<String, String>> adminLogInHttpEntity = new HttpEntity(adminLogInParams, adminLogInHeader);

        System.out.println("");
        System.out.println("Admin Login Http Entity");
        System.out.println(adminLogInHttpEntity);
        System.out.println("");

        Map<String, Object> adminLogInResponse = restTemplate.postForObject(adminLogIn, adminLogInHttpEntity, Map.class);

        System.out.println("-------------");
        System.out.println("");
        System.out.println("adminLogInResponse");
        System.out.println(adminLogInResponse);
        System.out.println("");
        System.out.println("-------------");

        return adminLogInResponse;
    }

    public String testAPILocal(){

        String url = testLocalAPI + "/ltms";
        System.out.println("");
        System.out.println("Test Local API URL");
        System.out.println(url);
        System.out.println("");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> httpEntity = new HttpEntity(new HttpHeaders());

        ResponseEntity<String> callResponse = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        System.out.println("");
        System.out.println("API CALL RESPONSE ENTITY LOCAL");
        System.out.println(callResponse);
        System.out.println("");
        System.out.println("API CALL RESPONSE LOCAL");
        System.out.println(callResponse.getBody());
        System.out.println("");

        return callResponse.getBody();
    }



    public String testAPI(){

        String url =  "https://team.lugbee.com:443/ltms";
        // String url =   "http://localhost:8086/ltms";

        System.out.println("");
        System.out.println("Calling URL ");
        System.out.println(url);
        System.out.println("");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> httpEntity = new HttpEntity(new HttpHeaders());

        ResponseEntity<String> callResponse = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        System.out.println("");
        System.out.println("API CALL RESPONSE ENTITY");
        System.out.println(callResponse);
        System.out.println("");
        System.out.println("API CALL RESPONSE");
        System.out.println(callResponse.getBody());
        System.out.println("");

        return callResponse.getBody();
    }

    public String testAPI(String namespace, String serviceName){

        String url =  "https://" +  namespace + ".lugbee.com:443/" + serviceName;
       // String url =   "http://localhost:8086/ltms";

        System.out.println("");
        System.out.println("Calling URL ");
        System.out.println(url);
        System.out.println("");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> httpEntity = new HttpEntity(new HttpHeaders());

        ResponseEntity<String> callResponse = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        System.out.println("");
        System.out.println("API CALL RESPONSE ENTITY");
        System.out.println(callResponse);
        System.out.println("");
        System.out.println("API CALL RESPONSE");
        System.out.println(callResponse.getBody());
        System.out.println("");

        return callResponse.getBody();
    }
}
