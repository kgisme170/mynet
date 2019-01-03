import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
class SocketThread implements Runnable {
    Socket incoming;

    public SocketThread(Socket s) {
        incoming = s;
    }

    @Override
    public void run() {
        try {
            InputStream in = incoming.getInputStream();
            OutputStream out = incoming.getOutputStream();
            Scanner sc = new Scanner(in, "UTF-8");
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true);
            pw.println("Ni hao");
            /*半关闭可以指定 incoming.shutdownOutput();*/

            boolean bye = false;
            while (!bye && sc.hasNextLine()) {
                String line = sc.nextLine();
                pw.println("Echo: " + line);
                if ("BYE".equalsIgnoreCase(line.trim())) {
                    bye = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/**
 * @author liming.gong
 */
public class EchoServer {

    public static void main(String[] args) {
        try (ServerSocket s = new ServerSocket(8100)) {
            Socket incoming = s.accept();
            SocketThread st = new SocketThread(incoming);
            Thread t = new Thread(st);
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}