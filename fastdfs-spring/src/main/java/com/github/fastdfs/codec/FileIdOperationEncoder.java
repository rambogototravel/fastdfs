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
 * 文件操作编码器.
 *
 * @author Rambo Yang
 */
public abstract class FileIdOperationEncoder implements Encoder {

    private final FileId fileId;

    public FileIdOperationEncoder(FileId fileId) {
        this.fileId = fileId;
    }

    @Override
    public List<Object> encode(ByteBufAllocator alloc) {
        int length = FastDFSConsts.FDFS_GROUP_LEN + fileId.pathBytes().length;
        byte cmd = cmd();

        ByteBuf buf = alloc.buffer(FastDFSConsts.FDFS_HEAD_LEN + length);
        buf.writeLong(length);
        buf.writeByte(cmd);
        buf.writeByte(FastDFSConsts.ERRNO_OK);
        FastDFSUtils.writeFixLength(buf, fileId.getGroup(), FastDFSConsts.FDFS_GROUP_LEN);
        ByteBufUtil.writeUtf8(buf, fileId.getPath());
        return Collections.singletonList(buf);
    }

    /**
     * Command, subclass must be implements.
     *
     * @return
     */
    protected abstract byte cmd();
}
