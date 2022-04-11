package com.jiaxwu.jrpc.framework.core.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jiaxwu
 * @create 2022/4/11 22:08
 */
public class BioServer {
    private static final ExecutorService executors = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(1009));
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("获得新连接");
                executors.execute(() -> {
                    while (true) {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            byte[] result = new byte[1024];
                            int len = inputStream.read(result);
                            if (len != -1) {
                                System.out.println("[Server Receive] " + new String(result, 0, len));
                                OutputStream outputStream = socket.getOutputStream();
                                outputStream.write(("Server response data " + new String(result, 0, len)).getBytes());
                                outputStream.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
