import java.util.Date;
import java.util.List;
import java.util.Random;
import java.lang.Exception.*;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.*;
import org.apache.zookeeper.KeeperException.*;
import org.apache.zookeeper.AsyncCallback.*;
public class workerWatch implements Watcher{
    private ZooKeeper zk;
    private String hostPort;
    private static final int SESSION_TIME_OUT = 15000;
    workerWatch(String _hostPort){
        hostPort=_hostPort;
    }
    void startZK() throws Exception{
        zk=new ZooKeeper(hostPort, SESSION_TIME_OUT, this);
    }
    void stopZK() throws Exception {zk.close();}
    void register(){
        try{
            zk.create("/workers/worker-" + serverId,
                "Idle".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                createWorkerCallback, null);
        }catch(Exception e){}
    }
    StringCallback createWorkerCallback = new StringCallback(){
        public void processResult(int rc, String path, Object ctx, String name){
            switch(Code.get(rc)){
                case CONNECTIONLOSS:
                    register();
                    break;
                case OK:
                    System.out.println("Register OK:" + serverId);
                    break;
                case NODEEXISTS:
                    System.out.println("Already exists:" + serverId);
                    break;
                default:
                    System.out.println("Not OK:" + serverId);
            }
        }
    };
    public void process(WatchedEvent event){
        String msg = "##########====>";
        msg += event;
        System.out.println("Enter the process method,the event is :" + msg);
    }
    Random rand = new Random();
    String serverId = Long.toString(rand.nextLong());
    String path = "/master";
    public static void main(String args[])throws Exception{
        if (args.length == 0){
            System.out.println("需要zk server的ip地址和端口作为参数");
            System.exit(1);
        }
        workerWatch m = new workerWatch(args[0]);
        m.startZK();
        m.register();
        Thread.sleep(30000);
        m.stopZK();
    }
}