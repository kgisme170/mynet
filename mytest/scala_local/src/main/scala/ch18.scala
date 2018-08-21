object ch18 extends App {

    class Document {
        def setTitle(title: String): this.type = {
            println(title);
            this
        }
    }

    class Book extends Document {
        def addChapter(chapter: String): this.type = {
            println(chapter);
            this
        }
    }

    new Book().setTitle("my").addChapter(" chapter")

    import scala.collection.mutable.ArrayBuffer

    class Network {

        class Member(val name: String) {}

        private val members = new ArrayBuffer[Member]

        def join(name: String) = {
            val m = new Member(name)
            members += m
            m
        }
    }

    val chatter = new Network
    val myface = new Network
    val fred = chatter.join("Fred")
    val barney = myface.join("Barney")
    type Index = scala.collection.immutable.HashMap[String, (Int, Int)]

    def appendLines(target: {def append(str: String): Any}, lines: Iterable[String]) {
        for (l <- lines) {
            target.append(l); target.append("\n")
        }
    }
}