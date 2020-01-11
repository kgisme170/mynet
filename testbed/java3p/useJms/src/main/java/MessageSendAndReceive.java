/**
 * 2 ways for receiver to get message
 * 1. consumer.receive() 或 consumer.receive(int timeout)；
 * 2. register a MessageListener。
 * First method uses p2p, receiver will wait until message arrives
 * Second method will use async and register a monitor, callback its onMessage when receives message
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
        //Destination for message creation
        Queue queue = new ActiveMQQueue("testQueue");

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //create message to send
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
                    System.out.println("Receives message: " + textMsg.getText());
                    System.exit(0);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}