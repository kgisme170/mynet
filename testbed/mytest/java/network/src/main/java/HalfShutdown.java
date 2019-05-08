import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liming.gong
 */
public class HalfShutdown {
    static final int PORT = 8108;

    public static void main(String[] args) {
        try {
            System.out.println("启动客户端1");
            new Thread(() -> {
                try {
                    System.out.println("启动客户端2");
                    Thread.sleep(1000);
                    System.out.println("启动客户端3");
                    Socket socket = new Socket("localhost", PORT);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = bufferedInputStream.read(bytes)) != -1) {
                        System.out.println("客户端读取=" + new String(bytes, 0, len) + '\n');
                    }
                    System.out.println("客户shutdownInput 1");
                    socket.shutdownInput();
                    System.out.println("客户shutdownInput 2");
                    Thread.sleep(1000);

                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
                    bufferedOutputStream.write("Client ends".getBytes());
                    socket.shutdownOutput();

                    bufferedInputStream.close();
                    bufferedInputStream.close();
                    socket.close();
                    System.out.println("客户端关闭");
                } catch (Exception e) {
                    System.out.println("客户线程1");
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            System.out.println("客户线程2");
            e.printStackTrace();
        }
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("开始服务端");
                Socket socket = serverSocket.accept();
                System.out.println("开始接收客户端");
                System.out.println("socket = " + socket);
                new Thread(() -> {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write("message1".getBytes());
                        outputStream.write("message2".getBytes());
                        System.out.println("服务端写入结束");
                        socket.shutdownOutput();
                        Thread.sleep(1000);

                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        byte[] bytes = new byte[1024];
                        int len;
                        while ((len = bufferedInputStream.read(bytes)) != -1) {
                            System.out.println(new String(bytes, 0, len));
                        }
                        socket.shutdownInput();
                        System.out.println("服务器端关闭");

                        bufferedInputStream.close();
                        inputStream.close();
                        outputStream.close();
                        socket.close();
                    } catch (Exception e) {
                        System.out.println("服务线程1");
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            System.out.println("服务线程2");
            e.printStackTrace();
        }
    }
}