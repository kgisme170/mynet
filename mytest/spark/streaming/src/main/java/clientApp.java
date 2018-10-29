import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class clientApp {
    public static void main(String[] args) {
        try {
            System.out.println("Defining new Socket");
            ServerSocket soc = new ServerSocket(9087);
            System.out.println("Waiting for Incoming Connection");
            Socket clientSocket = soc.accept();
            System.out.println("Connection Received");
            OutputStream os = clientSocket.getOutputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                PrintWriter out = new PrintWriter(os, true);
                System.out.println(">");
                String data = read.readLine();
                System.out.println("Write to socket");
                out.println(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}