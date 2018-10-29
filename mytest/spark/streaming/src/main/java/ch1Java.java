import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class ch1Java {
    public static void main(String[] s) {
        System.out.println("spark 配置");
        SparkConf conf = new SparkConf();
        conf.setAppName("第一个JavaStream程序").setMaster("local[4]");
        System.out.println("创建stream context");
        JavaStreamingContext ctx = new JavaStreamingContext(conf, Durations.seconds(2));
        JavaReceiverInputDStream<String> lines =
                ctx.socketTextStream("localhost", 9087, StorageLevel.MEMORY_AND_DISK_SER_2());
        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });
        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        });
        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });
        wordCounts.print(10);//打印最前面10个词语
        ctx.start();
        try {
            ctx.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
