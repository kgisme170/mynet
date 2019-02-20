import org.junit.*;

class MyClone implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
public class TestCloneable {
    @Test
    public void testDefaultClone() throws Exception {
        MyClone myClone = new MyClone();
        MyClone myClone1 = (MyClone) myClone.clone();
        Assert.assertNotEquals(myClone, myClone1); // failure?
    }
}
