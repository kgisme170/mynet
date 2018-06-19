import java.util.Random;
import java.lang.Exception.*;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.*;
import org.apache.zookeeper.KeeperException.*;
public class watcher1 implements Watcher{
    ZooKeeper zk;
    String hostPort;
    watcher1(String _hostPort){
        hostPort=_hostPort;
    }
    void startZK() throws Exception{
        zk=new ZooKeeper(hostPort, 15000, this);
    }
    void stopZK() throws Exception {zk.close();}
    public void process(WatchedEvent e){
        System.out.println(e);
    }
    Random rand = new Random();
    String serverId = Long.toString(rand.nextLong());
    static boolean isLeader = false;
    boolean checkMaster(){
        while(true){
            try{
                Stat stat = new Stat();
                byte data[]=zk.getData("/master", false, stat);
                isLeader = new String(data).equals(serverId);
                return true;
            }catch(NoNodeException e){
                System.out.println("NoNodeException");
                return false;
            //}catch(ConnectionLossException e){
            //    System.out.println("ConnectionLossException");
            //    return false;
            }catch(KeeperException e){
                System.out.println("KeeperException");
                return false;
            }catch(InterruptedException e){
                System.out.println("InterruptedException");
                return false;
            }
        }
    }
    void runForMaster() throws InterruptedException{
        System.out.println("---------------runForMaster--------------");

        while(true){
            try {
                zk.create(
                    "/master",
                    serverId.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
                isLeader = true;
                break;
            }catch (NodeExistsException e) {
                isLeader = false;
                break;
            //}catch(ConnectionLosssException e){

            }catch(KeeperException e){
                System.out.println("KeeperException");
            }
            if(checkMaster())break;
            System.out.println("---------------checkMaster again--------------");
        }
    }
    public static void main(String args[])throws Exception{
        watcher1 m = new watcher1(args[0]);
        m.startZK();
        m.runForMaster();
        if(isLeader){
            Thread.sleep(60000);
        }else{
            System.out.println("Someone else is the leader");
        }
        m.stopZK();
    }
}