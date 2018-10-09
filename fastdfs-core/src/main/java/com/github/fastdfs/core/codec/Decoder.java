package com.github.fastdfs.core.codec;

import io.netty.buffer.ByteBuf;

/**
 * 响应解码器
 *
 * @author Rambo Yang
 */
public interface Decoder<T> {

    /**
     * 期待的长度值，小于 0 时不验证
     *
     * @return
     */
    default long expectLength() {
        return -1;
    }

    /**
     * 解码响应
     *
     * @param buf
     * @return
     */
    T decode(ByteBuf buf);
}
