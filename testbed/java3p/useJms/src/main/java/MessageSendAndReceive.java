/**
 * 消息的消费者接收消息可以采用两种方式：
 * 1、consumer.receive() 或 consumer.receive(int timeout)；
 * 2、注册一个MessageListener。
 * 采用第一种p2p方式，消息的接收者会一直等待下去，直到有消息到达，或者超时。
 * 后一种异步方式会注册一个监听器，当有消息到达的时候，会回调它的onMessage()方法。
 */

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class MessageSendAndReceive {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();
        //创建消息的Destination
        Queue queue = new ActiveMQQueue("testQueue");

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建将要发送的消息
        Message message = session.createTextMessage("Hello JMS!");

        MessageProducer producer = session.createProducer(queue);
        producer.send(message);
        System.out.println("Send Message Completed!");

        MessageConsumer comsumer = session.createConsumer(queue);
        comsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message msg) {
                TextMessage textMsg = (TextMessage) msg;
                try {
                    System.out.println("接收到消息: " + textMsg.getText());
                    System.exit(0);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}