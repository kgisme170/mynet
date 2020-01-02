import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author liming.gong
 */
public class SocketInterrupt {
    static int PORT = 8111;
    static int LOOP = 5;
    public static void useSocket() {
        Thread t = new Thread(() -> {
            Socket socket = null;
            try {
                socket = new Socket("localhost", PORT);
                Thread.sleep(500);
                InputStream inputStream = socket.getInputStream();

                byte[] bytes = new byte[1024];
                for (int i = 0; i < LOOP; ++i) {
                    int readBytes = inputStream.read(bytes);
                    System.out.println(new String(bytes, 0, readBytes));
                }
                inputStream.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("被中断");
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        t.start();
    }
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            SocketChannel channel = null;
            try {
                channel = SocketChannel.open(
                        new InetSocketAddress("localhost", PORT));
                Thread.sleep(500);
                Scanner in = new Scanner(channel);
                while(!Thread.currentThread().isInterrupted()) {
                    while (in.hasNextLine()) {
                        System.out.println(in.nextLine());
                    }
                }
                in.close();
                channel.close();
            } catch (Exception e) {
                System.out.println("被中断");
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("关闭channel");
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        t.start();
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                while (true) {
                    System.out.println("socket listen");
                    Socket socket = serverSocket.accept();
                    System.out.println("socket accepted");
                    OutputStream outputStream = socket.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    for (int i = 0; i < LOOP; ++i) {
                        Thread.sleep(200);
                        //outputStream.write(("abc" + i).getBytes()); 用于配合useSocket函数使用
                        bufferedWriter.write("abc" + i);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                    outputStream.close();
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            Thread.sleep(800);
            t.interrupt();
        } catch (Exception e) {
            System.out.println("主线程异常");
            e.printStackTrace();
        }
    }
}