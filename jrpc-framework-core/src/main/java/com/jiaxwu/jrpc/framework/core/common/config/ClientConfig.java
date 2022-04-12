package com.jiaxwu.jrpc.framework.core.common.config;

/**
 * @author jiaxwu
 * @create 2022/4/13 00:40
 */
public class ClientConfig {
    private Integer port;
    private String serverAddr;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }
}
