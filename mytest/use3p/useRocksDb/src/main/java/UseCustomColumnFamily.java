import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.rocksdb.ColumnFamilyDescriptor;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.DBOptions;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
/**
 * @author liming.gong
 */
public class UseCustomColumnFamily {
    static {
        RocksDB.loadLibrary();
    }

    private RocksDB rocksDB;
    private ColumnFamilyHandle columnFamilyHandle;

    public void openDb(String path, String table) throws RocksDBException {
        final Options options = new Options();
        options.setCreateIfMissing(true);
        List<byte[]> cfs = RocksDB.listColumnFamilies(options, path);
        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<ColumnFamilyDescriptor>();
        if (cfs.size() > 0) {
            System.out.println("文件已存在cfs.size() = " + cfs.size());
            for (byte[] cf : cfs) {
                columnFamilyDescriptors.add(new ColumnFamilyDescriptor(cf, new ColumnFamilyOptions()));
            }
        } else {
            System.out.println("文件不存在，新建");
            columnFamilyDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));
        }

        List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<ColumnFamilyHandle>();
        DBOptions dbOptions = new DBOptions();
        dbOptions.setCreateIfMissing(true);
        rocksDB = RocksDB.open(dbOptions, path, columnFamilyDescriptors, columnFamilyHandles);
        for (int i = 0; i < columnFamilyDescriptors.size(); i++) {
            if (new String(columnFamilyDescriptors.get(i).columnFamilyName()).equals(table)) {
                rocksDB.dropColumnFamily(columnFamilyHandles.get(i));
            }
        }
        columnFamilyHandle = rocksDB.createColumnFamily(
                new ColumnFamilyDescriptor(table.getBytes(), new ColumnFamilyOptions()));
    }

    private void putData(String key, String value) throws RocksDBException {
        rocksDB.put(columnFamilyHandle, key.getBytes(), value.getBytes());
    }

    private void removeData(String key) throws RocksDBException {
        rocksDB.remove(key.getBytes());
    }

    private void printData(String key) throws RocksDBException {
        System.out.println("printData");
        byte[] getValue = rocksDB.get(columnFamilyHandle, key.getBytes());
        System.out.println(new String(getValue));
    }

    private void printData(String[] allKeys) throws RocksDBException {
        System.out.println("printData");
        List<byte[]> keys = new ArrayList<byte[]>();
        for (String k : allKeys) {
            keys.add(k.getBytes());
        }
        List<ColumnFamilyHandle> handleList = new ArrayList<ColumnFamilyHandle>();
        handleList.add(columnFamilyHandle);
        handleList.add(columnFamilyHandle);
        Map<byte[], byte[]> valueMap = rocksDB.multiGet(handleList, keys);
        for (Map.Entry<byte[], byte[]> entry : valueMap.entrySet()) {
            System.out.println(new String(entry.getKey()) + "--" + new String(entry.getValue()));
        }
    }

    private void printAllData() {
        RocksIterator it = rocksDB.newIterator(columnFamilyHandle);
        for (it.seekToFirst(); it.isValid(); it.next()) {
            System.out.println(new String(it.key()) + "=>" + new String(it.value()));
        }
    }

    public void testCertainColumnFamily() throws RocksDBException {
        String table = "CertainColumnFamilyTest";
        String key = "certainKey";
        String value = "certainValue";
        final String dbPath = "/tmp/data03/";
        openDb(dbPath, table);

        putData(key, value);
        printData(key);
        putData("SecondKey", "SecondValue");

        printData(new String[]{key, "SecondKey"});
        removeData(key);

        printAllData();
        columnFamilyHandle.close();
    }

    public static void main(String[] args) throws RocksDBException {
        new UseCustomColumnFamily().testCertainColumnFamily();
    }
}