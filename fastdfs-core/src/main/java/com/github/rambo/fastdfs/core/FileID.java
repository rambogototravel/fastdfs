package com.github.rambo.fastdfs.core;

import com.github.rambo.fastdfs.core.constant.FastDFSConsts;

import java.io.File;
import java.util.Objects;

/**
 * @author Rambo Yang
 */
public class FileID {

    private final String group;

    private final String path;

    public FileID(String group, String path) {
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
        return "FileID{" + "group='" + group + '\'' + ", path='" + path + '\'' + '}';
    }
}
