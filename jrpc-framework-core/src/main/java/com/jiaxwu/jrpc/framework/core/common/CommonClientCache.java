package com.jiaxwu.jrpc.framework.core.common;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiaxwu
 * @create 2022/4/12 23:36
 */
public class CommonClientCache {
    public static BlockingQueue<RpcInvocation> SEND_QUEUE = new ArrayBlockingQueue<>(100);
    public static Map<String, Object> RESP_MAP = new ConcurrentHashMap<>();
}
