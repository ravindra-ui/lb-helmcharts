package com.lats.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.net.ConnectException;
import java.util.Map;

import static com.lats.util.HttpConstants.*;


@ControllerAdvice(basePackages = "com.lats.controller")
public class ControllerExceptionHandler {



    @Autowired
    private HttpResponse HttpResponse;

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(NullPointerException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println(exception);
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");


        return HttpResponse.getHttpResponse(INTERNAL_SERVER_ERROR_STATUS_CODE, NULL_POINTER, null);

    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(IllegalArgumentException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");


        return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

    }

    @ExceptionHandler(ClaimJwtException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(ClaimJwtException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_INPUT, null);


    }


    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(ExpiredJwtException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, TOKEN_EXPIRED, null);

    }


    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(MalformedJwtException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");


        return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);

    }

    @ExceptionHandler(PrematureJwtException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(PrematureJwtException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);

    }

    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(SignatureException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);

    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(UnsupportedJwtException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(MissingServletRequestParameterException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(MethodArgumentTypeMismatchException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(UNAUTHORIZED_STATUS_CODE, INVALID_TOKEN, null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(ConstraintViolationException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");


        return HttpResponse.getHttpResponse(BAD_REQUEST_STATUS_CODE, INVALID_INPUT, null);

    }



    @ExceptionHandler(ResourceAccessException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(ResourceAccessException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println(exception);
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(SERVICE_UNAVAILABLE_STATUS_CODE, SERVICE_UNAVAILABLE, null);

    }


    @ExceptionHandler(ConnectException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(ConnectException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println(exception);
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(SERVICE_UNAVAILABLE_STATUS_CODE, SERVICE_UNAVAILABLE, null);

    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(HttpClientErrorException exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println(exception);
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(SERVICE_UNAVAILABLE_STATUS_CODE, SERVICE_UNAVAILABLE, null);

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> exceptionHandling(Exception exception) {

        System.out.println("");
        System.out.println("--------------");
        System.out.println("");
        System.out.println("EXCEPTION MESSAGE");
        System.out.println("");
        System.out.println(exception.getMessage());
        System.out.println("");
        System.out.println("EXCEPTION OCCURDED");
        System.out.println(exception.getClass());
        System.out.println("");
        System.out.println(exception);
        System.out.println("");
        System.out.println("--------------");
        System.out.println("");

        return HttpResponse.getHttpResponse(INTERNAL_SERVER_ERROR_STATUS_CODE, TECHNICAL_ERROR, null);

    }

}
