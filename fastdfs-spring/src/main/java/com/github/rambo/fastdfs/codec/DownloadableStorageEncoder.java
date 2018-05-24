package com.github.rambo.fastdfs.codec;


import com.github.rambo.fastdfs.core.FileID;
import com.github.rambo.fastdfs.core.constant.FastDFSConsts;

/**
 * @author Rambo Yang
 */
public class DownloadableStorageEncoder extends FileIDOperationEncoder {

    public DownloadableStorageEncoder(FileID fileID) {
        super(fileID);
    }

    @Override
    protected byte cmd() {
        return FastDFSConsts.Commands.SERVICE_QUERY_FETCH_ONE;
    }

}
