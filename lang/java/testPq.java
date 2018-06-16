import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class testPq{
    private String name;
    private int population;
    public testPq(String _n, int _p){
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
        Comparator<testPq> order = new Comparator<testPq>(){
            public int compare(testPq o1, testPq o2){
                int p1=o1.getPopuliation();
                int p2=o2.getPopuliation();
                if(p1>p2)return 1;
                else if(p1<p2)return -1;
                else return 0;
            }
        };
        testPq t1=new testPq("t1",1);
        testPq t2=new testPq("t2",5);
        testPq t3=new testPq("t3",3);
        testPq t4=new testPq("t4",4);
        Queue<testPq> q = new PriorityQueue<testPq>(10,order);
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