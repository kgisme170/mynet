import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
/**
 * @author liming.glm
 */
public class dfsio {
    Configuration conf = new Configuration();
    FileSystem fs;

    public dfsio() throws IOException {
        conf.addResource(new Path("/etc/hadoop/conf/core-site.xml"));
        conf.addResource(new Path("/etc/hadoop/conf/hdfs-site.xml"));
        fs = FileSystem.get(conf);
    }

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("需要参数 [put/delete/mkdir] 目录或者文件名");
                return;
            }
            dfsio dfs = new dfsio();
            if (args[0].equals("put")) {
                if (args.length != 3) {
                    System.out.println("put 需要2个参数 [from] [to]");
                    return;
                }
                dfs.put(args[1], args[2]);
            }
            if (args[0].equals("delete")) {
                if (args.length != 2) {
                    System.out.println("delete 需要1个参数 [文件名]");
                    return;
                }
                dfs.delete(args[1]);
            }
            if (args[0].equals("mkdir")) {
                if (args.length != 2) {
                    System.out.println("mkdir 需要1个参数 [目录名]");
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
        if (to.charAt(to.length() - 1) == '/') {
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
