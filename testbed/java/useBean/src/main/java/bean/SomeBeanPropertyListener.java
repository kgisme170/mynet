package bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

/**
 * 监听器，用来监听Bean属性值的变化与拒绝属性的修改
 * 监听器类似观察者模式观察者角色（Observer）
 */
public class SomeBeanPropertyListener implements PropertyChangeListener,
        VetoableChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //在这里处理监听到的事件
        System.out.println("\"" + evt.getNewValue()
                + "\" is setted to replace the old value \"" + evt.getOldValue() + "\"");
    }
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        //如果设置的值为某个特殊值（这里为 invalidValue ），拒绝修改
        if (evt.getNewValue().equals("invalidValue")) {
            System.out.println("\"" + evt.getNewValue()
                    + "\" is try to replace the old value \"" + evt.getOldValue() + "\"");
            //抛出异常表示拒绝修改属性
            throw new PropertyVetoException(
                    "What you set \"invalidValue\" is a invalid value! operate fail!",
                    evt);
        }
    }
}