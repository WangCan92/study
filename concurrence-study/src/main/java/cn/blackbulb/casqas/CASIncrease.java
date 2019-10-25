package cn.blackbulb.casqas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author wangcan
 */
public class CASIncrease {
    private static volatile int m = 0;
    //有ABA问题
    private static AtomicInteger n = new AtomicInteger(0);

    private static AtomicStampedReference d = new AtomicStampedReference(0,1);

    public static void increase1() {
        m++;
    }

    public static void main(String[] args) {
        for (int i = 0; i <20 ; i++) {
            new Thread(()->{
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                CASIncrease.increase1();
//                System.out.println(m);
                System.out.println("atomic："+n.incrementAndGet());
            }).start();
        }

        System.out.println("========================================");
        new Thread(()->{
            for (int i = 0; i <20 ; i++) {
                System.out.println(d.compareAndSet(i,i+1,i+1,i+2));
                System.out.println(d.getReference()+":"+d.getStamp());
            }
        }).start();


    }
}
