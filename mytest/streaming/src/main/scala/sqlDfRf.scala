import org.apache.spark.SparkConf
import org.apache.spark.sql._
object sqlDfRf {
  val conf = new SparkConf().setAppName("spark sql")
    .set("spark.sql.warehouse.dir", System.getProperty("user.dir"))
    .setMaster("local[4]");
  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()

  def main(args: Array[String]) {
    val df = spark.read.option("multiLine", true).json("employees.json")
    df.write.bucketBy(42,"name").sortBy("age").saveAsTable("people.bucket")
    df.write.partitionBy("favorite_color").format("parquet").save("2.parquet")
  }
}