package bean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

/**
 * Bean类似观察者模式里的主题角色或者是被观察者角色（Observable）
 */
public class SomeBean {

    protected PropertyChangeSupport propertySupport; //属性改变通知支持
    protected VetoableChangeSupport vetoableChangeSupport; //反对属性改变支持
    private String changableValue;

    public SomeBean() {
        //这里添加支持与像使用observer模式的实现接有口异曲同工之效
        propertySupport = new PropertyChangeSupport(this);//使本对象有属性改变通知监听器的能力
        vetoableChangeSupport = new VetoableChangeSupport(this);//使监听器有反对本对象属性改变的能力
    }

    public void setChangableValue(String newValue) throws PropertyVetoException {

        //如果值发生了修改
        if (!newValue.equals(changableValue)) {
            String oldValue = changableValue;

            //这个一定要在前调用，如果监听器拒绝会抛出异常
            vetoableChangeSupport
                    .fireVetoableChange("changableValue", oldValue, newValue);

            //如果未拒绝，则修改Bean属性
            changableValue = newValue;
            //这个在后调用，属性修改后通知监听器
            propertySupport.firePropertyChange("changableValue", oldValue, newValue);
            //如果两个监听器的次序调过来，你想想会有什么问题？你期待的效果没有达到哦~~~~~~~~~~~~~~~
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }
}