package com.jiaxwu.jrpc.framework.core.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jiaxwu
 * @create 2022/4/11 22:08
 */
public class NioServer extends Thread {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private SelectionKey selectionKey;

    public void initServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(20000));
        selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        while (true) {
            try {
                int keys = selector.select();
                if (keys > 0) {
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> it = keySet.iterator();
                    while (it.hasNext()) {
                        SelectionKey selectionKey = it.next();
                        it.remove();
                        if (selectionKey.isAcceptable()) {
                            accept(selectionKey);
                        }
                        if (selectionKey.isReadable()) {
                            read(selectionKey);
                        }
                        if (selectionKey.isWritable()) {
                            write(selectionKey);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    serverSocketChannel.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public void accept(SelectionKey selectionKey) {
        try {
            // 服务器有新的请求了，所以是ServerSocketChannel
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("[Server] accept a new conn");
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(SelectionKey selectionKey) {
        try {
            // 某个连接有新的可读数据，所以是SocketChannel
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
            int len = socketChannel.read(byteBuffer);
            if (len > 0) {
                byteBuffer.flip();
                byte[] byteArray = new byte[byteBuffer.limit()];
                byteBuffer.get(byteArray);
                System.out.println("[Server] receive from client:" + new String(byteArray, 0, len));
                selectionKey.interestOps(SelectionKey.OP_READ);
            }
        } catch (IOException e) {
            try {
                serverSocketChannel.close();
                selectionKey.cancel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void write(SelectionKey selectionKey) {
        try {
            // 某个连接有新的可写数据，所以是SocketChannel
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.wrap("I am server".getBytes());
            int len = socketChannel.write(byteBuffer);
            if (len > 0) {
                System.out.println("[Server] write to client");
                socketChannel.close();
            }
        } catch (IOException e) {
            try {
                serverSocketChannel.close();
                selectionKey.cancel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
        nioServer.initServer();
        nioServer.start();
    }
}