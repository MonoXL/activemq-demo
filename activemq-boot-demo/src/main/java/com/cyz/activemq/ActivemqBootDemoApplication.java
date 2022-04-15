package com.cyz.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ActivemqBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqBootDemoApplication.class, args);
    }

}
