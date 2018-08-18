import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext
object hiveCtx extends App {
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val context = new SparkContext(conf)
  val ctx = new HiveContext(context)
  val lp = ctx.jsonFile("test2.json")
  lp.registerTempTable("user");
  val schemaRDD = ctx.sql("select name, lovePandas from user");
  schemaRDD.columns.foreach(println)
  schemaRDD.show()
  schemaRDD.printSchema()
}