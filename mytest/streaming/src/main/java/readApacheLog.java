import java.io.File;

public class readApacheLog {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage - java readApacheLog <读取log文件> <间隔写入的log文件,用于flume>");
            System.exit(0);
        }
        File f = new File(args[0]);
    }
}
