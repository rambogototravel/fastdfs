package com.github.fastdfs.codec;


import com.github.fastdfs.core.FileId;
import com.github.fastdfs.core.constant.FastDFSConsts;

/**
 *
 */
public class FileDeleteEncoder extends FileIdOperationEncoder {

    public FileDeleteEncoder(FileId fileId) {
        super(fileId);
    }

    @Override
    protected byte cmd() {
        return FastDFSConsts.Commands.FILE_DELETE;
    }
}
