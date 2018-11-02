object testPq extends App {

  import scala.util.Sorting

  implicit val ord: Ordering[(Int, String)] = Ordering.by(_._1)
  val pairs = Array(("a", 5, 2), ("c", 3, 1), ("b", 1, 3), ("d", 2, 5))
  Sorting.quickSort(pairs)(Ordering.by[(String, Int, Int), Int](_._3).reverse)
  //Sorting.quickSort(pairs)(Ordering.by[(String,Int,Int),Int](_._3).reverse)
  pairs.foreach(println)
  println("----------------")
  Sorting.quickSort(pairs)(Ordering[(Int, String)].on(x => (x._3, x._1)))
  pairs.foreach(println)
  println("----------------")

  val priorityDemo = collection.mutable.PriorityQueue[(Int, String)]()
  priorityDemo.enqueue((2, "hello"))
  priorityDemo.enqueue((5, "ct"))
  priorityDemo.enqueue((1, "work"))
  priorityDemo.enqueue((3, "word"))
  (1 to priorityDemo.size).foreach(x => println(priorityDemo.dequeue()))

  println(List(1, 2, 3).fold(0)(_ + _))
  println(List(1, 2, 3).reduce(_ + _))
}