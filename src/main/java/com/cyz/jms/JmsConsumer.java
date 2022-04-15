package com.cyz.jms;

import com.sun.xml.internal.ws.transport.http.WSHTTPConnection;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author 陈宇哲
 * @Date 2022/3/24 15:38
 */
public class JmsConsumer {

    private static final String BROKE_URI = "tcp://192.168.3.4:61616";
    private static final String QUEUE_NAME = "queue01";
    private static final String TOPIC_NAME = "topic01";

    /**
     * reciver方式消费消息
     * @param args
     * @throws JMSException
     */
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        while(true){
            TextMessage message = (TextMessage)consumer.receive();
            String text = message.getText();
            if(text != null){
                System.out.println("接收到消息"+text+"！！！");
            }else{
                break;
            }
        }
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * listener方式消费消息
     */
    @Test
    public void testListener() throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @SneakyThrows
            public void onMessage(Message message) {
                if(null != message && message instanceof TextMessage){
                    System.out.println("接收到消息"+((TextMessage)message).getText());
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * 以reciver的方式接收消息
     * @throws JMSException
     */
    @Test
    public void testReciverTopic() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer consumer = session.createConsumer(topic);
        while(true){
            Message message = consumer.receive();
            if(null != message && message instanceof TextMessage){
                System.out.println("接收到消息---"+((TextMessage)message).getText());
            }else{
                break;
            }
        }
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testListenerTopic() throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener((message)-> {
            if(null != message && message instanceof TextMessage){
                try {
                    System.out.println("接收到消息"+((TextMessage)message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void 服务发布订阅之consumer() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKE_URI);
        Connection connection = factory.createConnection();
        connection.setClientID("z3");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "remark");
        connection.start();
        Message message = subscriber.receive();
        while(null != message){
            if(message instanceof TextMessage){
                System.out.println("接收到消息"+((TextMessage)message).getText()+"！！！");
            }
            message = subscriber.receive(2000L);
        }
        subscriber.close();
        session.close();
        connection.close();
    }
}
