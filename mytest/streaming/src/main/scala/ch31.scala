import java.sql.{DriverManager, ResultSet}

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

object ch31 extends App {
  val conf = new SparkConf().setMaster("local[4]").setAppName("ch31");
  val ctx = new SparkContext(conf);
  val rdd = new JdbcRDD(ctx, () =>
    {DriverManager.getConnection("jdbc:derby:temp/Jdbc-RDDExample")},
    "SELECT EMP_id,NAME FROM EMP WHERE Age>=? and ID <=?",
    20, 30, 3,
    (r: ResultSet) => {
      r.getInt(1);
      r.getString(2)
    }).cache();
  System.out.println(rdd.first)
}
