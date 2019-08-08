package cn.blackbulb.proxy;

import cn.blackbulb.service.impl.IServiceImpl;

/**
 * 静态继承代理
 * @author wangcan
 */
public class IServiceImpStaticIExtendProxy extends IServiceImpl {
    @Override
    public void query() {
        System.out.println("继承静态代理");
        super.query();
    }
}
