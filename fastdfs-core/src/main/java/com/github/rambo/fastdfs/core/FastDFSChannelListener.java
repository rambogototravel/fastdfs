package com.github.rambo.fastdfs.core;

import com.github.rambo.fastdfs.core.connection.FastDFSChannelPool;
import com.github.rambo.fastdfs.core.exchange.Replier;
import com.github.rambo.fastdfs.core.exchange.Requestor;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * @author Rambo Yang
 */
public class FastDFSChannelListener<T> implements FutureListener<Channel> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    final FastDFSChannelPool pool;

    final Requestor requestor;

    final Replier<T> replier;

    final CompletableFuture<T> promise;

    public FastDFSChannelListener(FastDFSChannelPool pool, Requestor requestor, Replier<T> replier, CompletableFuture<T> promise) {
        this.pool = pool;
        this.requestor = requestor;
        this.replier = replier;
        this.promise = promise;
    }

    @Override
    public void operationComplete(Future<Channel> future) throws Exception {
        if (future.isCancelled()) {
            promise.cancel(true);
            return;
        }

        if (!future.isSuccess()) {
            promise.completeExceptionally(future.cause());
            return;
        }

        Channel channel = future.getNow();
        promise.whenComplete((result, error) -> pool.release(channel));

        try {
            FastDFSOperation<T> fastDFSOperation = new FastDFSOperation<>(channel, requestor, replier, promise);
            if (logger.isDebugEnabled()) {
                logger.debug("execute {}", fastDFSOperation);
            }

            fastDFSOperation.execute();
        } catch (Exception e) {
            promise.completeExceptionally(e);
        }
    }
}
