import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object streamlog {
  def main(args: Array[String]) {

    //创建SparkConf对象
    val sparkConf = new SparkConf().setAppName("HdfsWordCount").setMaster("local[2]")
    // Create the context
    //创建StreamingContext对象，与集群进行交互
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // Create the FileInputDStream on the directory and use the
    // stream to count words in new files created
    //如果目录中有新创建的文件，则读取
    val lines = ssc.textFileStream("/Users/x/temp")
    //分割为单词
    val words = lines.flatMap(_.split(" "))
    //统计单词出现次数
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)

    //set the Checkpoint directory
    ssc.checkpoint("/Users/x/temp")
    val wins = lines.window(Seconds(30), Seconds(10))
    val winCount = wins.count()

    val myDStream = lines.map(k=>(k,1))
    /*
    val countDS = myDStream.reduceByKeyAndWindow(
      {(x,y)=>x+y},
      {(x,y)=>x-y},
      Seconds(30),
      Seconds(10))
    */
    val dsCount = myDStream.countByValueAndWindow(Seconds(30),Seconds(10))
    //updateStateByKey? SequenceFile foreachRDD // ~路徑判斷 cogroup
    //getOrCreate檢查點
    //monit工具
    //獨立集群 提交驅動器程序時候 --supervise
    //spark-submit --deploy-mode cluster --supervise --master spark://..... App.jar
    //spark-submit --conf spark.executor.exraJavaOptions=-XX:+UserConcMarkSweepGC App.jar
    val conf = new SparkConf()
    val dir = "/tmp"
    def createStreamingContext()={
      val sc = new SparkContext(conf)
      val ssc = new StreamingContext(sc, Seconds(1))
      ssc.checkpoint(dir)
      ssc
    }
    StreamingContext.getOrCreate(dir,createStreamingContext)
    //打印结果
    wordCounts.print()
    //启动Spark Streaming
    ssc.start()
    //一直运行，除非人为干预再停止
    ssc.awaitTermination()
  }
}
