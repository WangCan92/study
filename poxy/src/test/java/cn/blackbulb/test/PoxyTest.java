package cn.blackbulb.test;

import cn.blackbulb.proxy.*;
import cn.blackbulb.service.IService;
import cn.blackbulb.service.impl.IServiceImpl;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author wangcan
 */
public class PoxyTest {

    @Test
    public void staticExtendPoxyTest() {
        IService service = new IServiceImpStaticIExtendProxy();
        service.query();
    }

    @Test
    public void staticUnitPoxyTest() {
        IService service = new IServiceImplStaticUnitProxy(new IServiceImpl());
        service.query();
    }

    @Test
    public void manualProxyTest() {
        IService service = (IService) ProxyUtil.getInstance(new IServiceImpl());
        service.query();
    }

    //JDK 动态代理
    @Test
    public void jdkProxyTest() {
        IService o = (IService) Proxy.newProxyInstance(IServiceImpl.class.getClassLoader(),
                IServiceImpl.class.getInterfaces(),
                new MyInvocationHandler(new IServiceImpl()));
        o.query();
    }

    //cglib 代理
    @Test
    public void cglibProxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(IServiceImpl.class);
        enhancer.setCallbacks(new Callback[]{new CglibInvocationHandler(),new CglibInvocationHandler1()});
        IService s = (IService) enhancer.create();
        s.query();
    }
}
