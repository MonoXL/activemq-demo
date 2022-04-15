package com.cyz.jms;

import jdk.nashorn.internal.ir.CallNode;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @Author 陈宇哲
 * @Date 2022/3/24 14:30
 */
public class JmsProducer implements Serializable {

    private static final String BROKE_URI = "tcp://192.168.3.4:61616";
    private static final String QUEUE_NAME = "queue01";
    private static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            producer.send(textMessage);
        }
        producer.close();
        session.close();
        connection.close();
        System.out.println("消息已发送至"+QUEUE_NAME+"队列！！！");
    }

    /**
     * 绑定主题，给主题发送消息
     * @throws JMSException
     */
    @Test
    public void testTopic() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("topic_message" + i);
            producer.send(textMessage);
        }
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void 消息头消息体消息属性() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage("aaa");

        //设置消息头相关属性
        //1.持久化设置  DeliveryMode.NON_PERSISTENT：非持久的 -- DeliveryMode.PERSISTENT：持久的
        message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //2.设置优先级  0-4代表普通 -- 5-9代表加急 （没有绝对的先后顺序，但加急一定比普通先达）
        message.setJMSPriority(9);
        //3.设置过期时间
        message.setJMSExpiration(1000L);
        //4.设置唯一标识
        message.setJMSMessageID(UUID.randomUUID().toString());
        //5.设置消息投送地
        message.setJMSDestination(queue);

        //设置消息体相关属性  主要是消息的数据类型
        TextMessage stringMessage = session.createTextMessage();//字符串类型
        MapMessage mapMessage = session.createMapMessage();//键值对类型
        BytesMessage bytesMessage = session.createBytesMessage();//字节数组类型
        ObjectMessage objectMessage = session.createObjectMessage();//包含可序列化对象
        StreamMessage streamMessage = session.createStreamMessage();//流类型

        stringMessage.setText("stringMessage");
        mapMessage.setString("k","v");//若值是其他类型，就选用其他api
        bytesMessage.writeBytes(new byte[1]);
        objectMessage.setObject(new JmsProducer());
        streamMessage.writeString("streamMesage");//若值是其他类型，就选用其他api

        //设置消息属性
        stringMessage.setStringProperty("c01","vip");
        stringMessage.setIntProperty("c01",1);//若是其他类型，就选用其他api

    }

    @Test
    public void 持久化设置之queue() throws JMSException {
        /**
         * 结论，发送的消息若不做持久化的配置，则默认是持久的，意思是MQ宕机后重启消息不会丢失，消费者仍可以接收
         */
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("stringMessage");
        textMessage.setJMSDestination(queue);
        textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void 服务订阅发布之producer() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
        connection.start();
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("stringMessage");
        textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }
}
