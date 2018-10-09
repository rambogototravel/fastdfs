package com.github.fastdfs.codec;

import com.github.fastdfs.connection.StorageNode;
import com.github.fastdfs.support.FastDFSUtils;
import com.github.fastdfs.core.codec.Decoder;
import com.github.fastdfs.core.constant.FastDFSConsts;
import io.netty.buffer.ByteBuf;

/**
 * @author Rambo Yang
 */
public enum StorageServerNodeDecoder implements Decoder<StorageNode> {

    INSTANCE;

    @Override
    public long expectLength() {
        return FastDFSConsts.FDFS_STORAGE_STORE_LEN;
    }

    @Override
    public StorageNode decode(ByteBuf buf) {
        String group = FastDFSUtils.readString(buf, FastDFSConsts.FDFS_GROUP_LEN);
        String host = FastDFSUtils.readString(buf, FastDFSConsts.FDFS_HOST_LEN);
        int port = (int) buf.readLong();
        byte idx = buf.readByte();
        return new StorageNode(group, host, port, idx);
    }
}
