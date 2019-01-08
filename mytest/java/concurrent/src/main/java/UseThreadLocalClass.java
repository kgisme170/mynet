import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class UseThreadLocalClass {
    public static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    public static void main() {
        String dateStamp = dateFormat.get().format(new Date());
        int random = ThreadLocalRandom.current().nextInt(10);
    }
}