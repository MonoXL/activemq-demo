package com.cyz.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.apache.xbean.spring.context.SpringApplicationContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author 陈宇哲
 * @Date 2022/4/2 14:44
 */
@Component
public class SpringMqProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMqProducer springMqProducer = (SpringMqProducer)ctx.getBean("springMqProducer");
        springMqProducer.jmsTemplate.send(session -> session.createTextMessage("这是spring整合activemq的一条消息"));
        System.out.println("***消息已发送至指定对列！！！");
    }
}
