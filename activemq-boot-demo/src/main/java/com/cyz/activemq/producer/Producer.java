package com.cyz.activemq.producer;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author 陈宇哲
 * @Date 2022/4/2 19:31
 */
@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private ActiveMQQueue queue;

    public void sendMessage(){
        jmsMessagingTemplate.convertAndSend(queue,"springboot整合activemq发送的一条消息");
    }

    @Scheduled(fixedDelay = 3000)
    public void sendMsgScheduled(){
        jmsMessagingTemplate.convertAndSend(queue,"springboot整合activemq发送的一条消息");
        System.out.println("发送消息完毕！");
    }
}
