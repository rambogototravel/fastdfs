package com.github.rambo.fastdfs.spring.boot.autoconfigure;

import com.github.rambo.fastdfs.connection.FastDFSClusterConfiguration;
import com.github.rambo.fastdfs.connection.FastDFSConnectionFactory;
import com.github.rambo.fastdfs.connection.TrackerNode;
import com.github.rambo.fastdfs.core.FastDFSExecutor;
import com.github.rambo.fastdfs.core.FastDFSTemplate;
import com.github.rambo.fastdfs.core.connection.FastDFSPoolConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-Configuration} for FastDFS.
 *
 * @autho Rambo Yang
 */
@Configuration
@ConditionalOnClass(FastDFSExecutor.class)
@EnableConfigurationProperties(FastDFSProperties.class)
public class FastDFSAutoConfiguration {

    private final FastDFSProperties properties;

    private final FastDFSClusterConfiguration clusterConfiguration;


    public FastDFSAutoConfiguration(FastDFSProperties properties, ObjectProvider<FastDFSClusterConfiguration> clusterConfiguration) {
        this.properties = properties;
        this.clusterConfiguration = clusterConfiguration.getIfAvailable();
    }

    @Bean
    @ConditionalOnMissingBean(FastDFSExecutor.class)
    public FastDFSExecutor fastDFSExecutor() {
        FastDFSPoolConfig poolConfig = this.properties.getPool() != null ? fastDFSPoolConfig() : new FastDFSPoolConfig();
        return new FastDFSExecutor(poolConfig);
    }

    @Bean
    @ConditionalOnBean(FastDFSExecutor.class)
    public FastDFSConnectionFactory fastDFSConnectionFactory() {
        return createFastDFSConnectionFactory();
    }

    /**
     * Create a  {@link FastDFSClusterConfiguration} if necessary.
     *
     * @return {@literal null} if no cluster setting are set.
     */
    protected FastDFSClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        }
        if (this.properties.getCluster() == null) {
            return null;
        }

        FastDFSClusterConfiguration config = new FastDFSClusterConfiguration();
        //FIXME, 补充集群信息的操作
        return config;
    }

    private FastDFSConnectionFactory createFastDFSConnectionFactory() {
        if (getClusterConfiguration() != null) {
            return new FastDFSConnectionFactory(getClusterConfiguration(), fastDFSExecutor());
        }

        TrackerNode node = new TrackerNode(properties.getHost(), properties.getPort());
        return new FastDFSConnectionFactory(node, fastDFSExecutor());
    }

    private FastDFSPoolConfig fastDFSPoolConfig() {
        FastDFSPoolConfig config = new FastDFSPoolConfig();
        config.setConnectTime(properties.getPool().getConnectTime());
        config.setIdleTimeout(properties.getPool().getIdleTimeout());
        config.setMaxConnPerHost(properties.getPool().getMaxConnPerHost());
        config.setReadTimeout(properties.getPool().getReadTimeout());
        config.setMaxThreads(properties.getPool().getMaxThreads());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public FastDFSTemplate fastDFSTemplate(FastDFSConnectionFactory connectionFactory, FastDFSExecutor executor) {
        FastDFSTemplate fastDFSTemplate = new FastDFSTemplate();
        fastDFSTemplate.setConnectionFactory(connectionFactory);
        fastDFSTemplate.setExecutor(executor);
        return fastDFSTemplate;
    }
}
