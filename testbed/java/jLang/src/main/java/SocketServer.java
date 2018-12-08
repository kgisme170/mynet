import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * @author liming.gong
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Server created, waiting for client");
        Socket socket = server.accept();
        System.out.println("Client has connected");
    }
}
