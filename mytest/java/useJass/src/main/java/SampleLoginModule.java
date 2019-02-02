import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

class SamplePrincipal implements Principal {
    private String name;

    public SamplePrincipal(String name) {
        System.out.println("构造 SamplePrincipal");
        this.name = name;
    }

    @Override
    public String getName() {
        System.out.println("-------------getName");
        return name;
    }

    @Override
    public boolean equals(Object ob) {
        System.out.println("-------------equals");
        if (ob instanceof SamplePrincipal) {
            SamplePrincipal principal = (SamplePrincipal) ob;

            return this.name.equalsIgnoreCase(principal.getName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        System.out.println("-------------hashCode");
        return name.toUpperCase().hashCode();
    }
}

public class SampleLoginModule implements LoginModule {
    private boolean isAuthenticated = false;
    private CallbackHandler callbackHandler;
    private Subject subject;
    private SamplePrincipal principal;

    public SampleLoginModule() {
        System.out.println("构造 SampleLoginModule");
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        System.out.println("initialize-----------");
        System.out.println("subject = " + subject);
        for(Map.Entry<String, ?> entry : sharedState.entrySet()) {
            System.out.println("sharedState entry = [" + entry + "]");
        }
        for(Map.Entry<String, ?> entry : options.entrySet()) {
            System.out.println("options entry = [" + entry + "]");
        }
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        System.out.println("login----------------");
        try {
            NameCallback nameCallback = new NameCallback("Myusername");
            PasswordCallback passwordCallback = new PasswordCallback("Mypassword", false);

            Callback[] calls = new Callback[]{nameCallback, passwordCallback,
                    new TextOutputCallback(TextOutputCallback.INFORMATION, "=======hello, just a msg!"),
                    new TextOutputCallback(TextOutputCallback.WARNING, "========just warn you!")
            };

            this.callbackHandler.handle(calls);
            System.out.println("handle回调完成");
            String username = nameCallback.getName();
            String password = String.valueOf(passwordCallback.getPassword());
            System.out.println("username = " + username + ", password = " + password);

            /**
             * 假设通过了验证。如果没有设置this.principal 则导致 commit() 抛出 LoginException: Security Exception
             */
            this.principal = new SamplePrincipal(username);
            this.isAuthenticated = true;
        } catch (IOException e) {
            throw new LoginException("no such user");
        } catch (UnsupportedCallbackException e) {
            throw new LoginException("login failure");
        }
        /**
         * 返回 false 将触发 LoginModule 的 login 函数抛出 LoginException: 登录失败: 忽略所有模块
         */
        return this.isAuthenticated;
    }

    /**
     * 验证后处理，在Subject中加入用户对象
     *
     * @return
     * @throws javax.security.auth.login.LoginException
     */
    @Override
    public boolean commit() throws LoginException {
        System.out.println("commit----------------");
        if (this.isAuthenticated) {
            this.subject.getPrincipals().add(this.principal);
        } else {
            throw new LoginException("Authentication failure");
        }

        /**
         * 返回 false 将触发 LoginModule 的 login 函数抛出 LoginException: 登录失败: 忽略所有模块
         */
        return this.isAuthenticated;
    }

    @Override
    public boolean abort() throws LoginException {
        System.out.println("abort-----------------");
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        System.out.println("logout----------------");
        this.subject.getPrincipals().remove(this.principal);
        this.principal = null;
        return true;
    }
}