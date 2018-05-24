package com.github.rambo.fastdfs.core;

import com.github.rambo.fastdfs.core.codec.Decoder;
import com.github.rambo.fastdfs.core.codec.Encoder;
import com.github.rambo.fastdfs.core.connection.FastDFSChannelPool;
import com.github.rambo.fastdfs.core.connection.FastDFSChannelPoolGroup;
import com.github.rambo.fastdfs.core.connection.FastDFSPoolConfig;
import com.github.rambo.fastdfs.core.connection.ServerNode;
import com.github.rambo.fastdfs.core.exchange.Replier;
import com.github.rambo.fastdfs.core.exchange.ReplierDecoder;
import com.github.rambo.fastdfs.core.exchange.Requestor;
import com.github.rambo.fastdfs.core.exchange.RequestorEncoder;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author Rambo Yang
 */
public class FastDFSExecutor implements Closeable {

    private FastDFSPoolConfig poolConfig;

    private final EventLoopGroup loopGroup;

    private FastDFSChannelPoolGroup poolGroup;

    public FastDFSExecutor() {
        this(new FastDFSPoolConfig());
    }

    public FastDFSExecutor(FastDFSPoolConfig poolConfig) {
        this.poolConfig = poolConfig;

        loopGroup = new NioEventLoopGroup(poolConfig.getMaxThreads());

        poolGroup = new FastDFSChannelPoolGroup(loopGroup, poolConfig.getConnectTime(), poolConfig.getReadTimeout(),
                poolConfig.getIdleTimeout(), poolConfig.getMaxConnPerHost());
    }

    public FastDFSPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(FastDFSPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    /**
     * 访问 Fastdfs 服务器
     *
     * @param node 地址
     * @param encoder 编码器
     * @param decoder 解码器
     * @return
     */
    public <T> CompletableFuture<T> execute(ServerNode node, Encoder encoder, Decoder<T> decoder) {
        return execute(node, new RequestorEncoder(encoder), new ReplierDecoder<>(decoder));
    }

    /**
     * @param node
     * @param encoder
     * @param replier
     * @param <T>
     * @return
     */
    public <T> CompletableFuture<T> execute(ServerNode node, Encoder encoder, Replier<T> replier) {
        return execute(node, new RequestorEncoder(encoder), replier);
    }

    /**
     * @param node
     * @param requestor
     * @param replier
     * @param <T>
     * @return
     */
    public <T> CompletableFuture<T> execute(ServerNode node, Requestor requestor, Replier<T> replier) {
        CompletableFuture<T> promise = new CompletableFuture<>();
        doExecute(node, requestor, replier, promise);
        return promise;
    }

    private <T> void doExecute(ServerNode node, Requestor requestor, Replier<T> replier, CompletableFuture<T> promise) {
        FastDFSChannelPool pool = poolGroup.get(node);
        pool.acquire().addListener(new FastDFSChannelListener<>(pool, requestor, replier, promise));
    }

    @PreDestroy
    public void close() throws IOException {
        if (null != poolGroup) {
            try {
                poolGroup.close();
            } catch (Exception e) {
                // ignore
            }
        }
        if (null != loopGroup) {
            loopGroup.shutdownGracefully();
        }
    }
}
