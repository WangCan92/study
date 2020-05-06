package cn.blackbulb.decorator;

/**
 * @author wangcan
 * 装饰者摩卡，有一个饮料属性
 */
public class Mocha extends CondimentDecorator {
    Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",Mocha";
    }

    @Override
    public double cost() {
        return .22 + beverage.cost();
    }
}
