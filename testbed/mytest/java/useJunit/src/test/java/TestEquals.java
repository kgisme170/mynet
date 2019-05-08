import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void TestOverrideEqual() {
        My m1 = new My();
        My m2 = new My();
        Set<My> mySet = new HashSet<>();
        mySet.add(m1);
        mySet.add(m2);
        Assert.assertEquals(2, mySet.size());
    }

    class MyHash extends My {
        @Override
        public int hashCode() {
            return i;
        }
    }

    @Test
    public void TestOverrideAll() {
        MyHash m1 = new MyHash();
        MyHash m2 = new MyHash();
        Set<MyHash> mySet = new HashSet<>();
        mySet.add(m1);
        mySet.add(m2);
        Assert.assertEquals(1, mySet.size());
    }

    class He {
        int i = 1;

        @Override
        public int hashCode() {
            return i;
        }
    }

    @Test
    public void TestOverrideHashCode() {
        He he = new He();
        He he2 = new He();
        Set<He> heSet = new HashSet<>();
        heSet.add(he);
        Assert.assertFalse(heSet.contains(he2));
    }

    class HeEqual extends He {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final HeEqual heEqual = (HeEqual) obj;
            return heEqual.i == i;
        }
    }

    @Test
    public void TestOverrideAll2() {
        HeEqual he = new HeEqual();
        HeEqual he2 = new HeEqual();
        Set<HeEqual> heSet = new HashSet<>();
        heSet.add(he);
        Assert.assertTrue(heSet.contains(he2));
    }
}