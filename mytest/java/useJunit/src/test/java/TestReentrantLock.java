import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        public Thread doTransfer(int from, int to, double amount, int sleepMs) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    bankLock.lock();
                    try {
                        while(accounts[from] < amount) {
                            sufficientFunds.await();
                        }
                        System.out.println(Thread.currentThread());
                        Thread.sleep(sleepMs);
                        accounts[from] -= amount;
                        System.out.println("睡眠了" + sleepMs + ", 剩余" + accounts[from]);
                        Thread.sleep(sleepMs);
                        accounts[to] += amount;
                        sufficientFunds.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        bankLock.unlock();
                    }                }
            };
            Thread t = new Thread(runnable);
            t.start();
            return t;
        }

        public double getTotal() {
            bankLock.lock();
            try {
                double sum = 0;
                for (double a: accounts) {
                    System.out.println(a);
                    sum += a;
                }
                return sum;
            } finally {
                bankLock.unlock();
            }
        }
    }

    @Test
    public void TestAccountTransfer() throws InterruptedException {
        Bank bank = new Bank(3, 200);
        Thread t1 = bank.doTransfer(0,1, 100, 2000);
        Thread t2 = bank.doTransfer(1,2, 30, 400);
        Thread t3 = bank.doTransfer(2,0, 5, 500);
        t1.join();
        t2.join();
        t3.join();
        System.out.println(bank.getTotal());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldTestExceptionMessage() throws IndexOutOfBoundsException {
        List<Object> list = new ArrayList<Object>();
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Index: 0, Size: 0");
        list.get(0); // execution will never get past this line
    }
}