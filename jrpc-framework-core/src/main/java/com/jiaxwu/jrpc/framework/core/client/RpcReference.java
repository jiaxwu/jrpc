package com.jiaxwu.jrpc.framework.core.client;

import com.jiaxwu.jrpc.framework.core.proxy.ProxyFactory;

/**
 * @author jiaxwu
 * @create 2022/4/13 01:19
 */
public class RpcReference {
    private ProxyFactory proxyFactory;

    public RpcReference(ProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    public <T> T get(Class<T> clazz) throws Throwable {
        return proxyFactory.getProxy(clazz);
    }
}
