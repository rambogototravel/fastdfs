package com.github.rambo.fastdfs.codec;

import com.github.rambo.fastdfs.FastDFSException;
import com.github.rambo.fastdfs.core.FileID;
import com.github.rambo.fastdfs.core.codec.Decoder;
import com.github.rambo.fastdfs.core.constant.FastDFSConsts;
import com.github.rambo.fastdfs.support.FastDFSUtils;
import io.netty.buffer.ByteBuf;

/**
 * 文件上传解码器
 *
 * @author Rambo Yang
 */
public enum FileIDDecoder implements Decoder<FileID> {
    INSTANCE;

    @Override
    public FileID decode(ByteBuf buf) {
        int length = buf.readableBytes();
        if (length <= FastDFSConsts.FDFS_GROUP_LEN) {
            throw new FastDFSException("body length: " + length + ", lte required group name length 16.");
        }

        String group = FastDFSUtils.readString(buf, FastDFSConsts.FDFS_GROUP_LEN);
        String path = FastDFSUtils.readString(buf);
        return new FileID(group, path);
    }
}
