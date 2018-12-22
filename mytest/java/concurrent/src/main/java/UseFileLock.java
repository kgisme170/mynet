import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.*;

/**
 * @author liming.gong
 */

public class UseFileLock {
    public static void main(String[] args) {
        try {
            FileChannel channel = new FileInputStream("/tmp/file1").getChannel();
            FileLock lock = channel.lock();
            lock.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}