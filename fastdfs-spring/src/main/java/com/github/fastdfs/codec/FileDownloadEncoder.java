package com.github.fastdfs.codec;

import com.github.fastdfs.core.FileId;
import com.github.fastdfs.core.codec.Encoder;
import com.github.fastdfs.core.constant.FastDFSConsts;
import com.github.fastdfs.support.FastDFSUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Rambo Yang
 */
public class FileDownloadEncoder implements Encoder {

    private static final int DEFAULT_OFFSET = 0;

    private static final int DEFAULT_SIZE = 0;

    private final FileId fileID;

    private final int offset;

    private final int size;

    public FileDownloadEncoder(FileId fileID) {
        this(fileID, DEFAULT_OFFSET, DEFAULT_SIZE);
    }

    public FileDownloadEncoder(FileId fileID, int offset, int size) {
        this.fileID = fileID;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public List<Object> encode(ByteBufAllocator alloc) {
        byte[] pathBytes = fileID.pathBytes();
        int length = 2 * FastDFSConsts.FDFS_LONG_LEN + FastDFSConsts.FDFS_GROUP_LEN + pathBytes.length;
        byte cmd = FastDFSConsts.Commands.FILE_DOWNLOAD;

        ByteBuf buf = alloc.buffer(FastDFSConsts.FDFS_HEAD_LEN + length);
        buf.writeLong(length);
        buf.writeByte(cmd);
        buf.writeByte(FastDFSConsts.ERRNO_OK);

        buf.writeLong(offset);
        buf.writeLong(size);
        FastDFSUtils.writeFixLength(buf, fileID.getGroup(), FastDFSConsts.FDFS_GROUP_LEN);
        ByteBufUtil.writeUtf8(buf, fileID.getPath());
        return Collections.singletonList(buf);
    }
}
