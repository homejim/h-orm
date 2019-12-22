package com.homejim.framework.exception;

/**
 * @author homejim
 * @description 数据源异常
 * @create: 2019-12-22 23:54
 */
public class HormDataSourceException extends HormException {
    public HormDataSourceException() {
        super();
    }

    public HormDataSourceException(String message) {
        super(message);
    }

    public HormDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public HormDataSourceException(Throwable cause) {
        super(cause);
    }

}
