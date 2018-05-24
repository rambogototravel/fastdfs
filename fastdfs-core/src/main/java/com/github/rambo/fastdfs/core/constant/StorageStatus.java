package com.github.rambo.fastdfs.core.constant;

/**
 * Storage status enum
 *
 * @author Rambo Yang
 */
public enum StorageStatus {

    INIT("初始化", 0),
    WAIT_SYNC("等待同步", 1),
    SYNCING("同步中", 2),
    IP_CHANGED("", 3),
    DELETED("已删除", 4),
    OFFLINE("离线", 5),
    ONLINE("在线", 6, "尚不能提供服务"),
    ACTIVE("在线", 7, "可以提供服务"),
    NONE("", 99);


    private String name;
    private String desc;
    private int value;

    private StorageStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private StorageStatus(String name, int value, String desc) {
        this(name, value);
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StorageStatus{" + "name='" + name + '\'' + ", desc='" + desc + '\'' + ", value=" + value + '}';
    }
}
