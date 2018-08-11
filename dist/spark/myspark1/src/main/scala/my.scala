import java.io.File

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/*
 * 读取一个文件并分解到目标目录下
 */
object ch02 extends App {
  def deleteDir(dir: File): Unit = {
    val files = dir.listFiles();
    files.foreach(f => {
      if (f.isDirectory()) {
        deleteDir(f);
      } else {
        f.delete();
      }
    });
    dir.delete();
  }

  def checkExistenceAndDelete(dir: String): Unit = {
    val file = new File(dir);
    if (file.exists()) {
      deleteDir(file);
    }
  }

  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  val input = sc.textFile("pom.xml");
  val words = input.flatMap(line => line.split(" "));
  val counts = words.map(word => (word, 1)).reduceByKey { case (x, y) => x + y }
  val dir = "myResultDir";
  checkExistenceAndDelete(dir);
  counts.saveAsTextFile(dir);
}