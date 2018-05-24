package com.github.rambo.fastdfs.codec;

import com.github.rambo.fastdfs.core.FileID;
import com.github.rambo.fastdfs.core.codec.Encoder;
import com.github.rambo.fastdfs.core.constant.FastDFSConsts;
import com.github.rambo.fastdfs.support.FastDFSUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Rambo Yang
 */
public abstract class FileIDOperationEncoder implements Encoder {

    private final FileID fileID;

    public FileIDOperationEncoder(FileID fileID) {
        this.fileID = fileID;
    }

    @Override
    public List<Object> encode(ByteBufAllocator alloc) {
        int length = FastDFSConsts.FDFS_GROUP_LEN + fileID.pathBytes().length;
        byte cmd = cmd();

        ByteBuf buf = alloc.buffer(FastDFSConsts.FDFS_HEAD_LEN + length);
        buf.writeLong(length);
        buf.writeByte(cmd);
        buf.writeByte(FastDFSConsts.ERRNO_OK);
        FastDFSUtils.writeFixLength(buf, fileID.getGroup(), FastDFSConsts.FDFS_GROUP_LEN);
        ByteBufUtil.writeUtf8(buf, fileID.getPath());
        return Collections.singletonList(buf);
    }

    /**
     * Command, subclass must be implements.
     *
     * @return
     */
    protected abstract byte cmd();
}
