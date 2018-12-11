import java.util.HashSet;
import java.util.Objects;

class Inner {
    int i;
    String s;
    public Inner(int i, String s) {
        this.i = i;
        this.s = s;
    }
    @Override
    public int hashCode() {
        return Objects.hash(i, s);
    }
    @Override
    public boolean equals(Object o) {
        Inner inner = (Inner) o;
        return i == inner.i && s.equals(inner.s);
    }
}

/**
 * @author liming.gong
 */
public class TestEquals {
    public static void main(String[] args) {
        testString();
        System.out.println("==================");
        System.out.println("和null比较 = " + "abc".equals(null));
        System.out.println("==================");
        //System.out.println(Integer.valueOf(2) == Integer.valueOf(2));// T
        System.out.println(new Integer(2).equals(new Integer(2)));
        System.out.println(Integer.valueOf(256).equals(Integer.valueOf(256)));
        //System.out.println(new Integer(2) == new Integer(2)); // F
        //System.out.println(Integer.valueOf(256) == Integer.valueOf(256)); // F
        System.out.println("==================");
        HashSet<Inner> hi = new HashSet<>();
        hi.add(new Inner(1, "abc"));
        System.out.println(hi.contains(new Inner(1, "abc")));
    }

    private static void testString() {
        String s1 = "a";
        String s2 = "b";

        HashSet<String> hs = new HashSet<>();
        String s3 = s1 + s2;
        String s4 = "ab";
        hs.add(s1 + s2);
        System.out.println(hs.contains("ab"));
        System.out.println(s3 == s4);
        System.out.println(s3.equals(s4));
        System.out.println(s3.hashCode() == s4.hashCode());
        System.out.println("==================");
    }
}