package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsTopicConsumer {
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws Exception {
        //testReceiveMethod();
        testListenMethod();
    }

    private static void testListenMethod() throws JMSException, IOException {
        System.out.println("topic 01...");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer consumer = session.createConsumer(topic);
        /**
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println("receive topic msg:" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        */
        //consumer.setMessageListener((Message message) ->{
        consumer.setMessageListener(message ->{
            if(message != null && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("receive topic msg:" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
        System.out.println("*****finish******");
    }
//
//    /**
//     * receive
//     * @throws JMSException
//     */
//    private static void testReceiveMethod() throws JMSException {
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
//        Connection connection = factory.createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue(QUEUE_NAME);
//        MessageConsumer consumer = session.createConsumer(queue);
//        while(true){
//            //TextMessage textMessage = (TextMessage)consumer.receive();
//            TextMessage textMessage = (TextMessage)consumer.receive(3000L);
//            if(null != textMessage){
//                System.out.println("receive msg:" + textMessage.getText());
//            }else {
//                break;
//            }
//        }
//        consumer.close();
//        session.close();
//        connection.close();
//        System.out.println("*****finish******");
//    }
}
