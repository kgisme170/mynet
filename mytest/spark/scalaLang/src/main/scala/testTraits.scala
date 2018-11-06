object testTraits extends App {

  val s = new ServiceImpl with BeforeServiceAspect with AfterServiceAspect

  trait Service {
    def doAction(): Unit
  }

  trait BeforeServiceAspect extends Service {
    abstract override def doAction(): Unit = {
      println("before doAction ... ")
      super.doAction();
    }
  }

  trait AfterServiceAspect extends Service {
    abstract override def doAction(): Unit = {
      super.doAction();
      println("after doAction ... ");
    }
  }

  class ServiceImpl extends Service {
    override def doAction() {
      println("do job");
    }
  }
  s.doAction
}