import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapred.SequenceFileOutputFormat
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object sequenceFile {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("My App")
    val sc = new SparkContext(conf)

    //写sequenceFile，
    val rdd = sc.parallelize(List(("scala", 3), ("java", 6), ("python", 2)))
    rdd.saveAsSequenceFile("output")

    //读sequenceFile
    val output = sc.sequenceFile("output", classOf[Text], classOf[IntWritable])
    output.map{case(x,y)=>(x.toString, y.get)}.foreach(println)
    output.saveAsHadoopFile("output1", classOf[Text], classOf[IntWritable], classOf[SequenceFileOutputFormat[Text, IntWritable]])

    //写objectFile
    val ro = sc.parallelize(List("scala", "java", "python"))
    ro.saveAsObjectFile("output2")

    //读objectFile
    val ou = sc.objectFile("output2")
    ou.foreach(println)
  }
}