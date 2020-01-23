import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author liming.gong
 */
public class UseThreadLocalClass {
    public static final ThreadLocal<SimpleDateFormat> DATE_FORMAT =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    public static void main() {
        String dateStamp = DATE_FORMAT.get().format(new Date());
        int random = ThreadLocalRandom.current().nextInt(10);
    }
}