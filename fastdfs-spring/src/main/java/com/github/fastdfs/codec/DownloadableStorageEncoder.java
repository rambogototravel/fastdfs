package com.github.fastdfs.codec;


import com.github.fastdfs.core.FileId;
import com.github.fastdfs.core.constant.FastDFSConsts;

/**
 * @author Rambo Yang
 */
public class DownloadableStorageEncoder extends FileIdOperationEncoder {

    public DownloadableStorageEncoder(FileId fileId) {
        super(fileId);
    }

    @Override
    protected byte cmd() {
        return FastDFSConsts.Commands.SERVICE_QUERY_FETCH_ONE;
    }

}
