import org.junit.*;

import java.util.StringJoiner;

public class TestString {
    @Test
    public void useCodePoint() {
        System.out.println("Main01");
        Assert.assertEquals(1, 1);
        String all = String.join("/", "\t", "abc", "xyz");
        StringJoiner sj = new StringJoiner(":", "[", "]");
        sj.add("George").add("Sally").add("Fred");
        String desiredString = sj.toString();
        System.out.println(all + desiredString);
        Assert.assertEquals(1, 1);

        String s1 = "xyz";
        String s2 = "x";
        String s3 = s2 + "yz";
        Assert.assertEquals(s1, s3);
        Assert.assertFalse(s1 == s3);

        System.out.println(s1.codePointAt(0));
        System.out.println(s1.codePointAt(1));
        System.out.println(s1.codePointAt(2));

        int[] codePoints = s1.codePoints().toArray();
        String s4 = new String(codePoints, 0, codePoints.length);

        for (int i = 0; i < codePoints.length; ) {
            int cp = s4.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp)) {
                i += 2;
            } else {
                ++i;
            }
            System.out.println(cp);
        }
    }
}