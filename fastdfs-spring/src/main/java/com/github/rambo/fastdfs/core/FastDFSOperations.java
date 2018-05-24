package com.github.rambo.fastdfs.core;

import com.github.rambo.fastdfs.core.codec.Decoder;
import com.github.rambo.fastdfs.core.codec.Encoder;
import com.github.rambo.fastdfs.core.connection.ServerNode;

import java.io.File;
import java.util.concurrent.CompletableFuture;

/**
 * @author Rambo Yang
 */
public interface FastDFSOperations {
    /**
     *
     * @param node
     * @param encoder
     * @param decoder
     * @param <T>
     * @return
     */
    <T> CompletableFuture<T> execute(ServerNode node, Encoder encoder, Decoder decoder);

    /**
     *
     * @param file
     * @return
     */
    FileID upload(File file);

    /**
     *
     * @param group
     * @param file
     * @return
     */
    FileID upload(String group, File file);

//    FileID upload(String fileName, byte[] content);
//
//    FileID upload(String group, String fileName, byte[] content);
//
//    FileID upload(String group, String fileName, byte[] content, FileMetadata metadata);
//
//    FileID upload(File file, FileMetadata metadata);
//
//    FileID upload(String group, File file, FileMetadata metadata);

    /**
     *
     * @param fileID
     * @return
     */
    byte[] download(FileID fileID);

    /**
     *
     * @param fileID
     * @param out
     */
    void download(FileID fileID, Object out);
}
