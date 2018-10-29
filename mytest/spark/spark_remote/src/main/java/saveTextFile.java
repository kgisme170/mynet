import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;
public class saveTextFile {
    public static void main(String[] args) {
        System.out.println("hw");
        SparkConf conf = new SparkConf().setAppName("wordCount").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> input = sc.textFile("pom.xml");
        JavaRDD<String> words = input.flatMap((String s) -> Arrays.asList(s.split("\\s+")).iterator());

        JavaPairRDD<String, Integer> counts = words.mapToPair((String s) -> new Tuple2(s, 1))
                .reduceByKey(new Function2<Integer, Integer, Integer>() {
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