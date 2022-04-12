package com.jiaxwu.jrpc.framework.core.common;

import com.jiaxwu.jrpc.framework.core.common.constants.RpcConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author jiaxwu
 * @create 2022/4/12 23:06
 */
public class RpcDecoder extends ByteToMessageDecoder {

    // 魔数+内容长度
    public final int BASE_LENGTH = 2 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if (byteBuf.readableBytes() >= BASE_LENGTH) {
            // 防止收到过大的包
            if (byteBuf.readableBytes() > 1000) {
                byteBuf.skipBytes(byteBuf.readableBytes());
            }
            int beginReader;
            while (true) {
                beginReader = byteBuf.readerIndex();
                byteBuf.markReaderIndex();
                if (byteBuf.readShort() == RpcConstants.MAGIC_NUMBER) {
                    break;
                } else {
                    ctx.close();
                    return;
                }
            }
            int length = byteBuf.readInt();
            if (byteBuf.readableBytes() < length) {
                byteBuf.readerIndex(beginReader);
                return;
            }
            byte[] content = new byte[length];
            byteBuf.readBytes(content);
            RpcProtocol rpcProtocol = new RpcProtocol(content);
            out.add(rpcProtocol);
        }
    }
}
