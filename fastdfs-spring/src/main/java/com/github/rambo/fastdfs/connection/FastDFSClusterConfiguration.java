package com.github.rambo.fastdfs.connection;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Configuration class used for setting up {@link Connection} via {@link ConnectionFactory}
 * @author Rambo Yang
 */
public class FastDFSClusterConfiguration {

    private static final String FASTDFS_CLUSTER_NODES_CONFIG_PROPERTY = "fastdfs.cluster.nodes";

    private Set<TrackerNode> clusterNodes;

    /**
     * Creates new {@link FastDFSClusterConfiguration}.
     */
    public FastDFSClusterConfiguration() {
    }

    /**
     * Creates new {@link FastDFSClusterConfiguration} for given hostPort combinations.
     *
     * <pre>
     * clusterHostAndPorts[0] = 127.0.0.1:22122
     * clusterHostAndPorts[1] = 127.0.0.1:22222
     * ...
     *
     * </pre>
     *
     * @param clusterNodes
     */
    public FastDFSClusterConfiguration(Collection<String> clusterNodes) {
        this(new MapPropertySource("FastDFSClusterConfiguration", asMap(clusterNodes)));
    }

    /**
     * Create {@link FastDFSClusterConfiguration} looking up values in given {@link PropertySource}.
     *
     * <pre>
     * <code>
     * fastdfs.cluster.nodes=127.0.0.1:22122,127.0.0.1:22222
     * </code>
     * </pre>
     *
     * @param propertySource must not be {@literal null}
     */
    public FastDFSClusterConfiguration(PropertySource<?> propertySource) {
        Assert.notNull(propertySource, "PropertySource must not be null!");

        this.clusterNodes = new LinkedHashSet<>();
        if (propertySource.containsProperty(FASTDFS_CLUSTER_NODES_CONFIG_PROPERTY)) {
            appendClusterNodes(StringUtils.commaDelimitedListToSet(
                    propertySource.getProperty(FASTDFS_CLUSTER_NODES_CONFIG_PROPERTY).toString()));
        }
    }

    /**
     * Returns as {@link Collections#unmodifiableSet(Set)} of {@literal cluster nodes}.
     *
     * @return {@link Set} of nodes. Never {@literal null}
     */
    public Set<TrackerNode> getClusterNodes() {
        return Collections.unmodifiableSet(clusterNodes);
    }

    /**
     * Set {@literal cluster nodes} to connect to.
     *
     * @param clusterNodes must not be {@literal null}
     */
    public void setClusterNodes(Iterable<TrackerNode> clusterNodes) {
        Assert.notNull(clusterNodes, "Cannot set cluster nodes to 'null'.");

        this.clusterNodes.clear();

        for (TrackerNode node : clusterNodes) {
            addClusterNode(node);
        }
    }

    /**
     * Add a cluster node to configuration.
     *
     * @param node must not be {@literal null}
     */
    public void addClusterNode(TrackerNode node) {
        Assert.notNull(node, "ClusterNode must not be 'null'.");
        this.clusterNodes.add(node);
    }

    /**
     *
     * @param clusterHostAndPorts
     * @return
     */
    private static Map<String, Object> asMap(Collection<String> clusterHostAndPorts) {
        Assert.notNull(clusterHostAndPorts, "ClusterHostAndPorts must not be null!");

        Map<String, Object> map = new HashMap<>(clusterHostAndPorts.size());
        map.put(FASTDFS_CLUSTER_NODES_CONFIG_PROPERTY, StringUtils.collectionToCommaDelimitedString(clusterHostAndPorts));
        return map;
    }

    private void appendClusterNodes(Set<String> hostAndPorts) {
        for (String hostAndPort : hostAndPorts) {
            addClusterNode(readHostAndPortFromString(hostAndPort));
        }
    }

    private TrackerNode readHostAndPortFromString(String hostAndPort) {
        String[] args = StringUtils.split(hostAndPort, ":");

        Assert.notNull(args, "HostAndPort need to be seperated by ':'.");
        Assert.isTrue(args.length == 2, "Host and Port String needs to specified as host:port");
        return new TrackerNode(args[0], Integer.valueOf(args[1]));
    }
}
