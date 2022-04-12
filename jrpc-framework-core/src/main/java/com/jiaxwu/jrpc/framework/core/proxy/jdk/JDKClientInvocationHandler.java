package com.jiaxwu.jrpc.framework.core.proxy.jdk;

import com.jiaxwu.jrpc.framework.core.common.CommonClientCache;
import com.jiaxwu.jrpc.framework.core.common.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author jiaxwu
 * @create 2022/4/13 01:06
 */
public class JDKClientInvocationHandler<T> implements InvocationHandler {

    private final static Object OBJECT = new Object();

    private Class<T> clazz;

    public JDKClientInvocationHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    // 把调用放到发送队列里面，然后等待请求结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setArgs(args);
        rpcInvocation.setTargetMethod(method.getName());
        rpcInvocation.setTargetServiceName(clazz.getName());
        rpcInvocation.setUuid(UUID.randomUUID().toString());
        CommonClientCache.RESP_MAP.put(rpcInvocation.getUuid(), OBJECT);
        CommonClientCache.SEND_QUEUE.add(rpcInvocation);
        long beginTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - beginTime < 3 * 1000) {
            Object response = CommonClientCache.RESP_MAP.get(rpcInvocation.getUuid());
            if (response instanceof RpcInvocation) {
                return ((RpcInvocation) response).getResponse();
            }
        }
        throw new TimeoutException("client wait server's response timeout!");
    }
}
