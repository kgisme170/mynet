import java.sql.DriverManager
import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

object useJdbcRdd extends App {
    val conf = new SparkConf().setAppName("mysql").setMaster("local[4]")
  val sc = new SparkContext(conf)

  def createConnection() = {
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    DriverManager.getConnection("jdbc:mysql://localhost:3306/db1", "root", "mypassword")
  }

  val data = new JdbcRDD(sc, createConnection,
    sql = "select * from t1 WHERE ID >= ? AND ID <= ?",
    lowerBound = 1,
    upperBound = 100,
    numPartitions = 2,
    mapRow = r => (r.getInt(1), r.getString(2)))

  data.foreach(println)
}