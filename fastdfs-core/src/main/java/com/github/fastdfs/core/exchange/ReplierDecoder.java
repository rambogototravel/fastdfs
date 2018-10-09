package com.github.fastdfs.core.exchange;

import com.github.fastdfs.core.codec.Decoder;
import io.netty.buffer.ByteBuf;

import java.util.concurrent.CompletableFuture;

/**
 * 接收处理解码器
 *
 * @author Rambo Yang
 */
public class ReplierDecoder<T> extends ReplierSupport<T> {

    private Decoder<T> decoder;

    public ReplierDecoder(Decoder<T> decoder) {
        this.decoder = decoder;
    }

    @Override
    protected long expectLength() {
        return decoder.expectLength();
    }

    @Override
    protected void readContent(ByteBuf in, CompletableFuture<T> promise) {
        if (in.readableBytes() < length) {
            return;
        }

        ByteBuf buf = in.readSlice((int) length);
        T result = decoder.decode(buf);
        promise.complete(result);
        atHead = true;
    }

    @Override
    public String toString() {
        return "ReplierDecoder{" + "decoder=" + decoder + '}';
    }
}
