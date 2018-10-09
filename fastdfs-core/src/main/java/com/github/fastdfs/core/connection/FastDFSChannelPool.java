package com.github.fastdfs.core.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.pool.ChannelHealthChecker;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;

/**
 * FastDFS 通道连接池，存储通道连接，主要用于通道的访问、释放。
 *
 * @author Rambo Yang
 */
public class FastDFSChannelPool extends FixedChannelPool {

    public FastDFSChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections) {
        super(bootstrap, handler, maxConnections);
    }

    public FastDFSChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections, int maxPendingAcquires) {
        super(bootstrap, handler, maxConnections, maxPendingAcquires);
    }

    public FastDFSChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires) {
        super(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires);
    }

    public FastDFSChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires, boolean releaseHealthCheck) {
        super(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires, releaseHealthCheck);
    }

    public FastDFSChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires, boolean releaseHealthCheck, boolean lastRecentUsed) {
        super(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires, releaseHealthCheck, lastRecentUsed);
    }
}
