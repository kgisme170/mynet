import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object streamLog {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint("/tmp/checkpoint1")
    val lines = ssc.textFileStream("/tmp/tmp1")

    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    //以上打印一个数字k-v
    //(file13,1)
    //(file12,1)
    //(file14,1)
    //或者(file15,3)

    val wins = lines.window(Seconds(30), Seconds(10))
    val winCount = wins.count()
    winCount.print()
    //以上打印一个数字，每隔10s更新一次，会包含最30s的数据总和

    val myDStream = lines.map(k => (k, 1))
    val dsCount = myDStream.countByValueAndWindow(Seconds(30), Seconds(10))
    dsCount.print()
    /*
    echo "file1"> /tmp/tmp1/file1
    echo "file2"> /tmp/tmp1/file2 && echo "file3"> /tmp/tmp1/file3 && echo "file4"> /tmp/tmp1/file4
    echo "file12"> /tmp/tmp1/file12 && echo "file13"> /tmp/tmp1/file13 && echo "file14"> /tmp/tmp1/file14
    echo "file15"> /tmp/tmp1/file17 && echo "file15"> /tmp/tmp1/file16 && echo "file15"> /tmp/tmp1/file15

    spark输出是:
    -------------------------------------------
    Time: 1540140075000 ms
    -------------------------------------------
    ((file1,1),1)

    -------------------------------------------
    Time: 1540140085000 ms
    -------------------------------------------
    ((file4,1),1)
    ((file2,1),1)
    ((file3,1),1)
    ((file1,1),1)

    -------------------------------------------
    Time: 1540140095000 ms
    -------------------------------------------
    ((file4,1),1)
    ((file13,1),1)
    ((file2,1),1)
    ((file12,1),1)
    ((file14,1),1)
    ((file3,1),1)
    ((file1,1),1)

    -------------------------------------------
    Time: 1540140105000 ms
    -------------------------------------------
    ((file15,1),3)
    ((file4,1),1)
    ((file13,1),1)
    ((file2,1),1)
    ((file12,1),1)
    ((file14,1),1)
    ((file3,1),1)

    -------------------------------------------
    Time: 1540140115000 ms
    -------------------------------------------
    ((file15,1),3)
    ((file13,1),1)
    ((file12,1),1)
    ((file14,1),1)

    -------------------------------------------
    Time: 1540140125000 ms
    -------------------------------------------
    ((file15,1),3)

    -------------------------------------------
    Time: 1540140135000 ms
    -------------------------------------------

    -------------------------------------------
    Time: 1540140145000 ms
    -------------------------------------------
    */
    //spark-submit --deploy-mode cluster --supervise --master spark://..... App.jar
    ssc.start()
    ssc.awaitTermination()
  }
}