import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import org.junit.Test;
public class test01 {
    @Before
    public void testBefore() {
        System.out.println("Before case----");
    }

    @BeforeClass
    public static void testBeforeClass() {
        System.out.println("BeforeClass");
    }

    @After
    public void testAfter() {
        System.out.println("After case----");
    }

    @AfterClass
    public static void testAfterClass() {
        System.out.println("AfterClass");
        Assert.assertEquals(1, 1);
    }

    @Test
    public void testMain01() {
        System.out.println("Main01");
        Assert.assertEquals(1, 1);
    }

    @Test
    public void testMain02() {
        System.out.println("Main02");
        Assert.assertEquals(1, 1);
    }
}