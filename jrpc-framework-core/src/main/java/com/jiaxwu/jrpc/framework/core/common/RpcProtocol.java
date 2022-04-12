package com.jiaxwu.jrpc.framework.core.common;

import com.jiaxwu.jrpc.framework.core.common.constants.RpcConstants;

import java.io.Serializable;
import java.util.Arrays;

/**
 * RPC调用的协议
 *
 * @author jiaxwu
 * @create 2022/4/12 20:45
 */
public class RpcProtocol implements Serializable {

    private static final long serialVersionUID = 6537475055361732144L;

    private short magicNumber = RpcConstants.MAGIC_NUMBER;

    private int contentLength;

    private byte[] content;

    public RpcProtocol(byte[] content) {
        this.contentLength = content.length;
        this.content = content;
    }

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RpcProtocol{" +
                "magicNumber=" + magicNumber +
                ", contentLength=" + contentLength +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
