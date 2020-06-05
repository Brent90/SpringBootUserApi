package com.slinger.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SpringBootAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAPIApplication.class, args);
    }

}
