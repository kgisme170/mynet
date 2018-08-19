object ch17 extends App {

    class Test[T](val v: T) {
        def check = {
            if (v.isInstanceOf[String]) {
                println("The param is String")
            }
            if (v.isInstanceOf[Int]) {
                println("The param is Int")
            }
        }
    }

    val strv = "123"
    val intv = 123
    val strTest = new Test[String](strv)
    val intTest = new Test[Int](intv)
    strTest.check
    intTest.check
    var a = "Red"
    var b = "Green"

    class Pair[T <: Comparable[T]](val first: T, val second: T) {
        def smaller =
            if (first.compareTo(second) < 0) first else second
    }

    class Pair1[T](val first: T, val second: T) {
        def replaceFirst[R >: T](newFirst: R) = new Pair1[R](newFirst, second)
    }

    class Pair2[T <% Comparable[T]](val first: T, val second: T) {
        def smaller =
            if (first.compareTo(second) < 0) first else second
    }

    class Pair3[T: Ordering](val first: T, val second: T) {
        def smaller(implicit ord: Ordering[T]) =
            if (ord.compare(first, second) < 0) first else second
    }

    class Pair4[T](val first: T, val second: T) {
        def smaller(implicit ev: T <:< Ordered[T]) =
            if (first < second) first else second
    }

    val p = new Pair(a, b)
    println(p.smaller)
    val p1 = new Pair1(a, b)
    p1.replaceFirst("ok")
    val p2 = new Pair2(2, 3)
    println(p2.smaller)

    //5 times println("HI")
    def makePair[T: Manifest](first: T, second: T) = {
        val r = new Array[T](2)
        r(0) = first
        r(1) = second
        r
    }

    val a1 = makePair(1, 2)
    println(a1(1))
    val p4 = new Pair4(3, 4)
    val bo = Array("Hello", "Fred").corresponds(Array(5, 4))(_.length == _)
    println(bo)

    class Animal {}

    class Bird extends Animal {}

    class Consumer[-S, +T] {
        def m1[U >: T](u: U) = {}

        def m2[U <: S](u: U) = {}
    }

    val c1: Consumer[Animal, Bird] = new Consumer[Animal, Bird]()
    val c2: Consumer[Bird, Animal] = c1
    c2.m1(new Animal);
    c1.m2(new Bird);
}