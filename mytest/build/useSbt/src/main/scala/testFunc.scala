object testFunc extends App{
  println("ok")
  def func(name1: String, name2: String, f: (Int, Int) => String): (String, Int) => Int = {
    val temp = f(1, 2)
    (a: String, b: Int) => {
      a.size + b + temp.size
    }
  }

  val value = func("name1", "name2", (a: Int, b: Int) => {
    (a + b).toString()
  })("aaa", 10)

  println(value)
  def withRetry[T](ms: Int, maxRetryTimes: Int, actionName: String = "MyAction")(work: => T)(cleanUp: => Unit): T = {
    val again = ms.toLong
    def retry(iteration: Int): T = {
      try work
      catch {
        case e: Throwable if iteration < maxRetryTimes =>
          println(s"运行失败 $actionName: $e, 第 $iteration 次, 等待并重试: $again(ms)")
          Thread.sleep(again)
          retry(iteration + 1)
        case e: Throwable => throw e
      } finally {
        cleanUp
      }
    }

    retry(0)
  }
  withRetry(500, 3, "testAction"){println("开始工作")}{println("清理完毕")}
}
