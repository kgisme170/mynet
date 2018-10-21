import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
object hadoopFile extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val job = new Job()
  val data = sc.newAPIHadoopFile("test.json",
    classOf[KeyValueTextInputFormat], classOf[Text], classOf[Text], job.getConfiguration)
  data.foreach(println)
  data.saveAsNewAPIHadoopFile("result.json", classOf[Text], classOf[Text], classOf[TextOutputFormat[Text, Text]], job.getConfiguration)
}