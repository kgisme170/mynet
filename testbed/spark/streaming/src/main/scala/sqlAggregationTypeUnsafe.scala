import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}

object sqlAggregationTypeUnsafe extends App {
  val conf = new SparkConf().setAppName("spark sql")
    .set("spark.sql.warehouse.dir", System.getProperty("user.dir"))
    .setMaster("local[4]");
  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()
  val df = spark.read.json("employees.json")
  df.createOrReplaceTempView("employees")
  spark.udf.register("myAverage", MyAverage)
  val result = spark.sql("SELECT myAverage(salary) as average_salary FROM employees")
  df.createOrReplaceTempView("employees")
  df.show()

  object MyAverage extends UserDefinedAggregateFunction {
    def inputSchema: StructType = StructType(StructField("inputColumn", LongType) :: Nil)

    def bufferSchema: StructType = {
      StructType(StructField("sum", LongType) :: StructField("count", LongType) :: Nil)
    }

    def dataType: DataType = DoubleType

    def deterministic: Boolean = true

    def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0L
      buffer(1) = 0L
    }

    def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      if (!input.isNullAt(0)) {
        buffer(0) = buffer.getLong(0) + input.getLong(0)
        buffer(1) = buffer.getLong(1) + 1
      }
    }

    def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
      buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
    }

    def evaluate(buffer: Row): Double = buffer.getLong(0).toDouble / buffer.getLong(1)
  }
  result.show()
}
