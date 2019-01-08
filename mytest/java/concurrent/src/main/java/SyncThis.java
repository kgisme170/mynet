import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SyncCollection1 { // using synchronized in function signature
    List<Integer> list = new ArrayList();

    public synchronized void add(int o) {
        list.add(o);
    }

    public synchronized void remove(int o) {
        list.remove(o);
    }
}

class SyncCollection2 { // using synchronized(this) inside function
    List<Integer> list = new ArrayList();

    public void add(int o) {
        synchronized (this) {
            list.add(o);
        }
    }

    public void remove(int o) {
        synchronized (this) {
            list.remove(o);
        }
    }
}

class SyncCollection3 { // using ReentrantLock lock()/unlock()
    List<Integer> list = new ArrayList();
    Lock reentrantLock = new ReentrantLock();

    public void add(int o) {
        reentrantLock.lock();
        list.add(o);
        reentrantLock.unlock();
    }

    public void remove(int o) {
        reentrantLock.lock();
        list.remove(o);
        reentrantLock.unlock();
    }
}

/**
 * @author liming.gong
 */
public class SyncThis {
    public static void main(String[] args) {
        {
            SyncCollection1 syncCollection = new SyncCollection1();
            syncCollection.add(3);
            syncCollection.remove(0);
        }
        {
            SyncCollection2 syncCollection = new SyncCollection2();
            syncCollection.add(3);
            syncCollection.remove(0);
        }
        {
            SyncCollection3 syncCollection = new SyncCollection3();
            syncCollection.add(3);
            syncCollection.remove(0);
        }
    }
}