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

/**
 * 要使得log的配置文件有效，必须采用jvm选项，-D需要出现在class name之前。
 * 出现在class name后面的是程序运行时参数args的部分
 * 例如以下用法：
 *
 *
 $java -Djava.util.logging.config.file=myConfig.txt UseLog
 arg count: 0
 一月 07, 2019 5:50:30 下午 UseLog main
 警告: hello from warning
 *
 */