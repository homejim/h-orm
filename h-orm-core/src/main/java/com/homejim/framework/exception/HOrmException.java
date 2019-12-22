package com.homejim.framework.exception;


/**
 * @author homejim
 * @description 异常
 * @create: 2019-12-22 23:01
 */
public class HOrmException extends RuntimeException {
    public HOrmException() {
        super();
    }

    public HOrmException(String msg) {
        super(msg);
    }

    public HOrmException(String message, Throwable cause) {
        super(message, cause);
    }

    public HOrmException(Throwable cause) {
        super(cause);
    }

    protected HOrmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
