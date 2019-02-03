#!/usr/bin/env groovy
class A {
    static class B{}
}
new A.B()

public class Y {
    public class X {}
    public X foo() {
        return new X();
    }
    public static X createX(Y y) {
        return new X(y)
    }
}

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

CountDownLatch called = new CountDownLatch(1)

Timer timer = new Timer(true)
timer.schedule(new TimerTask() {
    void run() {
        called.countDown()
    }
}, 0)

assert called.await(2, TimeUnit.SECONDS)
print "await finish"