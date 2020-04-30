package cn.blackbulb.strategy;

/**
 * @author wangcan
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("用翅膀飞行");
    }
}
