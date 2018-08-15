import java.io.File

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/*
 * 读取一个文件并分解到目标目录下
 */
object ch02 extends App {
  def deleteDir(dir: File): Unit = {
    val files = dir.listFiles()
    files.foreach(f => {
      if (f.isDirectory()) {
        deleteDir(f)
      } else {
        f.delete()
      }
    })
    dir.delete()
  }

  def checkExistenceAndDelete(dir: String): Unit = {
    val file = new File(dir);
    if (file.exists()) {
      deleteDir(file)
    }
  }

  def test01() {
    val input = sc.textFile("/Users/x/Documents/spark-2.3.1-bin-hadoop2.7/README.md");
    input.persist()
    val words = input.flatMap(line => line.split(" "));
    val counts = words.map(word => (word, 1)).reduceByKey { case (x, y) => x + y }
    val dir = "myResultDir"
    checkExistenceAndDelete(dir)
    counts.saveAsTextFile(dir)
    val a = counts.collect()
    a.foreach(println)
  }

  def test02(): Unit = {
    val lines = sc.parallelize((List("pandas", "i like pandas", "I have", "my pandas")))
    println(lines.count())
    val p = lines.filter(l => l.contains("panda"))
    val q = lines.filter(l => l.contains("like"))
    val u = q.union(q)
    p.take(1).foreach(println)
    println(p.count())
    println(divide1.isDefinedAt(0))
  }

  val divide1:PartialFunction[Int, Int]= {
    case d: Int if d != 0 => 100 / d
  }
  val conf = new SparkConf().setMaster("local").setAppName("My App")
  val sc = new SparkContext(conf)
  test01()
}