package cn.blackbulb.strategy;

/**
 * @author wangcan
 */
public class ModelDuck extends Duck {
    public ModelDuck() {
        flyBehavior = new FlyNoWay();
    }

    @Override
    void display() {
        System.out.println("我是模型鸭子。。。。。。。");
    }
}
