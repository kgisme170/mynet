import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;

/**
 * @author liming.gong
 */
public class UseFileLock {
    public static void useReadLock() {
        try {
            InputStream fi = new FileInputStream("logfile.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fi));
            System.out.println(br.readLine());
            FileChannel fileChannel = ((FileInputStream) fi).getChannel();
            /**
             * 如果是无参数的 lock 函数或者 shared = false
             * 则对于只读的文件 Channel 会抛出 java.nio.channels.NonWritableChannelException
             */
            FileLock lock = fileChannel.lock(0L, fileChannel.size(), true);
            Thread.sleep(3000);
            lock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    FileChannel fileChannel = new FileOutputStream(
                            "logfile.txt").getChannel();
                    FileLock lock = fileChannel.lock();
                    fileChannel.write(ByteBuffer.wrap((new Date().toString()).getBytes()));
                    System.out.println("sub thread ends");
                    lock.release();
                } catch (Exception e) { // java.nio.channels.OverlappingFileLockException
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            OutputStream out = new FileOutputStream("logfile.txt");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            FileChannel fileChannel = ((FileOutputStream) out).getChannel();
            /**
             * 如果是无参数的 lock 函数或者 shared = false
             * 则对于只读的文件 Channel 会抛出 java.nio.channels.NonWritableChannelException
             */
            FileLock lock = fileChannel.lock();
            Thread.sleep(2000);
            lock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}