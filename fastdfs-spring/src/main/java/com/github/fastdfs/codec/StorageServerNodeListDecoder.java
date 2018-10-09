package com.github.fastdfs.codec;

import com.github.fastdfs.FastDFSException;
import com.github.fastdfs.connection.StorageNode;
import com.github.fastdfs.core.codec.Decoder;
import com.github.fastdfs.core.constant.FastDFSConsts;
import com.github.fastdfs.support.FastDFSUtils;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public enum StorageServerNodeListDecoder implements Decoder<List<StorageNode>> {
    INSTANCE;

    @Override
    public List<StorageNode> decode(ByteBuf buf) {
        int size = buf.readableBytes();
        if (size < FastDFSConsts.FDFS_STORAGE_LEN) {
            throw new FastDFSException("body length : " + size + " is less than required length " + FastDFSConsts.FDFS_STORAGE_LEN);
        }
        if ((size - FastDFSConsts.FDFS_STORAGE_LEN) % FastDFSConsts.FDFS_HOST_LEN != 0) {
            throw new FastDFSException("body length : " + size + " is invalidate. ");
        }

        int count = (size - FastDFSConsts.FDFS_STORAGE_LEN) / FastDFSConsts.FDFS_HOST_LEN + 1;
        List<StorageNode> results = new ArrayList<>(count);

        String group = FastDFSUtils.readString(buf, FastDFSConsts.FDFS_GROUP_LEN);
        String mainHost = FastDFSUtils.readString(buf, FastDFSConsts.FDFS_HOST_LEN);
        int port = (int) buf.readLong();

        results.add(new StorageNode(group, mainHost, port));
        for (int i = 1; i < count; i++) {
            String host = FastDFSUtils.readString(buf, FastDFSConsts.FDFS_HOST_LEN);
            results.add(new StorageNode(group, host, port));
        }

        return results;
    }
}
