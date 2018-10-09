package com.github.fastdfs.core.exchange;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;

import java.util.List;

/**
 * 发送器支持
 *
 * @author Rambo Yang
 */
public abstract class RequestorSupport implements Requestor {

    @Override
    public void request(Channel channel) {
        List<Object> requests = writeRequests(channel.alloc());
        requests.forEach(channel::write);
        channel.flush();
    }

    /**
     * @param alloc
     */
    protected abstract List<Object> writeRequests(ByteBufAllocator alloc);
}
