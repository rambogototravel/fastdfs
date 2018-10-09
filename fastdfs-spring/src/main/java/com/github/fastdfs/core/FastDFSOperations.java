package com.github.fastdfs.core;

import com.github.fastdfs.core.connection.ServerNode;
import com.github.fastdfs.core.codec.Decoder;
import com.github.fastdfs.core.codec.Encoder;

import java.io.File;
import java.util.concurrent.CompletableFuture;

/**
 * @author Rambo Yang
 */
public interface FastDFSOperations {


    /**
     *
     * @param file
     * @return
     */
    FileId upload(File file);

    /**
     *
     * @param group
     * @param file
     * @return
     */
    FileId upload(String group, File file);

//    FileId upload(String fileName, byte[] content);
//
//    FileId upload(String group, String fileName, byte[] content);
//
//    FileId upload(String group, String fileName, byte[] content, FileMetadata metadata);
//
//    FileId upload(File file, FileMetadata metadata);
//
//    FileId upload(String group, File file, FileMetadata metadata);

    FileId uploadAppend(String group, File file);



    /**
     *
     * @param fileID
     * @return
     */
    byte[] download(FileId fileID);

    /**
     *
     * @param fileID
     * @param out
     */
    void download(FileId fileID, Object out);

    /**
     *
     * @param group
     * @param path
     */
    void delete(String group, String path);

    void delete(FileId fileId);


    /**
     *
     * @param node
     * @param encoder
     * @param decoder
     * @param <T>
     * @return
     */
    <T> CompletableFuture<T> execute(ServerNode node, Encoder encoder, Decoder decoder);
}
