import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author liming.gong
 */
public class UseLog {
    public static final Logger logger = Logger.getLogger("my logger name");
    // f(T ... s) {
    //    System.out.println(s.length);
    //}
    void g(String [] s){
        System.out.println(s.length);
    }
    public static void main(String[] args) throws Exception {
        //Logger.getGlobal().log(Level.FINE, "msg");
        //Logger.getGlobal().info("msg1");
        //Logger.getGlobal().setLevel(Level.OFF);
        //Logger.getGlobal().warning("msg2");
        //Logger.getGlobal().setLevel(Level.INFO);
        //Logger.getGlobal().info("msg3");
        logger.addHandler(new FileHandler("%h/myapp.log", 0, 3));
        logger.info("my4");
        //logger的配置文件
        //java.util.logging.

        //new UseLog().f<String>("abc", "xyz");
    }
}
