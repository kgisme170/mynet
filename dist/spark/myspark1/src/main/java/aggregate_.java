import java.util.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import static java.util.Arrays.asList;

class AvgCount{
    public AvgCount(int total, int num){
        this.total = total;
        this.num = num;
    }
    public int total;
    public int num;
    public double avg(){return total/(double)num;}
}
public class aggregate_ {
    public static void main(String[] args) {
        Function2<AvgCount, Integer, AvgCount> addAndCount = (AvgCount avgCount, Integer integer) -> {
            avgCount.total += integer;
            avgCount.num += 1;
            return avgCount;
        };
        Function2<AvgCount, AvgCount, AvgCount> combine = (AvgCount avgCount, AvgCount avgCount2) -> {
            avgCount.total += avgCount2.total;
            avgCount.num += avgCount2.num;
            return avgCount;
        };
        SparkConf conf = new SparkConf().setAppName("wordCount").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        AvgCount initial = new AvgCount(0, 0);
        List<Integer> a = asList(11, 23, 4, 5, 7);
        JavaRDD<Integer> rdd = sc.parallelize(a);
        rdd.collect();
        Integer sum = rdd.reduce((Integer integer, Integer integer2) ->  integer + integer2);
        System.out.println(sum);
        AvgCount result = rdd.aggregate(initial, addAndCount, combine);
        System.out.println(result.avg());
        JavaPairRDD<Integer, Integer> pairs = rdd.mapToPair((Integer x) -> new Tuple2(x, x * x));
        JavaPairRDD<Integer, Integer> big = pairs.filter((Tuple2<Integer, Integer> integerIntegerTuple2) -> integerIntegerTuple2._2() > 20);
        big.foreach((Tuple2<Integer, Integer> integerIntegerTuple2) -> System.out.println(integerIntegerTuple2._2));
    }
}
