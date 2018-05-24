package com.github.rambo.fastdfs.core.connection;

/**
 * A simple "struct" encapsulating the configuration for a {@link FastDFSChannelPoolGroup}
 * and {@link io.netty.channel.nio.NioEventLoopGroup}.
 *
 * <p>
 * This class is not thread-safe; it is only intended to be used to provide
 * attribute used when creating a pool.
 *
 * @author Rambo Yang
 */
public class FastDFSPoolConfig {

    /**
     * The default value for the {@code maxThreads} configuration attribute.
     * @see FastDFSPoolConfig#getMaxThreads()
     */
    private final int DEFAULT_MAX_THREADS = 2;

    /**
     * The default value for the {@code connectTime} configuration attribute.
     * @see FastDFSPoolConfig#getConnectTime()
     */
    private final int DEFAULT_CONNECT_TIME = 30000;

    /**
     * The default value for the {@code readTimeout} configuration attribute.
     * @see FastDFSPoolConfig#getReadTimeout()
     */
    private final int DEFAULT_READ_TIMEOUT = 30000;

    /**
     * The default value for the {@code idleTimeout} configuration attribute.
     * @see FastDFSPoolConfig#getIdleTimeout()
     */
    private final int DEFAULT_IDLE_TIMEOUT = 30000;

    /**
     * The default value for the {@code maxConnPerHost} configuration attribute.
     * @see FastDFSPoolConfig#getMaxConnPerHost()
     */
    private final int DEFAULT_MAX_CON_PER_HOST = 10;

    private int maxThreads = DEFAULT_MAX_THREADS;

    private int connectTime = DEFAULT_CONNECT_TIME;

    private int readTimeout = DEFAULT_READ_TIMEOUT;

    private int idleTimeout = DEFAULT_IDLE_TIMEOUT;

    private int maxConnPerHost = DEFAULT_MAX_CON_PER_HOST;

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(int connectTime) {
        this.connectTime = connectTime;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public int getMaxConnPerHost() {
        return maxConnPerHost;
    }

    public void setMaxConnPerHost(int maxConnPerHost) {
        this.maxConnPerHost = maxConnPerHost;
    }
}
