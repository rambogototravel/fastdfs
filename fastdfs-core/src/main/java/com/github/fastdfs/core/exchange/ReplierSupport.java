package com.github.fastdfs.core.exchange;

import com.github.fastdfs.FastDFSException;
import com.github.fastdfs.core.constant.FastDFSConsts;
import io.netty.buffer.ByteBuf;

import java.util.concurrent.CompletableFuture;

/**
 * 接收处理基类
 *
 * @author Rambo Yang
 */
public abstract class ReplierSupport<T> implements Replier<T> {

    protected boolean atHead = true;

    protected long length;

    @Override
    public void reply(ByteBuf in, CompletableFuture<T> promise) {
        if (atHead) {
            readHead(in);
        }
        readContent(in, promise);
    }

    /**
     * 期待的长度值， 小于0不验证
     *
     * @return
     */
    protected long expectLength() {
        return -1;
    }

    /**
     * 读取内容
     *
     * @param in
     * @param promise
     */
    protected abstract void readContent(ByteBuf in, CompletableFuture<T> promise);


    private void readHead(ByteBuf in) {
        if (in.readableBytes() < FastDFSConsts.FDFS_HEAD_LEN) {
            return;
        }
        length = in.readLong();
        byte cmd = in.readByte();
        byte errno = in.readByte();
        if (errno != 0) {
            throw new FastDFSException("FastDFS responsed with an error, errno is " + errno);
        }
        if (cmd != FastDFSConsts.Commands.RESP) {
            throw new FastDFSException("Expect response command code error : " + cmd);
        }
        long expectLength = expectLength();
        if (expectLength >= 0 && length != expectLength) {
            throw new FastDFSException("Expect response length : " + expectLength + " , but reply length : " + length);
        }
        atHead = false;
    }
}
