import java.io.File;
/**
 * @author liming.gong
 */
public class TestPwd {
    public static void main(String [] args) {
        //touch ~/a.txt
        File f = new File(System.getProperty("user.home") + "/a.txt");
        System.out.println(f.exists());
    }
}
