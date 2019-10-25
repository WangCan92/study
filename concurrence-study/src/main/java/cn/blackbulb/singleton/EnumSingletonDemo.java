package cn.blackbulb.singleton;

/**
 * @author wangcan
 * 线程安全 非懒加载
 */
public enum EnumSingletonDemo {
    INSTANCE;
    public static EnumSingletonDemo getInstance(){
        return INSTANCE;
    }
}
