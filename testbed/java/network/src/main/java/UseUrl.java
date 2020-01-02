import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author liming.gong
 */
public class UseUrl {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            for (Map.Entry<String, List<String>> entry : urlConnection.getHeaderFields().entrySet()) {
                String k = entry.getKey();
                for (String v : entry.getValue()) {
                    System.out.println("k = " + k + ", v = " + v);
                }
            }
            System.out.println("----");
            System.out.println(urlConnection.getContentType());
            System.out.println(urlConnection.getConnectTimeout());
            System.out.println(urlConnection.getContentLength());
            System.out.println(urlConnection.getContentLengthLong());
            System.out.println(urlConnection.getContentEncoding());
            System.out.println(urlConnection.getAllowUserInteraction());
            System.out.println(urlConnection.getExpiration());
            System.out.println(urlConnection.getLastModified());
            System.out.println(urlConnection.getDate());
            System.out.println(urlConnection.getDoInput());
            System.out.println(urlConnection.getDoOutput());
            System.out.println("----");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}