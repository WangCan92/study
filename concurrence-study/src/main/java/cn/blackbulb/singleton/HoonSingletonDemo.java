package cn.blackbulb.singleton;

import java.util.concurrent.TimeUnit;

/**
 * @author wangcan
 * 懒汉模式
 * 特点：
 *  1.懒加载
 *  2.线程不安全
 */
public class HoonSingletonDemo {
    private static HoonSingletonDemo instance;

    private HoonSingletonDemo() {
    }

    public static HoonSingletonDemo getInstance() {
        if (instance == null) {
            instance = new HoonSingletonDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(HoonSingletonDemo.getInstance());
            }).start();
        }
    }
}
