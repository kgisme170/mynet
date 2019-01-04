import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liming.gong
 */
public class MyHttpClient {
    public static void main(String[] args) {
        try {
            MyHttpClient httpClient = new MyHttpClient();
            httpClient.get();
            httpClient.post();
            httpClient.httpGet();
            httpClient.httpPost();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void get() throws Exception {
        URL url = new URL("http://localhost:8090/myServer/myService?name=johnsmith&age=23");
        URLConnection urlConnection = url.openConnection();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        System.out.println(sb.toString());
    }

    public void post() throws IOException {
        URL url = new URL("http://localhost:8090/myServer/myService");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("charset", "utf-8");

        PrintWriter pw = new PrintWriter(
                new BufferedOutputStream(httpURLConnection.getOutputStream()));
        pw.write("name=welcome");
        pw.write("&age=14");
        pw.flush();
        pw.close();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        System.out.println(sb.toString());
    }

    public void httpGet() throws Exception{
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:8090/myServer/myService?name=johnsmith&age=23");
        HttpResponse response = client.execute(get);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity,"utf-8");
        System.out.println(result);
    }

    public void httpPost() throws Exception{
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8090/myServer/myService");
        List<BasicNameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("name", "张三"));
        parameters.add(new BasicNameValuePair("age", "25"));
        post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
        HttpResponse response = client.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity,"utf-8");
        System.out.println(result);
    }
}