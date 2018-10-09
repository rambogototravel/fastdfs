package com.github.fastdfs.codec;

import com.github.fastdfs.core.constant.FastDFSConsts;

import java.io.File;

public class FileUploadAppendEncoder extends FileUploadEncoder {

    public FileUploadAppendEncoder(File file, byte idx) {
        super(file, idx);
    }

    public FileUploadAppendEncoder(Object content, String fileName, long size, byte idx) {
        super(content, fileName, size, idx);
    }

    @Override
    protected byte cmd() {
        return FastDFSConsts.Commands.FILE_APPEND;
    }
}
