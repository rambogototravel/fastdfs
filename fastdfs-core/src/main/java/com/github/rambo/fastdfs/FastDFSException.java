package com.github.rambo.fastdfs;

/**
 * FastDFS exception.
 *
 * @author Rambo Yang
 */
public class FastDFSException extends RuntimeException {
    private static final long serialVersionUID = -8274679139300220262L;

    public FastDFSException() {}

    public FastDFSException(String message) {
        super(message);
    }

    public FastDFSException(String message, Throwable cause) {
        super(message, cause);
    }

    public FastDFSException(Throwable cause) {
        super(cause);
    }
}
