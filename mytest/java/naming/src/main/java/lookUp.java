import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class lookUp {
    public static void main(String[] args) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, "file:///");

        String name = "/Users/x/Downloads/derby-driver.jar";
        try {
            Context ctx = new InitialContext(env);

            Object obj = ctx.lookup(name);
            System.out.println(name + " is bound to: " + obj + "\t[" + obj.getClass() + ']');
        } catch (NamingException e) {
            System.err.println("Problem looking up " + name + ": " + e);
        }
    }
}
