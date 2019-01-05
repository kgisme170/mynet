import java.io.Console;
import java.util.Scanner;

/**
 * @author liming.gong
 */
public class UseSystemIn {
    public static void testIo() {
        System.out.println(Math.sqrt(Math.PI));
        Scanner in = new Scanner(System.in);
        System.out.println("输入名字");
        String name = in.nextLine();
        System.out.println(name);
        System.out.println("名字和年龄");

        String firstName = in.next();
        int age = in.nextInt();
        System.out.println(firstName + "," + age);
    }
    public static void main(String[] args) {
        Console cons = System.console();
        System.out.println(cons);
        String username = cons.readLine("User name = ");
        char[] passwd = cons.readPassword("Password = ");
    }
}
