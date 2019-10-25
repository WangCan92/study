package cn.blackbulb.singleton;

/**
 * @author wangcan
 *  指令重排还可能引发问题加上volatile防止指令重排
 */
public class DCLSingletonDemo {
    private volatile static DCLSingletonDemo instance;

    private DCLSingletonDemo() {
    }

    public static DCLSingletonDemo getInstance() {
        if (instance == null) {
            synchronized (DCLSingletonDemo.class) {
                if (instance == null) {
                    instance = new DCLSingletonDemo();
                }
            }
        }
        return instance;
    }
}
