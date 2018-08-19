object ch13 extends App {
    val i = Iterable(0xFF, 0xFF00, 0xFF0000)
    i.foreach(println _)
    Set("World", "Hello").foreach(println _)
    val m = scala.collection.mutable.Map("key1" -> 2, "key2" -> 3)
    val l = List(1, 2, 3)
    val iter = l.iterator
    while (iter.hasNext)
        println(iter.next)
    m("key3") = 4
    println("------")
    (4 :: l).foreach(println _)
    val l2 = List(1, 2, "3")
    (9 :: List(4, 2)).foreach(println _)
    (1 :: 3 :: 5 :: Nil).foreach(println _)
    println(l.head)

    def sum(lst: List[Int]): Int =
        lst match {
            case Nil => 0
            case h :: t => h + sum(t)
        }

    println(sum(l))

    val lst = scala.collection.mutable.LinkedList(1, -2, 7, -9)
    var cur = lst
    while (cur != Nil && cur.next != Nil) {
        cur.next = cur.next.next
        cur = cur.next
    }
    println("------")
    lst.foreach(println _)
    println("------")
    cur.foreach(println _) //?collection is iterator, cur points to the end
    println("------")

    val s = Set(1, 2, 3, 4, 5, 6)
    s.foreach(println _)
    val weekday = scala.collection.mutable.LinkedHashSet("Mo", "Tu", "We", "Th", "Fr")
    weekday.foreach(println _)
    val digits = Set(1, 7, 2, 9)
    println(digits contains 0)
    println(Set(1, 2) subsetOf digits)
    List("Peter", "John").map(_.toUpperCase).foreach(println _)
    println(List(1, 7, 2, 9).foldLeft(0)(_ - _))
    println((0 /: List(1, 7, 2, 9)) (_ - _))

    def ulcase(s: String) = Vector(s.toUpperCase(), s.toLowerCase())

    val names = List("Peter", "Paul")
    names.map(ulcase).foreach(println _)
    names.flatMap(ulcase).foreach(println _)

    val freq = scala.collection.mutable.Map[Char, Int]()
    for (c <- "Mississippi") freq(c) = freq.getOrElse(c, 0) + 1
    freq.foreach(println _)
    println((Map[Char, Int]() /: "Mississippi") {
        (m, c) => m + (c -> (m.getOrElse(c, 0) + 1))
    })
}