package cn.blackbulb.strategy;

/**
 * @author wangcan
 */
public class MallardDuck extends Duck {

    public MallardDuck() {
        flyBehavior = new FlyWithWings();
    }

    @Override
    void display() {
        System.out.println("我是绿头鸭。。。。。。");
    }
}
