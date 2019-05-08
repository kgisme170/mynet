package bean;

import java.beans.PropertyVetoException;
/**
 * 测试类
 */
public class TestBean {
    public static void main(String[] args) {
        SomeBean someBean = new SomeBean();
        //添加观察者监听器
        someBean.addPropertyChangeListener(new SomeBeanPropertyListener());
        someBean.addVetoableChangeListener(new SomeBeanPropertyListener());
        try {
            someBean.setChangableValue("someValue");
            someBean.setChangableValue("anotherValue");
            someBean.setChangableValue("invalidValue");
        } catch (PropertyVetoException e) {
            System.err.println(e.getMessage());
        }
    }
}