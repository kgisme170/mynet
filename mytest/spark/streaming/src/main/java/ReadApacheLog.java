import java.io.*;
/**
 * @author liming.glm
 */
public class ReadApacheLog {
    public static void main(String[] args) {
        final int len = 2;
        if (args.length != len) {
            System.out.println("Usage - java ReadApacheLog <读取log文件> <间隔写入的log文件,用于flume>");
            System.exit(0);
        }
        File read = new File(args[0]);
        File write = new File(args[1]);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(read));
            FileOutputStream writer = new FileOutputStream(write);
            while (true) {
                writer.write((reader.readLine() + "\n").getBytes());
                writer.flush();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
