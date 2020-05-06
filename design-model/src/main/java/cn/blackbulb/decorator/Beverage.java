package cn.blackbulb.decorator;

/**
 * @author wangcan
 * 饮料类，装饰者和被装饰者的父类
 */
public abstract class Beverage {
    String description = "Unkonwn Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
