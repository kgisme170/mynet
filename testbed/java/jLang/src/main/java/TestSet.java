import java.util.*;
/**
 * @author liming.gong
 */
public class TestSet {
    static class My implements Comparable {
        private int mI;

        public My(int i) {
            mI = i;
        }

        @Override
        public int compareTo(Object o) {
            return mI - ((My) o).mI;
        }

        @Override
        public int hashCode() {
            return mI;
        }

        @Override
        public boolean equals(Object o) {
            return mI == ((My)o).mI;
        }
    }

    public static void main(String[] args) {
        TreeSet tree = new TreeSet();
        tree.add(new My(4));
        tree.add(new My(2));
        tree.add(new My(5));

        Iterator it = tree.iterator();
        while (it.hasNext()) {
            System.out.println(it.next() + " ");
        }

        HashSet<My> m = new HashSet<>();
        m.add(new My(6));
        m.add(new My(2));
        LinkedHashSet<My> s = new LinkedHashSet<>();
        s.add(new My(7));
    }
}