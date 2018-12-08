import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
/**
 * @author liming.glm
 */
public class DfsIo {
    Configuration conf = new Configuration();
    FileSystem fs;

    public DfsIo() throws IOException {
        conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
        conf.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));
        fs = FileSystem.get(conf);
    }
    final static String PUT = "put";
    final static String DELETE = "delete";
    final static String MKDIR = "mkdir";
    final static int PUT_LENGTH = 3;
    final static int DELETE_LENGTH = 2;
    final static int MKDIR_LENGTH = 1;

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("需要参数 [PUT/delete/mkdir] 目录或者文件名");
                return;
            }
            DfsIo dfs = new DfsIo();
            if (PUT.equals(args[0])) {
                if (args.length != PUT_LENGTH) {
                    System.out.println("PUT 需要2个参数 [from] [to]");
                    return;
                }
                dfs.put(args[1], args[2]);
            }
            if (DELETE.equals(args[0])) {
                if (args.length != DELETE_LENGTH) {
                    System.out.println("DELETE 需要1个参数 [文件名]");
                    return;
                }
                dfs.delete(args[1]);
            }
            if (MKDIR.equals(args[0])) {
                if (args.length != MKDIR_LENGTH) {
                    System.out.println("MKDIR 需要1个参数 [目录名]");
                    return;
                }
                dfs.mkdir(args[1]);
            }
        } catch (IOException e) {
            System.out.println("获得异常");
            e.printStackTrace();
        }
        System.out.println("成功");
    }

    public void put(String from, String to) throws IOException {
        String fileName = from.substring(from.lastIndexOf('/') + 1);
        final char slash = '/';
        if (to.charAt(to.length() - 1) == slash) {
            to = to + fileName;
        } else {
            to = to + '/' + fileName;
        }
        Path path = new Path(to);
        if (fs.exists(path)) {
            System.out.println(to + "文件已经存在");
            return;
        }
        FSDataOutputStream out = fs.create(path);
        InputStream in = new BufferedInputStream(new FileInputStream(new File(from)));
        byte[] b = new byte[4096];
        int numBytes = 0;
        while ((numBytes = in.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }
        out.close();
        in.close();
    }

    public void delete(String fileName) throws IOException {
        Path path = new Path(fileName);
        if (!fs.exists(path)) {
            System.out.println(fileName + "不存在");
            return;
        }
        fs.delete(path, true);
    }

    public void mkdir(String dir) throws IOException {
        Path path = new Path(dir);
        if (fs.exists(path)) {
            System.out.println(dir + "目录已经存在");
            return;
        }
        fs.mkdirs(path);
    }
}