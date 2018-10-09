package com.github.fastdfs.core.exchange;

import com.github.fastdfs.core.codec.Decoder;
import io.netty.buffer.ByteBuf;

import java.util.concurrent.CompletableFuture;

/**
 * 接收处理
 *
 * @author Rambo Yang
 */
public interface Replier<T> {

    /**
     * @param in
     * @param promise
     */
    void reply(ByteBuf in, CompletableFuture<T> promise);

    /**
     * 空响应解码器
     */
    enum NOPDecoder implements Decoder<Void> {

        INSTANCE;

        @Override
        public long expectLength() {
            return 0;
        }

        @Override
        public Void decode(ByteBuf buf) {
            return null;
        }

    }
}
