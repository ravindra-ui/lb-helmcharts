package com.lats.util;


import com.lats.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.lats.util.HttpConstants.*;


@Component
public class HttpResponse {

    @Autowired
    private MessageService messageService;

    //HTTP RESPONSE FUNCTION
    public Map<String, Object> getHttpResponse(int status, String messageCode, Object data) {

        System.out.println("");
        System.out.println("GET HTTP RESPONSE");
        System.out.println("");
        System.out.println("status : " + status);
        System.out.println("messageCode : " + messageCode);
        System.out.println("data : " + data);
        System.out.println("");
        String messageBody = null;

        if (messageCode != null) {

            Integer[] messageCodeArray = new Integer[1];
            messageCodeArray[0] = Integer.parseInt(messageCode);


            Map<Integer, Map<String, Object>> messageCodeDetail = messageService.getMessages(messageCodeArray);

            System.out.println("-----------");
            System.out.println("");
            System.out.println("MESSAGE CODE NOT NULL");
            System.out.println("");
            System.out.println("messageCodeDetail");
            System.out.println(messageCodeDetail);
            System.out.println("");
            System.out.println("-----------");

            if (messageCodeDetail == null) {

                System.out.println("-----------");
                System.out.println("");
                System.out.println("Null Message Code Details");
                System.out.println("");
                System.out.println(messageCodeDetail);
                System.out.println("");
                System.out.println("-----------");

                messageBody = INVALID_MESSAGE_CODE;
                messageCode = null;

            } else {

                messageBody = (String) messageCodeDetail.get(messageCodeArray[0]).get("message_body");

            }
        }

        if (status != SUCCESS_STATUS_CODE && data != null) {

            System.out.println("--------");
            System.out.println("");
            System.out.println("MESSAGE CODE NOT SUCCESS");
            System.out.println("");

            System.out.println("Exception Data");
            System.out.println(data);
            System.out.println("");
            System.out.println("--------");
            System.out.println("");

            Map<String, String> exceptionData = (Map<String, String>) data;

            System.out.println("");
            System.out.println("Message Code Keys");
            System.out.println(exceptionData.keySet());
            System.out.println("");


            Object[] stringKeySet = exceptionData.keySet().toArray();

            Integer[] intKeySet = new Integer[stringKeySet.length];
            //    Map<String, Map<String, Object>> responseExceptionData = new HashMap<>(exceptionData.size());
            Map<String, Object> responseExceptionData = new HashMap<>(exceptionData.size());
            for (int x = 0; x <= stringKeySet.length - 1; x++) {

                if (stringKeySet[x].toString().matches("\\d+")) {
                    System.out.println("");
                    System.out.println("IN JJJJ J JJ J ");
                    intKeySet[x] = Integer.parseInt(stringKeySet[x].toString());
                    System.out.println("");
                } else {
                    responseExceptionData.put(stringKeySet[x].toString(), exceptionData.get(stringKeySet[x].toString()));
                }
            }


            if (intKeySet[0] != null) {

                Map<Integer, Map<String, Object>> messageData = messageService.getMessages(intKeySet);

                for (Integer key : messageData.keySet()) {

                    System.out.println("");
                    System.out.println("Key");
                    System.out.println(key);
                    System.out.println("");


                    String fieldName = exceptionData.get(key.toString());
                    Map<String, Object> messageDetails = new HashMap<>(2);
                    messageDetails.put("message_code", messageData.get(key).get("message_code"));
                    messageDetails.put("message_body ", messageData.get(key).get("message_body"));
                    responseExceptionData.put(fieldName, messageDetails);

                }
            }

            data = responseExceptionData;

            //     {801={id=923c6de0-915d-11e8-a363-9581d3d51fd6, lang_code=en, message_code=801, message_body=Some input are not properly entered. Please try to enter proper input., message_title=INVALID_INPUT}, 518={id=75ea52c9-9fa6-4908-9329-a43295a2c144, lang_code=en, message_code=518, message_body=currency code is blank, Please fill it., message_title=CURRENCY_CODE_BLANK}, 519={id=9539ae29-0eb0-4fba-8ddb-a2bf5a3c30e6, lang_code=en, message_code=519, message_body=Holder name is blank, Please fill it, message_title=HOLDER_NAME_BLANK}}

        }


        Map<String, Object> response = new HashMap<>(2);
        response.put("status", status);
        if (data != null) {
            response.put("data", data);
        }
        if (messageCode != null) {
            response.put("message_code", Integer.parseInt(messageCode));
        }
        if (messageBody != null) {
            response.put("message", messageBody);
        }


        return response;
    }


  /*

    message_code 1001
    message_title "ACCESS_TOKEN_REQUIRED"
    message_body "Invalid Input. Please try again";


    ACCESS_TOKEN_REQUIRED = 1001;


    {
        status : 400,
                message_code : 1001,
            mesage : "Invalid Input. Please try again",
            data :
        {
        }
    }

    */

}
