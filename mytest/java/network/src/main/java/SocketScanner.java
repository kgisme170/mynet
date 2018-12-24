import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author liming.gong
 */
public class SocketScanner {
    public static void main(String[] args) {
        String host = "time-a.nist.gov";
        try (Socket s = new Socket(host, 13);
             Scanner in = new Scanner(s.getInputStream(), "UTF-8")) {
            s.setSoTimeout(1000);
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
            byte[] addressBytes = InetAddress.getByName(host).getAddress();
            System.out.println(addressBytes);
            System.out.println(InetAddress.getLocalHost());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}