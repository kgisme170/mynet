import org.junit.Assert;
import org.junit.Test;

public class TestEquals {
    class My {
        int i = 1;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof My) {
                My m = (My) obj;
                return i == m.i;
            } else {
                return false;
            }
        }
    }

    class You {
        int i = 1;
    }

    @Test
    public void TestMyEqual() {
        My m1 = new My();
        My m2 = new My();
        Assert.assertEquals(m1, m2);
    }

    @Test
    public void TestYouEqual() {
        You m1 = new You();
        You m2 = new You();
        Assert.assertNotEquals(m1, m2);
    }
}