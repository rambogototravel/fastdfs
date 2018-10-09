package com.github.fastdfs.core;

import com.github.fastdfs.core.constant.FastDFSConsts;

import java.io.File;
import java.util.Objects;

/**
 *
 * @author Rambo Yang
 */
public class FileId {

    /**  */
    private final String group;

    /**  */
    private final String path;

    public FileId(String group, String path) {
        this.group = Objects.requireNonNull(group, "group must not be null.");
        this.path = Objects.requireNonNull(path, "path must not be null.");
    }

    public String getGroup() {
        return group;
    }

    public String getPath() {
        return path;
    }

    public byte[] pathBytes() {
        return path.getBytes(FastDFSConsts.UTF_8);
    }

    public byte[] toBytes() {
        return (group + File.separator + path).getBytes(FastDFSConsts.UTF_8);
    }

    @Override
    public String toString() {
        return "FileId{" + "group='" + group + '\'' + ", path='" + path + '\'' + '}';
    }
}
