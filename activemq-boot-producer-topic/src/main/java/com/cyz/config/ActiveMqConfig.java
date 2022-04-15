package com.cyz.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Topic;

/**
 * @Author 陈宇哲
 * @Date 2022/4/7 16:52
 */
@Configuration
@EnableJms
public class ActiveMqConfig {

    @Value("${topic}")
    private String topicName;

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(topicName);
    }
}
