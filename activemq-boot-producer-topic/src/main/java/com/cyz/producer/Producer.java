package com.cyz.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

/**
 * @Author 陈宇哲
 * @Date 2022/4/7 16:54
 */
@Component
public class Producer {

    @Autowired
    private Topic topic;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Scheduled(fixedDelay = 3000)
    public void sendMsgScheduled(){
        jmsMessagingTemplate.convertAndSend(topic, "msg body is --- "+UUID.randomUUID().toString().replaceAll("-","").substring(0,7));
        System.out.println("msg send over");
    }
}
