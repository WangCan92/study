package cn.blackbulb.strategy;

/**
 * @author wangcan
 */
public class DuckTest {
    public static void main(String[] args) {
        Duck mallardDuck = new MallardDuck();
        mallardDuck.performFly();
        System.out.println("==============================");
        Duck modelDuck = new ModelDuck();
        modelDuck.performFly();
    }
}
