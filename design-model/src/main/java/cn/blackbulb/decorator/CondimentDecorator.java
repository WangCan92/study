package cn.blackbulb.decorator;

/**
 * @author wangcan
 * 所有调料的父类 都必须实现getDescription方法
 */
public abstract class CondimentDecorator extends Beverage {
    public abstract String getDescription();
}
