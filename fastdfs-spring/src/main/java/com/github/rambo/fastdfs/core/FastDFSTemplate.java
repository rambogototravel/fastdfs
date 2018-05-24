package com.github.rambo.fastdfs.core;

import com.github.rambo.fastdfs.codec.FileIDDecoder;
import com.github.rambo.fastdfs.codec.FileUploadEncoder;
import com.github.rambo.fastdfs.core.codec.Decoder;
import com.github.rambo.fastdfs.core.codec.Encoder;
import com.github.rambo.fastdfs.core.connection.ServerNode;
import com.github.rambo.fastdfs.support.FastDFSAccessor;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Rambo Yang
 */
public class FastDFSTemplate extends FastDFSAccessor implements FastDFSOperations {

    @Override
    public <T> CompletableFuture<T> execute(ServerNode node, Encoder encoder, Decoder decoder) {
        return getExecutor().execute(node, encoder, decoder);
    }

    @Override
    public FileID upload(File file) {
        return upload(null, file);
    }

    @Override
    public FileID upload(String group, File file) {
        CompletableFuture<FileID> future = execute(getConnectionFactory().getConnection(), new FileUploadEncoder(file, (byte) 0), FileIDDecoder.INSTANCE);
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
    public byte[] download(FileID fileID) {
        return new byte[0];
    }

    @Override
    public void download(FileID fileID, Object out) {

    }
}
