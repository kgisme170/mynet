import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;

public class mytest05 {
    public static void main(String[] args){
        SparkConf conf=new SparkConf().setMaster("local").setAppName("My App");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> input=sc.textFile("/home/a/Downloads/spark-1.6.3-bin-hadoop2.6/README.md");
        JavaRDD<String> words = input.flatMap(new FlatMapFunction<String, String>(){
            public Iterable<String> call(String x){
                return Arrays.asList(x.split(" "));
            }
        });
        JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2(s,1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });
        counts.saveAsTextFile("/home/a/mytest05.txt");
    }
}
