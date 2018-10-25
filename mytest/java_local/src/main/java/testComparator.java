import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class testComparator {
    private int population;

    public testComparator(String _n, int _p) {
        population = _p;
    }

    public static void main(String[] args) {
        Comparator<testComparator> order = new Comparator<testComparator>() {
            public int compare(testComparator o1, testComparator o2) {
                int p1 = o1.getPopulation();
                int p2 = o2.getPopulation();
                if (p1 > p2) return 1;
                else if (p1 < p2) return -1;
                else return 0;
            }
        };
        testComparator t1 = new testComparator("t1", 1);
        testComparator t2 = new testComparator("t2", 5);
        testComparator t3 = new testComparator("t3", 3);
        testComparator t4 = new testComparator("t4", 4);
        Queue<testComparator> q = new PriorityQueue<testComparator>(10, order);
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