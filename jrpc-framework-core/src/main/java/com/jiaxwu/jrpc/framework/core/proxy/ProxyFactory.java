package com.jiaxwu.jrpc.framework.core.proxy;

/**
 * @author jiaxwu
 * @create 2022/4/13 01:04
 */
public interface ProxyFactory {
    <T> T getProxy(Class<T> clazz) throws Throwable;
}
