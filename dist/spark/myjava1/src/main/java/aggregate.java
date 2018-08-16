import java.io.Serializable;
import java.util.*;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

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
        List<Integer> a = Arrays.asList(11,23,4,5,7);
        JavaRDD<Integer> rdd = sc.parallelize(a);
        rdd.collect();
        Integer sum = rdd.reduce(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });
        System.out.println(sum);
        AvgCount result = rdd.aggregate(initial, addAndCount, combine);
        System.out.println(result.avg());
        JavaPairRDD<Integer, Integer> pairs = rdd.mapToPair(new PairFunction<Integer, Integer, Integer>() {
            public Tuple2<Integer, Integer> call(Integer x) throws Exception {
                return new Tuple2(x, x*x);
            }
        });
        JavaPairRDD<Integer, Integer> big = pairs.filter(new Function<Tuple2<Integer, Integer>, Boolean>() {
            public Boolean call(Tuple2<Integer, Integer> integerIntegerTuple2) throws Exception {
                return integerIntegerTuple2._2() > 20;
            }
        });
        big.foreach(new VoidFunction<Tuple2<Integer, Integer>>() {
            public void call(Tuple2<Integer, Integer> integerIntegerTuple2) throws Exception {
                System.out.println(integerIntegerTuple2._2);
            }
        });
    }
}
