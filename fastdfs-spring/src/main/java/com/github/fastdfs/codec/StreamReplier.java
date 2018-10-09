package com.github.fastdfs.codec;

import com.github.fastdfs.FastDFSException;
import com.github.fastdfs.core.exchange.ReplierSupport;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.GatheringByteChannel;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author Rambo Yang
 */
public class StreamReplier extends ReplierSupport<Void> {

    private final Sink sink;

    private long readed = 0;

    private StreamReplier(Sink sink) {
        this.sink = sink;
    }

    @Override
    protected void readContent(ByteBuf in, CompletableFuture<Void> promise) {

        try {
            int before = in.readableBytes();
            sink.write(in);
            int after = in.readableBytes();
            readed += before - after;

            if (readed >= length) {
                promise.complete(null);
            }
        } catch (IOException e) {
            throw new FastDFSException("write response to output error.", e);
        }
    }

    public static StreamReplier stream(Object out) {
        Objects.requireNonNull(out);
        return new StreamReplier(newSink(out));
    }

    private static Sink newSink(Object out) {
        if (out instanceof OutputStream) {
            return new OioSink((OutputStream) out);
        }

        if (out instanceof GatheringByteChannel) {
            return new NioSink((GatheringByteChannel) out);
        }

        throw new FastDFSException("unknown sink output type " + out.getClass().getName());
    }



    private interface Sink {
        void write(ByteBuf buf) throws IOException;
    }

    private static class OioSink implements Sink {
        private OutputStream out;

        OioSink(OutputStream out) {
            this.out = out;
        }

        @Override
        public void write(ByteBuf buf) throws IOException {
            buf.readBytes(out, buf.readableBytes());
        }
    }

    private static class NioSink implements Sink {
        private GatheringByteChannel out;

        NioSink(GatheringByteChannel out) {
            this.out = out;
        }

        @Override
        public void write(ByteBuf buf) throws IOException {
            buf.readBytes(out, buf.readableBytes());
        }
    }
}
