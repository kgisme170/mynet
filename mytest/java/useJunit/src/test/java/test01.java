import org.junit.*;

import org.junit.Test;

import java.time.LocalDate;
import java.util.StringJoiner;
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
        String all = String.join("/", "\t", "abc", "xyz");
        StringJoiner sj = new StringJoiner(":", "[", "]");
        sj.add("George").add("Sally").add("Fred");
        String desiredString = sj.toString();
        System.out.println(all + desiredString);
    }

    @Test
    public void testMain02() {
        System.out.println("Main02");
        Assert.assertEquals(1, 1);

        String s1 = "xyz";
        String s2 = "x";
        String s3 = s2 + "yz";
        Assert.assertEquals(s1, s3);
        Assert.assertFalse(s1 == s3);

        System.out.println(s1.codePointAt(0));
        System.out.println(s1.codePointAt(1));
        System.out.println(s1.codePointAt(2));

        int [] codePoints = s1.codePoints().toArray();
        String s4 = new String(codePoints, 0, codePoints.length);

        for(int i=0;i<codePoints.length;) {
            int cp = s4.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp)) {
                i += 2;
            }
            else {
                ++i;
            }
            System.out.println(cp);
        }
    }

    @Test
    public void testMain03() {
        NormalInit normalInit = new NormalInit(1,2);
        System.out.println(normalInit.year);
        System.gc();
    }
}