import org.apache.spark.SparkConf
import org.apache.spark.sql._

object sqlBasics extends App {
  val dir = System.getProperty("user.dir")
  println(dir)
  val conf = new SparkConf().setAppName("spark sql")
    .set("spark.sql.warehouse.dir", dir)
    .setMaster("local[4]");
  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()
  val df = spark.read.option("multiLine", true).json("my.json")
  println("查看表")
  df.show()
  println("查看schema")
  df.printSchema()
  println("查看Name列")
  df.select("Name").show()
  import spark.implicits._// This import is needed to use the $-notation
  df.select($"Name", $"No_Of_Supervisors".gt(3)).show()
  df.groupBy("Name").count().show()
  df.filter($"No_Of_Supervisors">2).show()
  df.createOrReplaceTempView("people")
  spark.sql("select * from people").show()
  //df.createGlobalTempView("people")
}