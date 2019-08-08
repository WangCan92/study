package cn.blackbulb.proxy;

import cn.blackbulb.service.IService;

/**
 * 静态聚合代理
 * @author wangcan
 */
public class IServiceImplStaticUnitProxy implements IService {
    private IService serive;

    public IServiceImplStaticUnitProxy(IService serive) {
        this.serive = serive;
    }

    @Override
    public void query() {
        System.out.println("静态聚合代理增强");
        serive.query();
    }
}
