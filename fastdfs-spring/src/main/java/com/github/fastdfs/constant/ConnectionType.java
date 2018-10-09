package com.github.fastdfs.constant;

/**
 * @author Rambo Yang
 */
@Deprecated
public enum ConnectionType {

    UPLOAD("上传"),

    DOWNLOAD("下载"),

    UPDATE("更新"),

    DELETE("删除");

    private String desc;

    ConnectionType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
