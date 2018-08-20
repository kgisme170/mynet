import org.apache.spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

case class Customer(customer_id: Int, name: String, city: String, state: String, zip_code: String)
case class C(id: Int, name: String, age: Int)
object sql extends App {
  println("1.---------------------")
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  println("2.---------------------")
  val sc = new SparkContext(conf)
  println("3.---------------------")
  val sqlContext = new SQLContext(sc)
  println("4.---------------------")
  val mySpark = SparkSession
    .builder()
    .appName("Spark SQL basic example")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()
  println("5.---------------------")
  import mySpark.implicits._// RDDs 到 DataFrames 的隐式转换

  def test01() {
    println("6.---------------------")
    val dfCustomers = sc.textFile("customers.txt").map(_.split(","))
      .map(p => Customer(p(0).trim.toInt, p(1), p(2), p(3), p(4))).toDF()
    println("7.---------------------")
    dfCustomers.registerTempTable("customers")
    println("8.---------------------")
    dfCustomers.show()
    println("9.---------------------")
    dfCustomers.printSchema()
    println("10.---------------------")
    dfCustomers.select("name").show()
    println("11.---------------------")
    dfCustomers.select("name", "city").show()
    // 根据id选择客户
    println("12.---------------------")
    dfCustomers.filter(dfCustomers("customer_id").equalTo(500)).show()
    println("13.---------------------")
    dfCustomers.groupBy("zip_code").count().show()
    println("14.---------------------")
  }
  def test02() {
    // 用编程的方式指定模式
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val rddCustomers = sc.textFile("customers.txt")
    val rowRDD = rddCustomers.map(_.split(",")).map(p => Row(p(0).trim, p(1), p(2), p(3), p(4))) // 将RDD（rddCustomers）记录转化成Row。

    val schemaString = "customer_id name city state zip_code"// 用字符串编码模式
    val schema = StructType(schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, true))) // 用模式字符串生成模式对象

    val dfCustomers = sqlContext.createDataFrame(rowRDD, schema)
    dfCustomers.registerTempTable("customers") // 将DataFrame注册为表

    // SQL查询的返回结果为DataFrame对象，支持所有通用的RDD操作。
    // 可以按照顺序访问结果行的各个列。
    val names = sqlContext.sql("SELECT name FROM customers") // 用sqlContext对象提供的sql方法执行SQL语句。
    names.map(t => "Name: " + t(0)).collect().foreach(println)

    val customersByCity = sqlContext.sql("SELECT name,zip_code FROM customers ORDER BY zip_code")
    customersByCity.map(t => t(0) + "," + t(1)).collect().foreach(println)
  }
  def test03() {
    val arr = Array("1,tom,12", "2,tomas,13", "3,tomasLee,14")
    val rdd1 = sc.makeRDD(arr)
    val rdd2 = rdd1.map(e => {
      val arr = e.split(",");
      C(arr(0).toInt, arr(1), arr(2).toInt)
    })
    val df = rdd2.toDF()
    df.printSchema
    df.show
  }
  test03
}