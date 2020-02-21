object o101 { //extends App {
  def main(args: Array[String]): Unit = {
  }

  val A : Map[Int, Int] = Map()
  def fibonacci(n : Int) : Int = {
      if n<=0 {
          panic("Should not have n <= 0")
      } else if n <= 2 {
          return 1
      } else {
          val ret = 0
          if ! A.contains(n-1) {
              A[n-1] = fibonacci(n-1)
          }
          if ! A.contains(n-2) {
              A[n-2] = fibonacci(n-2)
          }
          return A[n-1] + A[n-2]
      }
  }

  def func01() : Unit = {
    val vb : Byte = '1'
    val vc : Char = 2
    val vs : Short = 3
    val vi : Int = 4
    val vl : Long = 5
    val vf : Float = 6
    val vd : Double = 7
    val vB : Boolean = true

    var vc2 = 'a'
    vc2 = 'b'
    val vs2 = "abc"
    val vm = if (vs==5)10 else 20
    println(vm)

    for(i <- 1 to 20 if i%2==0;if i!=2)print(i)
    println
    for(i <- 1 to 20;j<- i to 20) {
        print(i*j)
        print(',')
    }
    val list: List[Any] = List(
        "string",
        123,
        'a',
        true,
        (x:Int)=>print(x)
    )
    list.foreach(e=>println(e))
    val face: Char = '☺'
    val number: Int = face
    print(number)
  }
  println(fibonacci(5))
  println(fibonacci(8))
}