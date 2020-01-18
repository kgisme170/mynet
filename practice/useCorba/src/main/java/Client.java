import java.util.Properties;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
 
import hello.GoodDay;
import hello.GoodDayHelper;
 
 
public class Client {
 
    static org.omg.CORBA.ORB orb = null;
    static GoodDay gd=null;
    public static void main(String[] args) {
        System.out.println(">>>>Start to initialize client stuff");
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
        props.put("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");
        props.put("ORBInitRef.NameService", "corbaloc::localhost:9527/NameService");
 
        try {
            orb = org.omg.CORBA.ORB.init(args, props);
 
            NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
            gd = GoodDayHelper.narrow(nc.resolve(nc.to_name("CorbaSample_Server")));
            //
            String s = gd.hello_simple();
            System.out.println(s);
            String s2 = gd.hello_wide("\"call me client\"");
            System.out.println(s2);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}
