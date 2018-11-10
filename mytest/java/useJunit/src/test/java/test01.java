import org.junit.Assert;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Test;
public class test01 {
    @Before
    public void testAdd(){
        System.out.println("Before");
    }
    @Test
    public void testMain(){
        System.out.println("Main");
    }
}
