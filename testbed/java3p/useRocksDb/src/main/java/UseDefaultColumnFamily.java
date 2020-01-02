import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
/**
 * @author liming.gong
 */
public class UseDefaultColumnFamily {
    static {
        RocksDB.loadLibrary();
    }

    private RocksDB rocksDB;

    private void openDb(String dbPath) throws RocksDBException {
        final Options options = new Options();
        options.setCreateIfMissing(true);
        rocksDB = RocksDB.open(options, dbPath);
        listColumnFamilies(options, dbPath);
    }

    private void listColumnFamilies(Options options, String path) throws RocksDBException {
        System.out.println("开始打印ColumnFamilies");
        List<byte[]> cfs = RocksDB.listColumnFamilies(options, path);
        for (byte[] cf : cfs) {
            System.out.println(new String(cf));
        }
        System.out.println("结束打印ColumnFamilies");
    }

    private void putData(String key, String value) throws RocksDBException {
        rocksDB.put(key.getBytes(), value.getBytes());
    }

    private void printData(String key) throws RocksDBException {
        byte[] getValue = rocksDB.get(key.getBytes());
        System.out.println(new String(getValue));
    }

    private void printData(String[] allKeys) throws RocksDBException {
        List<byte[]> keys = new ArrayList<byte[]>();
        for (String k : allKeys) {
            keys.add(k.getBytes());
        }
        Map<byte[], byte[]> valueMap = rocksDB.multiGet(keys);
        for (Map.Entry<byte[], byte[]> entry : valueMap.entrySet()) {
            System.out.println(new String(entry.getKey() + ":" + new String(entry.getValue())));
        }
    }

    private void printAllData() {
        System.out.println("开始打印所有数据");
        RocksIterator it = rocksDB.newIterator();
        for (it.seekToFirst(); it.isValid(); it.next()) {
            System.out.println("Key:" + new String(it.key()) + ", Value:" + new String(it.value()));
        }
        System.out.println("结束打印所有数据");
    }

    public void testDefaultColumnFamily() throws RocksDBException {
        final String dbPath = "/tmp/data02/";
        openDb(dbPath);
        putData("Hello", "World");
        printData("Hello");
        putData("SecondKey", "SecondValue");
        printData(new String[]{"Hello", "SecondKey"});

        printAllData();
    }

    public static void main(String[] args) throws RocksDBException {
        new UseDefaultColumnFamily().testDefaultColumnFamily();
    }
}