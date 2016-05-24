package com.lxz.fdfs.support;

/**
 * Created by xiaolezheng on 16/5/21.
 */
public class FsException extends RuntimeException{
    public FsException(String message) {
        super(message);
    }

    public FsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FsException(Throwable cause) {
        super(cause);
    }
}
