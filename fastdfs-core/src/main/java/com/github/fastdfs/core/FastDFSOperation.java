package com.github.fastdfs.core;

import com.github.fastdfs.core.exchange.Replier;
import com.github.fastdfs.core.exchange.Requestor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.concurrent.CompletableFuture;

/**
 * @author Rambo Yang
 */
public class FastDFSOperation<T> {

    private final Channel channel;

    private final Requestor requestor;

    private final Replier<T> replier;

    private final CompletableFuture<T> promise;

    public FastDFSOperation(Channel channel, Requestor requestor, Replier<T> replier, CompletableFuture<T> promise) {
        this.channel = channel;
        this.requestor = requestor;
        this.replier = replier;
        this.promise = promise;
    }

    /**
     *
     */
    protected void execute() {
        //FIXME，并发问题
        channel.pipeline()
                .get(FastDFSHandler.class)
                .operation(this);

        try {
            requestor.request(channel);
        } catch (Exception e) {
            caught(e);
        }
    }

    /**
     *
     * @return
     */
    protected boolean isDone() {
        return promise.isDone();
    }

    /**
     *
     * @param in
     */
    protected void await(ByteBuf in) {
        try {
            replier.reply(in, promise);
        } catch (Exception e) {
            caught(e);
        }
    }

    /**
     *
     * @param cause
     */
    protected void caught(Throwable cause) {
        promise.completeExceptionally(cause);
    }

    @Override
    public String toString() {
        return "FastDFSOperation{" + "channel=" + channel + ", requestor=" + requestor + ", replier=" + replier + ", promise=" + promise + '}';
    }
}
