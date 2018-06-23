import java.util.Date;
import java.util.List;
import java.util.Random;
import java.lang.Exception.*;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.*;
import org.apache.zookeeper.KeeperException.*;
public class watcher1 implements Watcher{
    private ZooKeeper zk;
    private String hostPort;
    private static final int SESSION_TIME_OUT = 15000;
    watcher1(String _hostPort){
        hostPort=_hostPort;
    }
    void startZK() throws Exception{
        zk=new ZooKeeper(hostPort, SESSION_TIME_OUT, this);
    }
    void stopZK() throws Exception {zk.close();}
    String createNode(String path, String data) throws Exception{
        return zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }
    List<String> getChildren(String path) throws KeeperException, InterruptedException{
        return zk.getChildren(path, false);
    }
    String getData(String path) throws KeeperException, InterruptedException{
        byte[] data = zk.getData(path, false, null);
        if(data==null)return "";
        return new String(data);
    }
    Stat setData(String path, String data) throws KeeperException, InterruptedException{
        return zk.setData(path, data.getBytes(), -1);
    }
    void deleteNode(String path) throws KeeperException, InterruptedException{
        zk.delete(path, -1);
    }
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
                System.out.println("##########检查master节点");
                isLeader = getData(path).equals(serverId);
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
                    createNode("/master", serverId);
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
            }catch(Exception e){
                System.out.println("KeeperException");
            }
            System.out.println("---------------checkMaster again--------------");
        }
    }
    public static void main(String args[])throws Exception{
        if (args.length == 0){
            System.out.println("需要zk server的ip地址和端口作为参数");
            System.exit(1);
        }
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