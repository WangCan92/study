package cn.blackbulb.decorator;

/**
 * @author wangcan
 */
public class DecoratorTest {
    public static void main(String[] args) {
        Beverage beverage = new Expresso();
        //加两份的摩卡
        Beverage mo1 = new Mocha(beverage);
        Beverage mo2 = new Mocha(mo1);
        //加一份奶盖
        Beverage mo3 = new Whip(mo2);
        System.out.println(mo3.getDescription());
        System.out.println("花费："+mo3.cost());
    }
}
