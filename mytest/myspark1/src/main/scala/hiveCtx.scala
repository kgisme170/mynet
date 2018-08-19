import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext
object hiveCtx extends App {
  def fromJson(): Unit ={
    val lp = ctx.jsonFile("test2.json")
    lp.registerTempTable("user");
    val schemaRDD = ctx.sql("select name, lovePandas from user");
    schemaRDD.columns.foreach(println)
    schemaRDD.show()
    schemaRDD.printSchema()
  }
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val context = new SparkContext(conf)
  val ctx = new HiveContext(context)
  val rows = ctx.sql("select * from z")
  rows.show()
  rows.printSchema()
}