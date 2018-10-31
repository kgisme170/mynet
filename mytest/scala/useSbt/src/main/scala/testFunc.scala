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
  def withRetry[T](initIntervalMs: Int, maxRetryTimes: Int, actionName: String = "Anonymous")(work: => T)(cleanUp: => Unit): T = {
    def retry(iteration: Int): T = {
      try work
      catch {
        case e: Throwable if iteration < maxRetryTimes =>
          val backoff = (Math.pow(2, iteration) * initIntervalMs).toLong
          println(s"Failed to run action $actionName: $e, attempt: $iteration, will try again after: $backoff ms")
          //          logWarning(s"Failed to run action $actionName: $e, attempt: $iteration, will try again after: $backoff ms")
          Thread.sleep(backoff)
          retry(iteration + 1)
        case e: Throwable =>
          throw e
      } finally {
        cleanUp
      }
    }

    retry(0)
  }
  withRetry(500, 3, "test"){println("开始工作")}{println("清理完毕")}
}
