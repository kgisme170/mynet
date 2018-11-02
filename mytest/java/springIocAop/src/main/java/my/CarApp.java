package my;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CarApp {
    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("bean.xml");
        Car car = (Car) context.getBean("car");
        car.go();
    }
}