import java.util.Date;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
public class adminClient implements Watcher{
    ZooKeeper zk;
    String hostPort;
    adminClient(String _hostPort){hostPort=_hostPort;}
    void start() throws Exception{zk=new ZooKeeper(hostPort, 15000, this);}
    void stop() throws Exception{zk.close();}
    void listState() throws Exception{
        try{
            Stat stat = new Stat();
            byte masterData[] = zk.getData("/master", false, stat);
            Date startDate = new Date(stat.getCtime());
            System.out.println("Master:" + new String(masterData) + " since " +startDate);
        }catch(Exception e){
            System.out.println("No master:"+e.getMessage());
        }
        System.out.println("Workers");
        for(String w:zk.getChildren("/workers", false)){
            byte data[]=zk.getData("/workers/"+w, false, null);
            String state = new String(data);
            System.out.println("\t" + w + ": " + state);
        }
        System.out.println("Tasks");
        for(String t:zk.getChildren("/assign",false)){
            System.out.println("\t" + t);
        }
    }
    public void process(WatchedEvent e){System.out.println(e);}
    public static void main(String args[]) throws Exception{
        adminClient c = new adminClient(args[0]);
        c.start();
        c.listState();
        c.stop();
    }
}