package com.github.rambo.fastdfs.support;

import com.github.rambo.fastdfs.connection.ConnectionFactory;
import com.github.rambo.fastdfs.core.FastDFSExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Rambo Yang
 */
public class FastDFSAccessor implements InitializingBean {

    /** Logger  available to subclasses */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ConnectionFactory connectionFactory;

    private FastDFSExecutor executor;

    /**
     * Returns the connectionFactory.
     *
     * @return the connectionFactory
     */
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Sets the connectionFactory.
     *
     * @param connectionFactory
     */
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public FastDFSExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(FastDFSExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(getConnectionFactory(), "ConnectionFactory is required");
    }
}
