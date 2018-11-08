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
public class cacheRow {
    private static final String dbPath = "/tmp/data02/";
    static {
        RocksDB.loadLibrary();
    }

    RocksDB rocksDB;

    //  RocksDB.DEFAULT_COLUMN_FAMILY
    public void testDefaultColumnFamily() throws RocksDBException {
        Options options = new Options();
        options.setCreateIfMissing(true);

        rocksDB = RocksDB.open(options, dbPath);
        byte[] key = "Hello".getBytes();
        byte[] value = "World".getBytes();
        rocksDB.put(key, value);
        System.out.println("开始打印ColumnFamilies");
        List<byte[]> cfs = RocksDB.listColumnFamilies(options, dbPath);
        for(byte[] cf : cfs) {
            System.out.println(new String(cf));
        }
        System.out.println("结束打印ColumnFamilies");

        byte[] getValue = rocksDB.get(key);
        System.out.println(new String(getValue));

        rocksDB.put("SecondKey".getBytes(), "SecondValue".getBytes());

        List<byte[]> keys = new ArrayList<>();
        keys.add(key);
        keys.add("SecondKey".getBytes());

        Map<byte[], byte[]> valueMap = rocksDB.multiGet(keys);
        for(Map.Entry<byte[], byte[]> entry : valueMap.entrySet()) {
            System.out.println(new String(entry.getKey()) + ":" + new String(entry.getValue()));
        }

        RocksIterator it = rocksDB.newIterator();
        for(it.seekToFirst(); it.isValid(); it.next()) {
            System.out.println("it key:" + new String(it.key()) + ", it value:" + new String(it.value()));
        }

        rocksDB.remove(key);
        System.out.println("after remove key:" + new String(key));

        it = rocksDB.newIterator();
        for(it.seekToFirst(); it.isValid(); it.next()) {
            System.out.println("it key:" + new String(it.key()) + ", it value:" + new String(it.value()));
        }

    }

    public void testCertainColumnFamily() throws RocksDBException {
        String table = "CertainColumnFamilyTest";
        String key = "certainKey";
        String value = "certainValue";

        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
        Options options = new Options();
        options.setCreateIfMissing(true);

        List<byte[]> cfs = RocksDB.listColumnFamilies(options, dbPath);
        if(cfs.size() > 0) {
            System.out.println("文件已存在cfs.size() = " + cfs.size());
            for(byte[] cf : cfs) {
                columnFamilyDescriptors.add(new ColumnFamilyDescriptor(cf, new ColumnFamilyOptions()));
            }
        } else {
            System.out.println("文件不存在，新建");
            columnFamilyDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));
        }

        List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
        DBOptions dbOptions = new DBOptions();
        dbOptions.setCreateIfMissing(true);

        rocksDB = RocksDB.open(dbOptions, dbPath, columnFamilyDescriptors, columnFamilyHandles);
        for(int i = 0; i < columnFamilyDescriptors.size(); i++) {
            if(new String(columnFamilyDescriptors.get(i).columnFamilyName()).equals(table)) {
                rocksDB.dropColumnFamily(columnFamilyHandles.get(i));
            }
        }

        ColumnFamilyHandle columnFamilyHandle = rocksDB.createColumnFamily(
                new ColumnFamilyDescriptor(table.getBytes(), new ColumnFamilyOptions()));
        rocksDB.put(columnFamilyHandle, key.getBytes(), value.getBytes());

        byte[] getValue = rocksDB.get(columnFamilyHandle, key.getBytes());
        System.out.println("get Value : " + new String(getValue));

        rocksDB.put(columnFamilyHandle, "SecondKey".getBytes(), "SecondValue".getBytes());

        List<byte[]> keys = new ArrayList<>();
        keys.add(key.getBytes());
        keys.add("SecondKey".getBytes());

        List<ColumnFamilyHandle> handleList = new ArrayList<>();
        handleList.add(columnFamilyHandle);
        handleList.add(columnFamilyHandle);

        Map<byte[], byte[]> multiGet = rocksDB.multiGet(handleList, keys);
        for(Map.Entry<byte[], byte[]> entry : multiGet.entrySet()) {
            System.out.println(new String(entry.getKey()) + "--" + new String(entry.getValue()));
        }

        rocksDB.remove(columnFamilyHandle, key.getBytes());

        RocksIterator it = rocksDB.newIterator(columnFamilyHandle);
        for(it.seekToFirst(); it.isValid(); it.next()) {
            System.out.println(new String(it.key()) + ":" + new String(it.value()));
        }
        columnFamilyHandle.close();
    }

    public static void main(String[] args) throws RocksDBException {
        cacheRow test = new cacheRow();
        if(args.length>=2) {
            test.testCertainColumnFamily();
        }else{
            test.testDefaultColumnFamily();
        }
    }
}
