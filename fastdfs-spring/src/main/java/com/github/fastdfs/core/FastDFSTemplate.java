package com.github.fastdfs.core;

import com.github.fastdfs.codec.FileDeleteEncoder;
import com.github.fastdfs.codec.FileIdDecoder;
import com.github.fastdfs.codec.FileUploadEncoder;
import com.github.fastdfs.connection.StorageNode;
import com.github.fastdfs.core.connection.ServerNode;
import com.github.fastdfs.core.exchange.Replier;
import com.github.fastdfs.support.FastDFSAccessor;
import com.github.fastdfs.codec.FileUploadAppendEncoder;
import com.github.fastdfs.core.codec.Decoder;
import com.github.fastdfs.core.codec.Encoder;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Rambo Yang
 */
public class FastDFSTemplate extends FastDFSAccessor implements FastDFSOperations {

    @Override
    public FileId upload(File file) {
        return upload(null, file);
    }

    @Override
    public FileId upload(String group, File file) {
        CompletableFuture<FileId> future = execute(getConnectionFactory().getUploadableConnection(group),
                new FileUploadEncoder(file, (byte) 0), FileIdDecoder.INSTANCE);

        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FileId uploadAppend(String group, File file) {
        StorageNode node = (StorageNode) getConnectionFactory().getUploadableConnection(group);
        CompletableFuture<FileId> future = execute(node,
                new FileUploadAppendEncoder(file, node.getIdx()), FileIdDecoder.INSTANCE);


        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] download(FileId fileId) {
        return new byte[0];
    }

    @Override
    public void download(FileId fileId, Object out) {

    }

    @Override
    public void delete(String group, String path) {
        delete(new FileId(group, path));
    }

    @Override
    public void delete(FileId fileId) {
        execute(getConnectionFactory().getUpdatableConnection(fileId),
                new FileDeleteEncoder(fileId), Replier.NOPDecoder.INSTANCE);
    }

    @Override
    public <T> CompletableFuture<T> execute(ServerNode node, Encoder encoder, Decoder decoder) {
        return getExecutor().execute(node, encoder, decoder);
    }
}
