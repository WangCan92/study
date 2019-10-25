package cn.blackbulb.singleton;

/**
 * @author wangcan
 * 饿汉单例模式
 * 特点：
 *  1.线程安全
 *  2.非懒加载
 */
public class HungrySingletonDemo {
    private static HungrySingletonDemo instance = new HungrySingletonDemo();

    public static HungrySingletonDemo getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i <20 ; i++) {
            new Thread(()->{
                System.out.println(HungrySingletonDemo.getInstance());
            }).start();
        }
    }
}
