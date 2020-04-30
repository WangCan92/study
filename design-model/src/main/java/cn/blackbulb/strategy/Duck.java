package cn.blackbulb.strategy;

/**
 * @author wangcan
 */
public abstract class Duck {
    public FlyBehavior flyBehavior;
    public void swim(){
        System.out.println("会游泳。。。。");
    }
    public void performFly(){
        flyBehavior.fly();
    }
    abstract void display();
}
