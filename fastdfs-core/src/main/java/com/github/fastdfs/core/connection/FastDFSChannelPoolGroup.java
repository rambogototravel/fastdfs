package com.github.fastdfs.core.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FastDFS 通道连接池组，存储通道连接池。
 *
 * @author Rambo Yang
 */
public class FastDFSChannelPoolGroup extends AbstractChannelPoolMap<ServerNode, FastDFSChannelPool> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private EventLoopGroup eventLoopGroup;

    private final long connectTimeout;

    private final long readTimeout;

    private final long idleTimeout;

    private final int maxConnPerHost;

    public FastDFSChannelPoolGroup(EventLoopGroup eventLoopGroup, long connectTimeout, long readTimeout, long idleTimeout, int maxConnPerHost) {
        this.eventLoopGroup = eventLoopGroup;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.idleTimeout = idleTimeout;
        this.maxConnPerHost = maxConnPerHost;
    }

    @Override
    protected FastDFSChannelPool newPool(ServerNode key) {
        if (logger.isDebugEnabled()) {
            logger.debug("channel pool created: {}", key.toString());
        }

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(eventLoopGroup)
                .remoteAddress(key.getHost(), key.getPort())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) connectTimeout)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);

        return new FastDFSChannelPool(bootstrap, new FastDFSChannelPoolHandler(readTimeout, idleTimeout), maxConnPerHost);
    }
}
