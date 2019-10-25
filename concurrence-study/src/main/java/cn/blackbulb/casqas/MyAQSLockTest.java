package cn.blackbulb.casqas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author wangcan
 */
public class MyAQSLockTest {
    Lock lock = new MyAQSLock();
    private int m = 0;

    public int next() {
        lock.lock();
        try {
            return ++m;
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        MyAQSLockTest test = new MyAQSLockTest();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(test.next());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
