package com.lats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}");
        SpringApplication.run(Application.class);
        System.out.println("-----------");
        System.out.println("");
        System.out.println("lats Service is Started");
        System.out.println("");
        System.out.println("-----------");
    }
}
