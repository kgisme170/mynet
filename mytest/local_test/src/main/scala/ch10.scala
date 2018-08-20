object ch10 extends App {

    trait _Logger {
        def log(m: String)
    }

    class _ConsoleLogger extends _Logger {
        override def log(m: String) {
            println(m)
        }
    }

    trait Logger {
        def log(msg: String) {
            println(msg)
        }
    }

    trait ConsoleLogger extends Logger {
        override def log(msg: String) {
            println(msg)
        }
    }

    class Account

    class SavingsAccount(val balance: Int = 3) extends Account with Logger {
        def withdraw(amount: Double) {
            if (amount > balance) log("Insufficient funds")
        }
    }

    val acct = new SavingsAccount with ConsoleLogger
    acct.log("ab")

    trait TimestampLogger extends Logger {
        override def log(msg: String) {
            super.log(new java.util.Date() + " " + msg)
        }
    }

    trait ShortLogger extends Logger {
        val maxLength = 15

        override def log(msg: String) {
            super.log(
                if (msg.length <= maxLength) msg else msg.substring(0, maxLength - 3) + "..."
            )
        }
    }

    val acct1 = new SavingsAccount with TimestampLogger with ShortLogger
    val acct2 = new SavingsAccount with ShortLogger with TimestampLogger
    acct1.log("ab")
    acct2.log("ab")

    trait l1 {
        def log(msg: String)
    }

    trait l2 extends l1 {
        abstract override def log(msg: String) {
            super.log(new java.util.Date() + " " + msg)
        }
    }

    trait l3 {
        val maxLength = 15

        def log(msg: String)

        def info(msg: String) {
            log("INFO: " + msg)
        }
    }

    class s2 extends Account with l3 {
        val balance = 10

        override def log(msg: String) {
            println(msg)
        }
    }

    trait l5 extends Logger {
        val maxLength: Int

        override def log(msg: String) {
            super.log(
                if (msg.length <= maxLength) msg else msg.substring(0, maxLength - 3) + "..."
            )
        }
    }

    class s3 extends Account with ConsoleLogger with l5 {
        val maxLength = 20
    }

    import java.io._

    trait f1 extends Logger {
        val filename: String
        val out = new PrintStream(filename)

        override def log(msg: String) {
            out.println(msg);
            out.flush()
        }
    }

    val a3 = new {
        val filename = "myapp.log"
    } with SavingsAccount with f1

    trait f2 extends Logger {
        val filename: String
        lazy val out = new PrintStream(filename)

        override def log(msg: String) {
            out.println(msg)
        }
    }

    val a4 = new SavingsAccount with f2 {
        val filename = "myapp.log"
    }

    trait LoggerException extends Exception with Logger {
        def log() {
            log(getMessage())
        }
    }

    class UnhappyException extends IOException with LoggerException

    trait selfType1 extends Logger {
        this: Exception =>
        def log() {
            log(getMessage())
        }
    }

    trait selfType2 extends Logger {
        this: {def getMessage(): String} =>
        def log() {
            log(getMessage())
        }
    }

}