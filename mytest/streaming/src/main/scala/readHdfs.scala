import org.apache.spark.{SparkConf, SparkContext}

object readHdfs {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().set("spark.master", "local").set("spark.app.name", "spark demo")
    val sc = new SparkContext(conf);
    val textFileRdd = sc.textFile("hdfs://localhost:9000/tmp/testfile.txt")
    textFileRdd.saveAsTextFile("hdfs://localhost:9000/mytest14")
    textFileRdd.saveAsObjectFile("hdfs://localhost:9000/myObject01/")
  }
}