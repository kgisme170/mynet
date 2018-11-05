package my;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CarApp {
    public static void main(String[] args) {
        String fileName = "../../../src/bean.xml";
        String s = UserServiceApp.class.getResource(".") + fileName;
        ApplicationContext context = new FileSystemXmlApplicationContext(s);
        Car car = (Car) context.getBean("car");
        car.go();
    }
}