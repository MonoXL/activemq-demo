package com.cyz.activemq;

import com.cyz.activemq.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@SpringBootTest(classes = ActivemqBootDemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ActivemqBootDemoApplicationTests {

    @Autowired
    private Producer producer;

    @Test
    public void testSendMessage() {
        producer.sendMessage();
    }

}
