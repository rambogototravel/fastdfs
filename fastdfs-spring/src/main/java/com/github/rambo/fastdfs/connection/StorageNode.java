package com.github.rambo.fastdfs.connection;

import com.github.rambo.fastdfs.core.connection.ServerNode;

/**
 * @author Rambo Yang
 */
public class StorageNode extends ServerNode {

    private String group;

    private byte idx;

    public StorageNode(String host, int port) {
        super(host, port);
    }

    public StorageNode(String group, String host, int port) {
        this(host, port);
        this.group = group;
    }

    public StorageNode(String group, String host, int port, byte idx) {
        this(host, port);
        this.group = group;
        this.idx = idx;
    }
}
