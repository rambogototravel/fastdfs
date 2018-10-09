package com.github.fastdfs.connection;

import com.github.fastdfs.core.connection.ServerNode;
import com.github.fastdfs.core.FileId;

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
    ServerNode getUploadableConnection(String group);

    ServerNode getDownloadableConnection(FileId fileId);

//    List<ServerNode> getDownloadableConnections(FileId fileId);

    ServerNode getUpdatableConnection(FileId fileId);

    ServerNode getDeletableConnection(FileId fileId);

    ServerNode getClusterConnection();
}
