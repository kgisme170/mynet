import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class testPq_ {
    private String name;
    private int population;
    public testPq_(String _n, int _p){
        name = _n;
        population = _p;
    }
    public String getName(){
        return name;
    }
    public int getPopuliation(){
        return population;
    }
    public static void main(String[] args){
        Comparator<testPq_> order = new Comparator<testPq_>(){
            public int compare(testPq_ o1, testPq_ o2){
                int p1=o1.getPopuliation();
                int p2=o2.getPopuliation();
                if(p1>p2)return 1;
                else if(p1<p2)return -1;
                else return 0;
            }
        };
        testPq_ t1=new testPq_("t1",1);
        testPq_ t2=new testPq_("t2",5);
        testPq_ t3=new testPq_("t3",3);
        testPq_ t4=new testPq_("t4",4);
        Queue<testPq_> q = new PriorityQueue<testPq_>(10,order);
        q.add(t1);
        q.add(t2);
        q.add(t3);
        q.add(t4);
        System.out.println(q.size());
        while(q.size()!=0){
            System.out.println(q.poll());
        }
    }
}