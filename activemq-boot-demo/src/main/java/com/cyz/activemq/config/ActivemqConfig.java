package com.cyz.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

/**
 * @Author 陈宇哲
 * @Date 2022/4/2 19:28
 */
@Configuration
@EnableJms
public class ActivemqConfig {

    @Value("${myqueue}")
    private String queueName;

    @Bean
    public ActiveMQQueue getQueue(){
        return new ActiveMQQueue(queueName);
    }
}
