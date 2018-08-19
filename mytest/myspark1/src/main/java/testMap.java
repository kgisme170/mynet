import java.io.File;
import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class myjava {
    public static void main(String[] args) {
        System.out.println("hw");
        SparkConf conf = new SparkConf().setAppName("wordCount").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> input = sc.textFile("pom.xml");
        JavaRDD<String> words = input.flatMap(
                new FlatMapFunction<String, String>() {
                    @Override
                    public java.util.Iterator<String> call(String s) throws Exception {
                        return Arrays.asList(s.split((" "))).iterator();
                    }
                }
        );
        JavaPairRDD<String, Integer> counts = words.mapToPair(
                new PairFunction<String, String, Integer>() {
                    @Override
                    public Tuple2<String, Integer> call(String s) throws Exception {
                        return new Tuple2(s, 1);
                    }
                }
        ).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });
        String dir = "myResult";
        checkDeleteDir.checkExistenceAndDelete(dir);
        counts.saveAsTextFile(dir);
    }
}