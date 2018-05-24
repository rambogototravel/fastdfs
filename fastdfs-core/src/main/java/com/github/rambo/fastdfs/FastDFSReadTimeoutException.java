package com.github.rambo.fastdfs;

/**
 * @author Rambo Yang
 */
public class FastDFSReadTimeoutException extends FastDFSTimeoutException {
    private static final long serialVersionUID = -4345410185778108716L;

    public FastDFSReadTimeoutException(String message) {
        super(message);
    }
}
