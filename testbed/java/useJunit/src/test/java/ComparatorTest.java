import java.util.*;
import org.junit.Test;
/**
 * @author liming.gong
 */
public class ComparatorTest {
    private int population;

    public ComparatorTest(){}
    public void set(int p1) {
        population = p1;
    }

    public static void main(String[] args) {
        Comparator<ComparatorTest> order = new Comparator<ComparatorTest>() {
            @Override
            public int compare(ComparatorTest o1, ComparatorTest o2) {
                int p1 = o1.getPopulation();
                int p2 = o2.getPopulation();
                if (p1 > p2) {
                    return 1;
                } else if (p1 < p2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        ComparatorTest t1 = new ComparatorTest(); t1.set(1);
        ComparatorTest t2 = new ComparatorTest(); t1.set(5);
        ComparatorTest t3 = new ComparatorTest(); t1.set(3);
        ComparatorTest t4 = new ComparatorTest(); t1.set(4);
        Queue<ComparatorTest> q = new PriorityQueue<ComparatorTest>(10, order);
        q.add(t1);
        q.add(t2);
        q.add(t3);
        q.add(t4);
        System.out.println(q.size());
        while (q.size() != 0) {
            System.out.println(q.poll());
        }

        class LengthCompare implements Comparator<String> {
            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        }
        String[] str = new String[]{"xyz1", "abc", "1234"};
        Arrays.sort(str, new LengthCompare());
        for (String s : str) {
            System.out.println(s);
        }
        Arrays.sort(str, Comparator.comparingInt(String::length));
        for (String s : str) {
            System.out.println(s);
        }
        Comparator<String> comparator = (first, second) -> first.length() - second.length();
        Arrays.sort(str, comparator);
    }

    @Test
    public void testArrayListSort() {
        class Him {
            private int i;
            private int j;
            private String s;

            public Him(int i, int j, String s) {
                this.i = i;
                this.j = j;
                this.s = s;
            }

            public int getI() {
                return i;
            }

            public int getJ() {
                return j;
            }

            public String getS() {
                return s;
            }
        }
        Him h1 = new Him(30, 2, "h1");
        Him h2 = new Him(20, 2, "h2");
        Him h3 = new Him(30, 3, "h3");

        ArrayList<Him> himArrayList = new ArrayList<>();
        himArrayList.add(h1);
        himArrayList.add(h2);
        himArrayList.add(h3);
        himArrayList.sort(
                Comparator.comparing(Him::getI).thenComparing(Him::getJ)
                        .thenComparing(Him::getS, (s1, s2) -> s1.length() - s2.length()));
    }

    public int getPopulation() {
        return population;
    }
}
