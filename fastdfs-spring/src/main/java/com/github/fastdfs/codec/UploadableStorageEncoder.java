package com.github.fastdfs.codec;

import com.github.fastdfs.support.FastDFSUtils;
import com.github.fastdfs.core.codec.Encoder;
import com.github.fastdfs.core.constant.FastDFSConsts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.Collections;
import java.util.List;

/**
 * @author Rambo Yang
 */
public class UploadableStorageEncoder implements Encoder {

    private String group;

    public UploadableStorageEncoder() {
    }

    public UploadableStorageEncoder(String group) {
        this.group = group;
    }

    @Override
    public List<Object> encode(ByteBufAllocator alloc) {
        int length = FastDFSUtils.isEmpty(group) ? 0 : FastDFSConsts.FDFS_GROUP_LEN;
        byte cmd = FastDFSUtils.isEmpty(group) ? FastDFSConsts.Commands.SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE :
                FastDFSConsts.Commands.SERVICE_QUERY_STORE_WITH_GROUP_ONE;

        ByteBuf buf = alloc.buffer(FastDFSConsts.FDFS_HEAD_LEN + length);
        buf.writeLong(length);
        buf.writeByte(cmd);
        buf.writeByte(FastDFSConsts.ERRNO_OK);
        if (!FastDFSUtils.isEmpty(group)) {
            FastDFSUtils.writeFixLength(buf, group, FastDFSConsts.FDFS_GROUP_LEN);
        }
        return Collections.singletonList(buf);
    }
}
