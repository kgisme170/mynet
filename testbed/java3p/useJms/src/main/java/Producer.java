import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author liming.gong
 */
public class Producer extends MqConfig {
    MessageProducer messageProducer;

    void run() {
        try {
            init(true);
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Producer().run();
    }

    public void sendMessage(Session session, MessageProducer messageProducer) throws Exception {
        final int num = 10;
        for (int i = 0; i < num; i++) {
            TextMessage message = session.createTextMessage("ActiveMQ send message" + i);
            System.out.println("Send message：ActiveMQ send message" + i);
            messageProducer.send(message);
        }
        messageProducer.close();
        session.commit();
        session.close();
        connection.close();
    }
}