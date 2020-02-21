import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types
import org.apache.spark.sql.functions._
object wordCountSimple {
  def main(args: Array[String]): Unit = {
	val spark = SparkSession
	  .builder
	  .master("local")
	  .appName("wordCountSimple")
	  .getOrCreate()

	spark.sparkContext.setLogLevel("WARN")
	import spark.sqlContext.implicits._
	val df = Seq(("abc", "2019-07-01 12:01:19.000"),
	  ("xyz", "2019-06-24 12:01:19.000"),
	  ("abc", "2019-11-16 16:44:55.406"),
	  ("abc", "2019-11-16 16:50:59.406")).toDF("value", "date")
	val res = df.select(
	  $"value",
	  $"date",
	  unix_timestamp($"date", "yyyy/MM/dd HH:mm:ss").as("timestamp"))
	res.printSchema
	res.show(false)
	println("step1")
	spark.close()
	println("step2")
  }
}
