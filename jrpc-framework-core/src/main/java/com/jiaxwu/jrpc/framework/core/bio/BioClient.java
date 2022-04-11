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
public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1009);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello world!".getBytes());
        outputStream.flush();
        InputStream inputStream = socket.getInputStream();
        byte[] response = new byte[1024];
        int len = inputStream.read(response);
        if (len != -1) {
            System.out.println("[Client Response is] " + new String(response, 0, len));
        }
    }
}
