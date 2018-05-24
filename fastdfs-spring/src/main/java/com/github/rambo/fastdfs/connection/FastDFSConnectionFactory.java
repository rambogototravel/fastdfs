package com.github.rambo.fastdfs.connection;

import com.github.rambo.fastdfs.codec.StorageServerNodeDecoder;
import com.github.rambo.fastdfs.codec.UploadableStorageEncoder;
import com.github.rambo.fastdfs.constant.ConnectionTypeEnum;
import com.github.rambo.fastdfs.core.FastDFSExecutor;
import com.github.rambo.fastdfs.core.codec.Decoder;
import com.github.rambo.fastdfs.core.codec.Encoder;
import com.github.rambo.fastdfs.core.connection.ServerNode;
import org.springframework.beans.factory.InitializingBean;

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

    public FastDFSExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(FastDFSExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (clusterConfig != null) {

        }
    }

    @Override
    public ServerNode getConnection() {
        return getConnection(ConnectionTypeEnum.UPLOAD);
    }

    @Override
    public ServerNode getConnection(ConnectionTypeEnum type) {
        if (clusterConfig != null) {
            return null;
        }

        ServerNode node = null;
        switch (type) {
            case UPLOAD:
                node = getUploadableConnection(tracker, null);
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case DOWNLOAD:
                break;
        }

        return node;
    }

    @Override
    public ServerNode getClusterConnection() {
        if (cluster == null) {
            //FIXME，抛出异常
        }

        return null;
    }

    private ServerNode getUploadableConnection(TrackerNode node, String group) {
        try {
            //FIXME， get会导致阻塞等待
            return doConnection(node, new UploadableStorageEncoder(group), StorageServerNodeDecoder.INSTANCE).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private ServerNode getDownloadableConnection()

    private <T> CompletableFuture<T> doConnection(TrackerNode node, Encoder encoder, Decoder<T> decoder) {
        return executor.execute(node, encoder, decoder);
    }
}
