import org.junit.*;
import org.junit.Test;

import java.util.*;

/**
 * @author liming.gong
 */
public class ComparableComparator {
    @Test
    public void UseAnyValue() {
        class Cb implements Comparable<Integer> {
            int m_i;

            public Cb(int i) {
                m_i = i;
            }

            @Override
            public int compareTo(Integer o) {
                return m_i - o;
            }
        }

        Cb cb = new Cb(3);
        Assert.assertEquals(1, cb.compareTo(2));
        Assert.assertEquals(-2, cb.compareTo(4));
    }

    @Test
    public void UseModel() {
        class Cb implements Comparable<Cb> {
            int m_i;

            public Cb(int i) {
                m_i = i;
            }

            @Override
            public int compareTo(Cb o) {
                return m_i - o.m_i;
            }
        }

        Cb[] a = new Cb[5];
        a[0] = new Cb(4);
        a[1] = new Cb(44);
        a[2] = new Cb(3);
        a[3] = new Cb(5);
        a[4] = new Cb(6);
        Arrays.sort(a);
        for (Cb cb : a) {
            System.out.println(cb.m_i);
        }
    }

    @Test
    public void ExternalCompare() {
        class Cb {
            int m_i;

            public Cb(int i) {
                m_i = i;
            }
        }

        class Cr implements Comparator<Cb> {
            @Override
            public int compare(Cb o1, Cb o2) {
                return o1.m_i - o2.m_i;
            }
        }
        List<Cb> cbs = new ArrayList<>();
        cbs.add(new Cb(4));
        cbs.add(new Cb(44));
        cbs.add(new Cb(3));
        cbs.add(new Cb(5));
        cbs.add(new Cb(6));
        Collections.sort(cbs, new Cr());
        for (Cb cb : cbs) {
            System.out.println(cb.m_i);
        }
        Cb[] a = new Cb[5];
        a[0] = new Cb(4);
        a[1] = new Cb(44);
        a[2] = new Cb(3);
        a[3] = new Cb(5);
        a[4] = new Cb(6);
        Arrays.sort(a, new Cr());
        for (Cb cb : a) {
            System.out.println(cb.m_i);
        }
    }
}
