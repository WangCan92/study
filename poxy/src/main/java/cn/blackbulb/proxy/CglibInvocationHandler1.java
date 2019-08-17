package cn.blackbulb.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wangcan
 */
public class CglibInvocationHandler1 implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib1 代理逻辑在此！！！！");
        return methodProxy.invokeSuper(o,objects);
    }
}
