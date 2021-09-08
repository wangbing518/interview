package com.myself.interview;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);
            log.info("应用程序启动完毕!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
