package com.homejim.framework.exception;


/**
 * @author homejim
 * @description 异常
 * @create: 2019-12-22 23:01
 */
public class HormException extends RuntimeException {
    public HormException() {
        super();
    }

    public HormException(String msg) {
        super(msg);
    }

    public HormException(String message, Throwable cause) {
        super(message, cause);
    }

    public HormException(Throwable cause) {
        super(cause);
    }

    protected HormException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
