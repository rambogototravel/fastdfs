package com.github.fastdfs.codec;

import com.github.fastdfs.FastDFSException;
import com.github.fastdfs.core.FileId;
import com.github.fastdfs.core.codec.Decoder;
import com.github.fastdfs.core.constant.FastDFSConsts;
import com.github.fastdfs.support.FastDFSUtils;
import io.netty.buffer.ByteBuf;

/**
 * 文件上传解码器
 *
 * @author Rambo Yang
 */
public enum FileIdDecoder implements Decoder<FileId> {

    INSTANCE;

    @Override
    public FileId decode(ByteBuf buf) {
        int length = buf.readableBytes();
        if (length <= FastDFSConsts.FDFS_GROUP_LEN) {
            throw new FastDFSException("body length: " + length + ", lte required group name length 16.");
        }

        String group = FastDFSUtils.readString(buf, FastDFSConsts.FDFS_GROUP_LEN);
        String path = FastDFSUtils.readString(buf);
        return new FileId(group, path);
    }
}
