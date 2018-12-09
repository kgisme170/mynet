package my;

import org.springframework.stereotype.Component;
/**
 * @author liming.gong
 */
@Component("person")
public class Person {
    public void run() {
        System.out.println("run...");
    }

    public String run(int i) {
        System.out.println("run" + i + "...");
        return i + "";
    }

    public void run(int i, String a) {
        System.out.println("Running run" + i + "..." + a);
    }

    public void say() {
        System.out.println("say...");
    }
}