package com.github.fastdfs.core.constant;

public enum Commands {
    ;

    private String name;
    private byte value;

    Commands(byte value) {
        this.value = value;
    }

    Commands(String name, byte value) {
        this.name = name;
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
