package com.cyz.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author 陈宇哲
 * @Date 2022/4/2 14:44
 */
@Component
public class SpringMqConsumer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMqConsumer springMqConsumer = (SpringMqConsumer)ctx.getBean("springMqConsumer");
        String retValue = (String)springMqConsumer.jmsTemplate.receiveAndConvert();
        System.out.println("***消费者接收到的消息为："+retValue);
    }
}
