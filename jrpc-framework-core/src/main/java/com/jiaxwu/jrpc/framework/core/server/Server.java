package com.jiaxwu.jrpc.framework.core.server;

import com.jiaxwu.jrpc.framework.core.common.RpcDecoder;
import com.jiaxwu.jrpc.framework.core.common.RpcEncoder;
import com.jiaxwu.jrpc.framework.core.common.cache.CommonServerCache;
import com.jiaxwu.jrpc.framework.core.common.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author jiaxwu
 * @create 2022/4/12 23:44
 */
public class Server {
    private static EventLoopGroup bossGroup;
    private static EventLoopGroup workerGroup;
    private ServerConfig serverConfig;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void startApplication() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true); // 禁用nagle算法
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024); // 半连接队列大小
        bootstrap.option(ChannelOption.SO_SNDBUF, 16 * 1024); // 发送缓冲区大小
        bootstrap.option(ChannelOption.SO_RCVBUF, 16 * 1024); // 接收缓冲区大小
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // 启动Keepalive
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(new RpcEncoder());
                ch.pipeline().addLast(new RpcDecoder());
                ch.pipeline().addLast(new ServerHandler());
            }
        });
        bootstrap.bind(serverConfig.getPort()).sync();
    }

    public void registerService(Object service) {
        if (service.getClass().getInterfaces().length == 0) {
            throw new RuntimeException("service must had interface");
        }
        Class<?>[] classes = service.getClass().getInterfaces();
        if (classes.length > 1) {
            throw new RuntimeException("service must only had one interface!");
        }
        Class<?> clazz = classes[0];
        CommonServerCache.PROVIDER_CLASS_MAP.put(clazz.getName(), service);
    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(9999);
        server.setServerConfig(serverConfig);
        server.registerService(new DataServiceImpl());
        server.startApplication();
    }

}
