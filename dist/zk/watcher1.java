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
    public void process(WatchedEvent event){
        String msg = "##########====>";
        msg += event;
        System.out.println("Enter the process method,the event is :" + msg);
        Event.EventType type = event.getType();
        switch (type) {
            case NodeCreated:
                System.out.println("新建节点:" + event.getPath());
            case NodeDeleted:
                System.out.println("删除节点:" + event.getPath());
            case NodeDataChanged:
                System.out.println("修改节点:" + event.getPath());
            case NodeChildrenChanged:
                System.out.println("子节点:" + event);
        }
    }
    Random rand = new Random();
    String serverId = Long.toString(rand.nextLong());
    String path = "/master";
    static boolean isLeader = false;
    boolean checkMaster(){//如果这个节点已存在，那么前面的create就不会执行。
        while(true){
            try{
                Stat stat = new Stat();
                System.out.println("##########检查master节点");
                byte data[]=zk.getData(path, false, stat);
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
            System.out.println("---------------while--------------");
            try {
                System.out.println("---------------create--------------");
                Stat stat = zk.exists(path, this);
                if (stat == null) {
                    zk.create(
                        "/master",
                        serverId.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
                    isLeader = true;
                    System.out.println("---------------after create--------------");
                    break;
                }else{
                    if(checkMaster()){
                        System.out.println("---------------end while--------------");
                        break;
                    }
                    Thread.sleep(5000);
                    zk.delete(path, stat.getVersion());
                }
            }catch (NodeExistsException e) {
                isLeader = false;
                break;
            //}catch(ConnectionLosssException e){

            }catch(KeeperException e){
                System.out.println("KeeperException");
            }
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