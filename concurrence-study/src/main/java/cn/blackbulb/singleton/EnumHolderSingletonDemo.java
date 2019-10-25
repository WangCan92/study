package cn.blackbulb.singleton;

/**
 * @author wangcan
 */
public class EnumHolderSingletonDemo {
    private EnumHolderSingletonDemo() {
    }

    public enum SingletonHolder {
        INSTAHNCE;
        public EnumHolderSingletonDemo instance;

        SingletonHolder() {
            this.instance = new EnumHolderSingletonDemo();
        }

        public EnumHolderSingletonDemo getInstance() {
            return instance;
        }
    }

    public static EnumHolderSingletonDemo getInstance() {
        return SingletonHolder.INSTAHNCE.instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(EnumHolderSingletonDemo.getInstance());
            }).start();
        }
    }
}
