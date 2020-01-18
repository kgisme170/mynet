import java.util.Properties;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
 
public class Main {
 
    public static void main(String[] args) {
        System.out.println(">>>>Start to initialize CORBA stuff");
        System.setProperty("jacorb.home", "C:\\jacorb-2.3.1");
        try {
            Properties props = new Properties();
            //
            props.put("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
            props.put("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");
            props.put("org.omg.CORBA.ORBInitRef.NameService", "corbaloc::localhost:9527/NameService");
            //
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, props);
            //
            POA poaRoot = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            //
            poaRoot.the_POAManager().activate();
            //
            GoodDayImpl impl = new GoodDayImpl();
            //
            org.omg.CORBA.Object obj = poaRoot.servant_to_reference(impl);
            //
            NamingContextExt nc = NamingContextExtHelper.narrow(orb.resolve_initial_references("NameService"));
            //
            nc.bind(nc.to_name("CorbaSample_Server"), obj);
            //
            orb.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(">>>>End the CORBA server");
    }
 
}
