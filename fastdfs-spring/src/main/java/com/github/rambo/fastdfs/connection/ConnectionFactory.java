package com.github.rambo.fastdfs.connection;

import com.github.rambo.fastdfs.constant.ConnectionTypeEnum;
import com.github.rambo.fastdfs.core.connection.ServerNode;

/**
 * Thread-safe factory of FastDFS storage connections.
 *
 * @author Rambo Yang
 */
public interface ConnectionFactory {

    /**
     * Providers a suitable connection for interacting with storage.
     *
     * @return connection for interacting with storage
     */
    ServerNode getConnection();

    ServerNode getConnection(ConnectionTypeEnum type);

    ServerNode getClusterConnection();
}
