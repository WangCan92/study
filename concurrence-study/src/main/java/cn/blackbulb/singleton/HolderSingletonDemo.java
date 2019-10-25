package cn.blackbulb.singleton;

/**
 * @author wangcan
 * 内部类实现延迟加载，无锁
 */
public class HolderSingletonDemo {
    private HolderSingletonDemo() {
    }

    public static class Holder {
        public static HolderSingletonDemo instance = new HolderSingletonDemo();
    }

    public static HolderSingletonDemo getInstance() {
        return Holder.instance;
    }
}
