import java.io.Serializable;
import java.util.*;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
class AvgCount implements Serializable{
    public AvgCount(int total, int num){
        this.total = total;
        this.num = num;
    }
    public int total;
    public int num;
    public double avg(){return total/(double)num;}
}
public class aggregate {
    public static void main(String[] args){
        Function2<AvgCount, Integer, AvgCount> addAndCount = new Function2<AvgCount, Integer, AvgCount>() {
            public AvgCount call(AvgCount avgCount, Integer integer) throws Exception {
                avgCount.total+=integer;
                avgCount.num+=1;
                return avgCount;
            }
        };
        Function2<AvgCount, AvgCount, AvgCount> combine = new Function2<AvgCount, AvgCount, AvgCount>() {
            public AvgCount call(AvgCount avgCount, AvgCount avgCount2) throws Exception {
                avgCount.total += avgCount2.total;
                avgCount.num += avgCount2.num;
                return avgCount;
            }
        };
        SparkConf conf = new SparkConf().setAppName("wordCount").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        AvgCount initial = new AvgCount(0,0);
        List<Integer> a = Arrays.asList(11,23,4,5);
        JavaRDD<Integer> rdd = sc.parallelize(a);
        AvgCount result = rdd.aggregate(initial, addAndCount, combine);
        System.out.println(result.avg());
    }
}
