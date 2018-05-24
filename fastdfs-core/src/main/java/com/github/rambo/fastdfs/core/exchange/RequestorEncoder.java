package com.github.rambo.fastdfs.core.exchange;

import com.github.rambo.fastdfs.core.codec.Encoder;
import io.netty.buffer.ByteBufAllocator;

import java.util.List;

/**
 * @author Rambo Yang
 */
public class RequestorEncoder extends RequestorSupport {

    private final Encoder encoder;

    public RequestorEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    protected List<Object> writeRequests(ByteBufAllocator alloc) {
        return encoder.encode(alloc);
    }
}
