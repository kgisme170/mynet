import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
/**
 * @author liming.gong
 */

public class Consumer extends MqConfig {
    MessageConsumer messageConsumer;

    public void run() {
        try {
            init(false);
            messageConsumer = session.createConsumer(destination);

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if (textMessage != null) {
                    System.out.println("收到的消息:" + textMessage.getText());
                } else {
                    break;
                }
            }
            messageConsumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Consumer().run();
    }
}