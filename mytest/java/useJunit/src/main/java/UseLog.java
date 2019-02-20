import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author liming.gong
 */
public class UseLog {
    public static final Logger logger = Logger.getLogger("UseLog");

    public static void main(String[] args) throws IOException {
        System.out.println("arg count: " + args.length);
        logger.info("hello from info");
        logger.warning("hello from warning");

        Logger.getGlobal().log(Level.FINE, "msg");
        Logger.getGlobal().info("msg3");
        logger.addHandler(new FileHandler("%h/myapp.log", 0, 3));
        logger.info("my4");
        Logger.getGlobal().setLevel(Level.INFO);
    }
}
