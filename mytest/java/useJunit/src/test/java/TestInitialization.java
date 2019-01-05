import org.junit.*;
import org.junit.Test;
import java.time.LocalDate;
class NormalInit {
    int i = 3;
    LocalDate h = LocalDate.now();
    public int year = h.getYear(); // is it safe?

    public NormalInit() {
        System.out.println("default ctor");
    }

    {
        System.out.println("init block 1");
    }

    public NormalInit(int i) {
        this();
        System.out.println("1 param");
    }

    {
        System.out.println("init block 2");
    }

    public NormalInit(int i, int j) {
        this(i);
        System.out.println("2 params");
    }

    static {
        System.out.println("static block");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
        super.finalize();
    }
}

public class TestInitialization {
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
    public void testMain03() {
        NormalInit normalInit = new NormalInit(1,2);
        System.out.println(normalInit.year);
        System.gc();
    }
}