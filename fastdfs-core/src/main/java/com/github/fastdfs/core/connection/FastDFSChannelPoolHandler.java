package com.github.fastdfs.core.connection;

import com.github.fastdfs.core.FastDFSHandler;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * FastDFS 通道连接池处理者，主要用于通道池创建、访问、释放时提供操作。
 *
 * @author Rambo Yang
 */
public class FastDFSChannelPoolHandler implements ChannelPoolHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final long readTimeout;
    private final long idleTimeout; // 最大闲置时间(秒)


    public FastDFSChannelPoolHandler(long readTimeout, long idleTimeout) {
        this.readTimeout = readTimeout;
        this.idleTimeout = idleTimeout;
    }

    @Override
    public void channelReleased(Channel ch) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("channel released : {}", ch.toString());
        }
    }

    @Override
    public void channelAcquired(Channel ch) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("channel acquired : {}", ch.toString());
        }
    }

    @Override
    public void channelCreated(Channel ch) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("channel created : {}", ch.toString());
        }

        ch.pipeline()
                .addLast(new IdleStateHandler(readTimeout, 0, idleTimeout, TimeUnit.MILLISECONDS))
                .addLast(new ChunkedWriteHandler())
                .addLast(new FastDFSHandler());
    }
}
