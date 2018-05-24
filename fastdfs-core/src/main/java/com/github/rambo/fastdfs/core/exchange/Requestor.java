package com.github.rambo.fastdfs.core.exchange;

import io.netty.channel.Channel;

/**
 * 发送器
 *
 * @author Rambo Yang
 */
public interface Requestor {

    /**
     * 发送请求
     *
     * @param channel 通道
     */
    void request(Channel channel);
}
