import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 以下三种方式等效
 */
class SyncCollection1 {
    List<Integer> list = new ArrayList();

    public synchronized void add(int o) {
        list.add(o);
    }

    public synchronized void remove(int o) {
        list.remove(o);
    }
}

class SyncCollection2 {
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

class SyncCollection3 {
    List<Integer> list = new ArrayList();
    Lock reentrantLock = new ReentrantLock();

    public void add(int o) {
        try {
            reentrantLock.lock();
            list.add(o);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void remove(int o) {
        try {
            reentrantLock.lock();
            list.remove(o);
        } finally {
            reentrantLock.unlock();

        }
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