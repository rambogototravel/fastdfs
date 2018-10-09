package com.github.fastdfs.codec;

import com.github.fastdfs.core.constant.FastDFSConsts;
import com.github.fastdfs.support.FastDFSUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.io.File;

/**
 * @author Rambo Yang
 */
public class FileUploadEncoder extends FileOperationEncoder {

    private final byte idx;

    private final String ext;

    public FileUploadEncoder(File file, byte idx) {
        super(file);
        this.idx = idx;
        this.ext = FastDFSUtils.getFileExt(file.getName(), "tmp");
    }

    public FileUploadEncoder(Object content, String fileName, long size, byte idx) {
        super(content, size);
        this.idx = idx;
        this.ext = FastDFSUtils.getFileExt(fileName, "tmp");
    }

    @Override
    protected byte cmd() {
        return FastDFSConsts.Commands.FILE_UPLOAD;
    }

    @Override
    protected ByteBuf metaData(ByteBufAllocator alloc) {
        int metaLen = FastDFSConsts.FDFS_STORE_PATH_INDEX_LEN + FastDFSConsts.FDFS_PROTO_PKG_LEN_SIZE + FastDFSConsts.FDFS_FILE_EXT_LEN;
        ByteBuf buf = alloc.buffer(metaLen);
        buf.writeByte(idx);
        buf.writeLong(getSize());
        FastDFSUtils.writeFixLength(buf, ext, FastDFSConsts.FDFS_FILE_EXT_LEN);
        return buf;
    }
}
