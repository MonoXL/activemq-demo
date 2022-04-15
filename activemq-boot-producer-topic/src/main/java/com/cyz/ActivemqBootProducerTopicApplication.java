package com.cyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ActivemqBootProducerTopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqBootProducerTopicApplication.class, args);
    }

}
