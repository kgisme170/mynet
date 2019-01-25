package my;

import java.util.ServiceLoader;
/**
 * @author liming.gong
 */
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Command> serviceLoader = ServiceLoader.load(Command.class);
        for (Command command : serviceLoader) {
            command.run();
        }
    }
}