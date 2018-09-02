import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.Matrices
import org.apache.spark.mllib.linalg.distributed.RowMatrix
object mailFilter {
  def main(): Unit ={
    val conf = new SparkConf().setMaster("local").setAppName("My App")
    val sc = new SparkContext(conf)
    val spam = sc.textFile("spam.txt")
    val normal =sc.textFile("normal.txt")
    var tf = new HashingTF(numFeatures = 10000)
    val spamFeatures = spam.map(email=>tf.transform(email.split(" ")))
    val normalFeatures = normal.map(email=>tf.transform(email.split(" ")))
    val positive = spamFeatures.map(f=>LabeledPoint(1,f))
    val negative = normalFeatures.map(f=>LabeledPoint(0,f))
    val training = positive.union(negative)
    training.cache()
    val model = new LogisticRegressionWithSGD().run(training)
    val positiveTest = tf.transform("O M G cheap stuff".split(" "))
    val negativeTest = tf.transform("Hi I'm learning".split(" "))
    println(model.predict(positiveTest))
    println(model.predict(negativeTest))
    ////////////
    val denseVec1 = Vectors.dense(1.0, 2.0, 3.0)
    val denseVec2 = Vectors.dense(Array(1.0, 2.0, 3.0))

    val sparseVec = Vectors.sparse(4, Array(0,2), Array(1.0,2.0))
    /////////////
    //val points:RDD[LabeledPoint]=None
    ////下一句的方法已經不存在了
    //val lr=new LinearRegressionWithSGD().setNumIterations(200).setIntercept(true)
    //val model = lr.run(points)
    //println(model.weights, model.intercept)
    //val points:RDD[Vector]=None

    //val mat = new RowMatrix(points)
    //val pc = mat.computePrincipalComponents(2)
    //val projected = mat.multiply(pc).rows
    //val m = KMeans.train(projected, 10)
  }
}
