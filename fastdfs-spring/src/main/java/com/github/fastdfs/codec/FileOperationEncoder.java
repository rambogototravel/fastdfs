package com.github.fastdfs.codec;

import com.github.fastdfs.core.codec.Encoder;
import com.github.fastdfs.core.constant.FastDFSConsts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.DefaultFileRegion;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Rambo Yang
 */
public abstract class FileOperationEncoder implements Encoder {

    private final Object content;

    private final long size;

    public FileOperationEncoder(File file) {
        long length = file.length();
        this.content = new DefaultFileRegion(file, 0, length);
        this.size = length;
    }

    public FileOperationEncoder(Object content, long size) {
        this.content = content;
        this.size = size;
    }

    public Object getContent() {
        return content;
    }

    public long getSize() {
        return size;
    }

    @Override
    public List<Object> encode(ByteBufAllocator alloc) {
        ByteBuf meta = metaData(alloc);

        ByteBuf head = alloc.buffer(FastDFSConsts.FDFS_HEAD_LEN);
        head.writeLong(meta.readableBytes() + this.size);
        head.writeByte(cmd());
        head.writeByte(FastDFSConsts.ERRNO_OK);

        CompositeByteBuf cbb = alloc.compositeBuffer();
        cbb.addComponents(head, meta);
        cbb.writerIndex(head.readableBytes() + meta.readableBytes());

        List requests = new LinkedList();
        requests.add(cbb);
        requests.add(content);
        return requests;
    }

    protected abstract byte cmd();

    protected abstract ByteBuf metaData(ByteBufAllocator alloc);
}
