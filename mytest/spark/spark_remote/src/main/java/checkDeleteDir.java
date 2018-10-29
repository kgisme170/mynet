import java.io.File;
public class checkDeleteDir {
    static void deleteDir(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                deleteDir(f);
            } else {
                f.delete();
            }
        }
        dir.delete();
    }

    static void checkExistenceAndDelete(String dir) {
        File file = new File(dir);
        if (file.exists()) {
            deleteDir(file);
        }
    }
}
