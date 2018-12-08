import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 * @author liming.gong
 */
public class TestComparator {
    private int population;

    public TestComparator(String n1, int p1) {
        population = p1;
    }

    public static void main(String[] args) {
        Comparator<TestComparator> order = new Comparator<TestComparator>() {
            @Override
            public int compare(TestComparator o1, TestComparator o2) {
                int p1 = o1.getPopulation();
                int p2 = o2.getPopulation();
                if (p1 > p2) {
                    return 1;
                }
                else if (p1 < p2) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        };
        TestComparator t1 = new TestComparator("t1", 1);
        TestComparator t2 = new TestComparator("t2", 5);
        TestComparator t3 = new TestComparator("t3", 3);
        TestComparator t4 = new TestComparator("t4", 4);
        Queue<TestComparator> q = new PriorityQueue<TestComparator>(10, order);
        q.add(t1);
        q.add(t2);
        q.add(t3);
        q.add(t4);
        System.out.println(q.size());
        while (q.size() != 0) {
            System.out.println(q.poll());
        }
    }

    public int getPopulation() {
        return population;
    }
}