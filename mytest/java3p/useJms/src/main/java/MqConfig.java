import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author liming.gong
 */
public class MqConfig {
    static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
    Connection connection = null;
    Session session;
    Destination destination;

    void init(boolean isProducer) {
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("myQueue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}