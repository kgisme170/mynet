import org.apache.spark.sql.SparkSession
object wordCountSimple {
  def main(args: Array[String]): Unit = {
	val spark = SparkSession
	  .builder
	  .master("local")
	  .appName("wordCount9999")
	  .getOrCreate()

	spark.sparkContext.setLogLevel("WARN")
	import spark.sqlContext.implicits._
	val df = Seq(("2019-07-01 12:01:19.000"),
	  ("2019-06-24 12:01:19.000"),
	  ("2019-11-16 16:44:55.406"),
	  ("2019-11-16 16:50:59.406")).toDF("input_timestamp")
	df.withColumn("datetype_timestamp", to_timestamp(col("input_timestamp")))
	  .printSchema()
	spark.close()
  }
}
