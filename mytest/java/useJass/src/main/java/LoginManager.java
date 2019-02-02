import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.security.auth.callback.*;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * 将username / password 传递给 LoginModule的handler，通过 LoginContext 的构造函数进行关联
 */
class SampleCallbackHandler implements CallbackHandler {
    private String username;
    private String password;

    public SampleCallbackHandler(String username, String password) {
        System.out.println("构造 SampleCallbackHandler");
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException {
        System.out.println("SampleCallbackHandler.handle():" + callbacks.length);
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback ncb = (NameCallback) callbacks[i];
                ncb.setName(this.username);
                System.out.println("Username 回调");
            } else if (callbacks[i] instanceof PasswordCallback) {
                PasswordCallback pcb = (PasswordCallback) callbacks[i];
                pcb.setPassword(this.password.toCharArray());
                System.out.println("Password 回调");
            } else if (callbacks[i] instanceof TextOutputCallback) {
                TextOutputCallback toc = (TextOutputCallback) callbacks[i];
                switch (toc.getMessageType()) {
                    case TextOutputCallback.INFORMATION:
                        System.out.println("INFO: " + toc.getMessage());
                        break;
                    case TextOutputCallback.ERROR:
                        System.err.println("ERROR: " + toc.getMessage());
                        break;
                    case TextOutputCallback.WARNING:
                        System.err.println("WARNING: " + toc.getMessage());
                        break;
                    default:
                        throw new IOException("Unsupported message type: " + toc.getMessageType());
                }
            }
        }
    }
}

/**
 * @author liming.gong
 */
public class LoginManager {
    public static void main(String[] args) {
        try {
            final String username = "username";
            final String password = "password";

            //此处指定了使用配置文件的“Sample"验证模块，对应的实现类为SampleLoginModule
            LoginContext lc = new LoginContext("Sample", new SampleCallbackHandler(username, password));
            System.out.println("进行登录操作，如果验证失败会抛出异常");
            lc.login();
        } catch (LoginException e) {
            System.out.println("Login失败:");
            e.printStackTrace();
        } catch (SecurityException e) {
            System.out.println("Security失败:");
            e.printStackTrace();
        }
    }
}