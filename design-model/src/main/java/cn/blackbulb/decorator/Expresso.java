package cn.blackbulb.decorator;

/**
 * @author wangcan
 */
public class Expresso extends Beverage {
    public Expresso() {
        this.description="expresso.....";
    }

    @Override
    public double cost() {
        return .3;
    }
}
