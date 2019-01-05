import org.junit.*;

class MyClone implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // 为什么不返回MyClone
    }
}
public class TestCloneable {
    @Test
    public void testDefaultClone() throws Exception {
        MyClone myClone = new MyClone();
        MyClone myClone1 = (MyClone) myClone.clone();
        Assert.assertEquals(myClone, myClone1); // failure?
    }
}