package com.github.fastdfs.codec;

import com.github.fastdfs.core.FileId;
import com.github.fastdfs.core.constant.FastDFSConsts;

/**
 * 可更新的存储服务编码器.
 *
 * @author Rambo Yang
 */
public class UpdatableStoregeEncoder extends FileIdOperationEncoder {


    public UpdatableStoregeEncoder(FileId fileId) {
        super(fileId);
    }

    @Override
    protected byte cmd() {
        return FastDFSConsts.Commands.SERVICE_QUERY_UPDATE;
    }
}
