package org.mybatis.util;

import org.mybatis.anno.SelectSql;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wangcan
 */
public class MapperFactoryBean implements FactoryBean, InvocationHandler {
    private Class clazz;

    public MapperFactoryBean(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getObject() throws Exception {
        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {clazz}, this);
        return o;
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //这里就是mapper实现的代理逻辑
        String sql = method.getDeclaredAnnotation(SelectSql.class).value();
        if (method.getReturnType() == String.class) {
            return sql;
        }
        return null;
    }
}
