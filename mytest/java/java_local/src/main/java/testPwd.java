import java.io.File;

public class testPwd {
    public static void main(String [] args) {
        //touch ~/a.txt
        File f = new File("~/a.txt");
        System.out.println(f.exists());
    }
}
