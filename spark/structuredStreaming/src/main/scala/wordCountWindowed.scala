import java.sql.Timestamp
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
object wordCountWindowed {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("wordCountWindowed")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
  }
}
