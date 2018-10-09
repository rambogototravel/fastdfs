package com.github.fastdfs.core.codec;

import io.netty.buffer.ByteBufAllocator;

import java.util.List;

/**
 * 请求编码器
 *
 * @author Rambo Yang
 */
public interface Encoder {

    /**
     *
     * @param alloc
     * @return
     */
    List<Object> encode(ByteBufAllocator alloc);
}
