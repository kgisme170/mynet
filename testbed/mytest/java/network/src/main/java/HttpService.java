import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author liming.gong
 */
public class HttpService extends HttpServlet {
    private String helloMsg = "hello";

    public HttpService() {
    }

    public HttpService(String m) {
        helloMsg = m;
    }

    private void respond(HttpServletRequest request, HttpServletResponse response, String msg) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        PrintWriter pw = response.getWriter();
        pw.print(helloMsg + "使用" + msg + "方式请求该Servlet。<br/>" + "name = " + name + ",age = " + age);
        pw.flush();
        pw.close();
    }

    /**
     * 在浏览器里面
     * http://localhost:8090/myServer/myService?name=johnsmith&age=23
     * <p>
     * 会调用到这里面
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        respond(request, response, "GET");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        respond(request, response, "POST");
    }

    @Override
    public void init() {
        System.out.println("HttpService init()");
    }

    @Override
    public void destroy() {
        System.out.println("HttpService destroy()");
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8090);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/myServer");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HttpService()), "/myService");
        context.addServlet(new ServletHolder(new HttpService("v2")), "/myService2/v2");
        server.start();
        server.join();
    }
}