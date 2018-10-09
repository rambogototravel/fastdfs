package com.github.fastdfs.core;

import com.github.fastdfs.FastDFSDataOverflowException;
import com.github.fastdfs.FastDFSException;
import com.github.fastdfs.FastDFSReadTimeoutException;
import com.github.fastdfs.FastDFSTimeoutException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

/**
 * @author Rambo Yang
 */
public class FastDFSHandler extends ByteToMessageDecoder {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile FastDFSOperation<?> operation;

    void operation(FastDFSOperation<?> operation) {
        this.operation = operation;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (null != operation) {
            operation.await(in);
            return;
        }

        if (in.readableBytes() <= 0) {
            return;
        }

        throw new FastDFSDataOverflowException(
                String.format(
                        "FastDFS channel %s remain %s data bytes, but there is not operation await.",
                        ctx.channel(), in.readableBytes()
                )
        );
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        // read idle event.
        if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT || evt == IdleStateEvent.READER_IDLE_STATE_EVENT) {

            if (null != operation) {
                throw new FastDFSReadTimeoutException(String.format("execute %s read timeout.", operation));
            }
            return;
        }

        // all idle event.
        if (evt == IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT || evt == IdleStateEvent.ALL_IDLE_STATE_EVENT) {
            throw new FastDFSTimeoutException("FastDFS channel was idle timeout.");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (null == operation) {
            return;
        }

        if (!operation.isDone()) {
            throw new FastDFSException("channel closed.");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();

        Throwable error = translateException(cause);
        if (null != operation) {
            operation.caught(error);
            return;
        }

        // idle timeout.
        if (error instanceof FastDFSTimeoutException) {
            logger.debug(error.getMessage(), error);
            return;
        }

        logger.error(error.getMessage(), error);
    }

    private Throwable translateException(Throwable cause) {
        if (cause instanceof FastDFSException) {
            return cause;
        }

        Throwable unwrap = cause;
        for (; ; ) {

            if (unwrap instanceof InvocationTargetException) {
                unwrap = ((InvocationTargetException) unwrap).getTargetException();
                continue;
            }

            if (unwrap instanceof UndeclaredThrowableException) {
                unwrap = ((UndeclaredThrowableException) unwrap).getUndeclaredThrowable();
                continue;
            }

            break;
        }

        return new FastDFSException("FastDFS operation error.", unwrap);
    }
}
