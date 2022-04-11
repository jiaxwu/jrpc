package com.jiaxwu.jrpc.framework.core.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author jiaxwu
 * @create 2022/4/11 22:08
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello world!".getBytes());
        outputStream.flush();
        socket.close();
    }
}
