/**
 * @author liming.gong
 */

public class TestPrincipal {
    public static void main(String[] args) {
        System.setProperty("java.security.policy","demo.policy");
        System.setSecurityManager(new SecurityManager());
        System.out.println(System.getProperty("java.home"));
    }
}