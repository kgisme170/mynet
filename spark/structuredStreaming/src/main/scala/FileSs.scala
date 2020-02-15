import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

object FileSs {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("StructuredNetworkWordCount")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    import spark.implicits._
    val userSchema = new StructType().add("name", "string").add("age", "integer")
    val lines = spark.readStream
      .option("sep", ";")
      .schema(userSchema)
      .csv("file:///data/*")

    val query = lines.writeStream
      .outputMode("append")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
