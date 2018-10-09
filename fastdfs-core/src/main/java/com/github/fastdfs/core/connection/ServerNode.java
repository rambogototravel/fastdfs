package com.github.fastdfs.core.connection;

/**
 * @author Rambo Yang
 */
public class ServerNode implements NamedNode {

    private String host;

    private int port;

    public ServerNode(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String toString() {
        return "ServerNode{" + "host='" + host + '\'' + ", port=" + port + '}';
    }
}
