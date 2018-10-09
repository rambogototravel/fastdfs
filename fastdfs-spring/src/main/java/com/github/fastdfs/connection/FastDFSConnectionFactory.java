package com.github.fastdfs.connection;

import com.github.fastdfs.codec.*;
import com.github.fastdfs.core.connection.ServerNode;
import com.github.rambo.fastdfs.codec.*;
import com.github.fastdfs.core.FastDFSExecutor;
import com.github.fastdfs.core.FileId;
import com.github.fastdfs.core.codec.Decoder;
import com.github.fastdfs.core.codec.Encoder;
import com.github.fastdfs.support.FastDFSUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Rambo Yang
 */
public class FastDFSConnectionFactory implements InitializingBean, ConnectionFactory {

    private TrackerNode tracker;

    private FastDFSClusterConfiguration clusterConfig;

    private TrackerCluster cluster;

    private FastDFSExecutor executor;

    public FastDFSConnectionFactory() {}

    public FastDFSConnectionFactory(TrackerNode tracker, FastDFSExecutor executor) {
        this.tracker = tracker;
        this.executor = executor;
    }

    public FastDFSConnectionFactory(FastDFSClusterConfiguration clusterConfig, FastDFSExecutor executor) {
        this.clusterConfig = clusterConfig;
        this.executor = executor;
    }

    public TrackerNode getTracker() {
        return tracker;
    }

    public void setTracker(TrackerNode tracker) {
        this.tracker = tracker;
    }

    public FastDFSClusterConfiguration getClusterConfig() {
        return clusterConfig;
    }

    public void setClusterConfig(FastDFSClusterConfiguration clusterConfig) {
        this.clusterConfig = clusterConfig;
    }

    /**
     *
     * @return
     */
    public FastDFSExecutor getExecutor() {
        return executor;
    }

    /**
     *
     * @param executor
     */
    public void setExecutor(FastDFSExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (clusterConfig != null) {

        }
        Assert.notNull(tracker, "TrackerNode is required");
        Assert.notNull(executor, "FastDFSExecutor is required");
    }

    @Override
    public ServerNode getUploadableConnection(String group) {
        try {
            //FIXME， get会导致阻塞等待
            return doConnection(tracker, new UploadableStorageEncoder(group), StorageServerNodeDecoder.INSTANCE).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ServerNode getDownloadableConnection(FileId fileId) {
        try {
            //FIXME， get会导致阻塞等待
            return doConnection(tracker, new DownloadableStorageEncoder(fileId),
                    StorageServerNodeListDecoder.INSTANCE).thenApply(FastDFSUtils::first).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public List<ServerNode> getDownloadableConnections(FileId fileId) {
//        try {
//            //FIXME， get会导致阻塞等待
//            return (List<ServerNode>) doConnection(tracker, new DownloadableStorageEncoder(fileId),
//                    StorageServerNodeListDecoder.INSTANCE).get();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public ServerNode getUpdatableConnection(FileId fileId) {
        try {
            //FIXME， get会导致阻塞等待
            return doConnection(tracker, new UpdatableStoregeEncoder(fileId),
                    StorageServerNodeListDecoder.INSTANCE).thenApply(FastDFSUtils::first).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ServerNode getDeletableConnection(FileId fileId) {
        return getUpdatableConnection(fileId);
    }

    @Override
    public ServerNode getClusterConnection() {
        if (cluster == null) {
            //FIXME，抛出异常
        }

        return null;
    }


    private <T> CompletableFuture<T> doConnection(TrackerNode node, Encoder encoder, Decoder<T> decoder) {
        return executor.execute(node, encoder, decoder);
    }
}
