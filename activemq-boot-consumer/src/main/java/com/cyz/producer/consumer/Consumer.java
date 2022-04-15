package com.cyz.producer.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @Author 陈宇哲
 * @Date 2022/4/6 15:04
 */
@Component
public class Consumer {

    @JmsListener(destination = "${myqueue}")
    public void reciverMsg(TextMessage message) throws JMSException {

                System.out.println("接收到消息---"+message.getText()+"！！！");

    }
}
