import org.junit.*;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    @Test
    public void TestLockWithException() {
        Lock bankLock = new ReentrantLock();
        try {
            bankLock.lock();
            Thread main = Thread.currentThread();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        System.out.println("子线程睡眠结束");
                        main.interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread t = new Thread(runnable);
            t.start();
            Thread.sleep(1000);
            System.out.println("主线程睡眠结束");
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally unlock");
            bankLock.unlock();
        }
    }

    class Bank {
        private Lock bankLock = new ReentrantLock();
        private Condition sufficientFunds = bankLock.newCondition();
        private final double[] accounts;

        public Bank(int n, double initialBalance) {
            accounts = new double[n];
            Arrays.fill(accounts, initialBalance);
        }

        public void transfer(int from, int to, double amount) throws InterruptedException {
            bankLock.lock();
            try {
                while(accounts[from] < amount) {
                    sufficientFunds.await();
                }
                System.out.println(Thread.currentThread());
                accounts[from] -= amount;
                accounts[to] += amount;
                sufficientFunds.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bankLock.unlock();
            }
        }

        public double getTotal() {
            bankLock.lock();
            try {
                double sum = 0;
                for (double a: accounts) {
                    sum += a;
                }
                return sum;
            } finally {
                bankLock.unlock();
            }
        }
    }

    @Test
    public void TestAccountTransfer() {
    }
}