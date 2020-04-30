package cn.blackbulb.casqas;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author wangcan
 */
public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(8);
        CountDownLatch latch = new CountDownLatch(8);
        for (int i = 0; i <8 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"开始准备");
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                    System.out.println(Thread.currentThread().getName()+"准备好了");
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"开始跑步！");
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"跑完了！");
                latch.countDown();
            },"运动员"+i).start();
        }
        latch.await();
        System.out.println("所有运动员运动结束，比赛结束！！！");
    }
}
