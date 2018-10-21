import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
public class hadoopFileOld {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("wordCount").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Tuple2<String, Integer>> data = Arrays.asList(new Tuple2<>("a", 9), new Tuple2<>("b", 10));
        JavaPairRDD<String, Integer> dataRdd = sc.parallelizePairs(data);
        String dir = "myResult";
        checkDeleteDir.checkExistenceAndDelete(dir);
        dataRdd.saveAsHadoopFile(dir, Text.class, IntWritable.class, SequenceFileOutputFormat.class);
    }
}