package com.github.rambo.fastdfs.core.exchange;

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
}
