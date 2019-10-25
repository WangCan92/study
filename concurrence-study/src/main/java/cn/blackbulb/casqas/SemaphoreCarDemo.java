package cn.blackbulb.casqas;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author wangcan
 */
public class SemaphoreCarDemo {
    public static void main(String[] args) {
        Semaphore sp = new Semaphore(2);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"准备进入停车场");
                    sp.tryAcquire();
                    int val = new Random().nextInt(10);
                    TimeUnit.SECONDS.sleep(val);
                    System.out.println(Thread.currentThread().getName()+"停留"+val+"秒");
                    System.out.println(Thread.currentThread().getName()+"离开停车场");
                    sp.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "car[" + i + "]").start();
        }
    }
}
