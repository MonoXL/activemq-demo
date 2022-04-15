package com.cyz.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @Author 陈宇哲
 * @Date 2022/4/7 16:59
 */
@Component
public class Consumer {

    @JmsListener(destination = "${topic}")
    public void reciveMsg(TextMessage message) throws JMSException {
        System.out.println("recive msg -- " + message.getText());
    }
}
