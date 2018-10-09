package com.github.fastdfs.core.constant;

/**
 * @author Rambo Yang
 */
public enum RetCodes {

    ERR_NO_ENOENT("", 2),

    ERR_NO_EIO("", 5),

    ERR_NO_EBUSY("", 16),

    ERR_NO_EINVAL("", 22),

    ERR_NO_ENOSPC("", 28),

    ERR_NO_EALREADY("", 114);


    private String name;
    private int value;

    RetCodes(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
