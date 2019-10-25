package cn.blackbulb.casqas;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wangcan
 */
public class CountDownLatchTest {
    private static List<String> dd = Arrays.asList("海南航空", "东方航空", "南方航空");
    private static List<String> result = new ArrayList<>();



    public static void main(String[] args)throws InterruptedException {
         CountDownLatch latch = new CountDownLatch(dd.size());
        for (int i = 0; i < dd.size(); i++) {
            String d = dd.get(i);
            new Thread(()->{

                System.out.printf("开始查询是%s\n",d);
                int val = new Random().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(val);
                    result.add(d+"----------"+val);
                    System.out.printf("%s查询结束\n",d);
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            },d).start();
        }
        latch.await();

        System.out.println("================查询结果======================");
        result.forEach(r->{
            System.out.println(r);
        });
    }
}
