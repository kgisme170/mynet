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
    static {
        RocksDB.loadLibrary();
    }

    RocksDB rocksDB;
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
    private void removeData(String key) throws RocksDBException {
        rocksDB.remove(key.getBytes());
    }
    private void printData(String key) throws RocksDBException {
        byte[] getValue = rocksDB.get(key.getBytes());
        System.out.println(new String(getValue));
    }
    private void printData(String [] allKeys) throws RocksDBException {
        List<byte[]> keys = new ArrayList<>();
        for(String k : allKeys){
            keys.add(k.getBytes());
        }
        Map<byte[], byte[]> valueMap = rocksDB.multiGet(keys);
        for(Map.Entry<byte[], byte[]> entry: valueMap.entrySet()){
            System.out.println(new String(entry.getKey() + ":" + new String(entry.getValue())));
        }
    }

    private void printAllData(){
        System.out.println("开始打印所有数据");
        RocksIterator it = rocksDB.newIterator();
        for(it.seekToFirst(); it.isValid(); it.next()) {
            System.out.println("Key:" + new String(it.key()) + ", Value:" + new String(it.value()));
        }
        System.out.println("结束打印所有数据");
    }

    public void testDefaultColumnFamily() throws RocksDBException {
        final String dbPath = "/tmp/data02/";
        final Options options = new Options();
        options.setCreateIfMissing(true);
        rocksDB = RocksDB.open(options, dbPath);
        listColumnFamilies(options,dbPath);
        putData("Hello", "World");
        printData("Hello");
        putData("SecondKey", "SecondValue");
        printData(new String[]{"Hello", "SecondKey"});

        printAllData();
        removeData("Hello");
        printAllData();
    }

    public void testCertainColumnFamily() throws RocksDBException {
        String table = "CertainColumnFamilyTest";
        String key = "certainKey";
        String value = "certainValue";
        final String dbPath = "/tmp/data03/";
        final Options options = new Options();
        options.setCreateIfMissing(true);

        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();

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

        //printData(new String[]{key, "SecondKey"});
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
            System.out.println(new String(it.key()) + "=>" + new String(it.value()));
        }
        columnFamilyHandle.close();
    }

    public static void main(String[] args) throws RocksDBException {
        cacheRow test = new cacheRow();
        if(args.length<=1) {
            test.testCertainColumnFamily();
        }else{
            test.testDefaultColumnFamily();
        }
    }
}
