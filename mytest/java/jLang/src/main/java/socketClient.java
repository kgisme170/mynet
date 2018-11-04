import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class socketClient {
    public static void main(String argv[]) throws IOException {
        try {
            Socket clientSocket = new Socket("localhost", 1234);
        } catch (UnknownHostException e) {
            System.err.println("Couldn't find Host");
        }
    }
}
