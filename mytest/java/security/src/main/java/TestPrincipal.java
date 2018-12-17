public class TestPrincipal {
    public static void main(String[] args) {
        //System.setProperty("java.security.policy", "demo.policy");
        //System.setProperty("java.security.auth.login.config", "demo.config");
        System.setProperty("java.security.policy","demo.policy");
        System.setSecurityManager(new SecurityManager());
        System.out.println(System.getProperty("java.home"));
    }
}
/**
 * @author liming.gong
 */
