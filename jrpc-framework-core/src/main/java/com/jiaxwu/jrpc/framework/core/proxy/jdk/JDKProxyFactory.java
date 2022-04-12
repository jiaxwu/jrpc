package com.jiaxwu.jrpc.framework.core.proxy.jdk;

import com.jiaxwu.jrpc.framework.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * @author jiaxwu
 * @create 2022/4/13 01:12
 */
public class JDKProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Class<T> clazz) throws Throwable {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new JDKClientInvocationHandler<>(clazz));
    }
}
