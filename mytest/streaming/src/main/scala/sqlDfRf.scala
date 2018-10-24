import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{StructType, _}

object sqlDfRf {
  val conf = new SparkConf().setAppName("spark sql")
    .set("spark.sql.warehouse.dir", System.getProperty("user.dir"))
    .setMaster("local[4]");
  val spark = SparkSession
    .builder()
    .config(conf)
    .getOrCreate()

  def main(args: Array[String]) {
    typeUnsafeAggregation()
  }

  def typeUnsafeAggregation(): Unit = {
    spark.udf.register("myAverage", myAverage)

    val df = spark.read.option("multiLine", true).json("employees.json")
    df.createOrReplaceTempView("people")
    spark.sql("select myAverage(salary) as avg from people").show()
  }

  object myAverage extends UserDefinedAggregateFunction {
    override def inputSchema: StructType = StructType(StructField("inputColumn", LongType) :: Nil)

    override def bufferSchema: StructType = StructType(StructField("sum", LongType) :: StructField("count", LongType) :: Nil)

    override def dataType: DataType = DoubleType

    override def deterministic: Boolean = true

    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      println("initialize")
      buffer(0) = 0
      buffer(1) = 0
    }

    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      println("update")
      buffer(0) = buffer.getLong(0) + input.getLong(0)
      buffer(1) = buffer.getLong(1) + input.getLong(1)
    }

    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      println("merge")
      buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
      buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
    }

    override def evaluate(buffer: Row): Any = {
      println("evaluate")
      buffer.getLong(0).toDouble / buffer.getLong(1)
    }
  }
}