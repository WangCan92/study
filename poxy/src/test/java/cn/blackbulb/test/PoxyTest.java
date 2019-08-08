package cn.blackbulb.test;

import cn.blackbulb.proxy.IServiceImpStaticIExtendProxy;
import cn.blackbulb.proxy.IServiceImplStaticUnitProxy;
import cn.blackbulb.proxy.MyInvocationHandler;
import cn.blackbulb.proxy.ProxyUtil;
import cn.blackbulb.service.IService;
import cn.blackbulb.service.impl.IServiceImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author wangcan
 */
public class PoxyTest {

    @Test
    public void staticExtendPoxyTest(){
        IService service = new IServiceImpStaticIExtendProxy();
        service.query();
    }

    @Test
    public void staticUnitPoxyTest(){
        IService service = new IServiceImplStaticUnitProxy(new IServiceImpl());
        service.query();
    }

    @Test
    public void manualProxyTest(){
        IService service = (IService) ProxyUtil.getInstance(new IServiceImpl());
        service.query();
    }

    //JDK 动态代理
    @Test
    public void jdkProxyTest(){
        IService o = (IService) Proxy.newProxyInstance(IServiceImpl.class.getClassLoader(), IServiceImpl.class.getInterfaces(), new MyInvocationHandler(new IServiceImpl()));
        o.query();
    }
}
