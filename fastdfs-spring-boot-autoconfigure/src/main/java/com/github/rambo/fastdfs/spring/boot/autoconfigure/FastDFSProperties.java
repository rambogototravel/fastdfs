package com.github.rambo.fastdfs.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Configuration properties for FastDFS.
 *
 * @autho Rambo Yang
 */
@ConfigurationProperties(prefix = FastDFSProperties.FASTDFS_PREFIX)
public class FastDFSProperties {

    public static final String FASTDFS_PREFIX = "fastdfs";

    /**
     * FastDFS tracker server host.
     */
    private String host;

    /**
     * FastDFS tracker server port.
     */
    private int port = 22122;

    private Pool pool;

    private Cluster cluster;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }


    /**
     * Pool properties.
     */
    public static class Pool {
        private int connectTime;

        private int idleTimeout;

        private int maxConnPerHost;

        private int readTimeout;

        private int maxThreads;

        public int getConnectTime() {
            return connectTime;
        }

        public void setConnectTime(int connectTime) {
            this.connectTime = connectTime;
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

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getMaxThreads() {
            return maxThreads;
        }

        public void setMaxThreads(int maxThreads) {
            this.maxThreads = maxThreads;
        }
    }

    /**
     * Cluster properties.
     */
    public static class Cluster {
        /**
         * Comma-separated list of "host:port" pairs to bootstrap from. This represents an
         * "initial" list of cluster nodes and is required to have at least one entry.
         */
        private List<String> nodes;

        public List<String> getNodes() {
            return nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }
}
