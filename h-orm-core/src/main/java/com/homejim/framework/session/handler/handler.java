package com.homejim.framework.session.handler;

/**
 * 处理器接口
 *
 * @author homejim
 * @since 2019-12-30 12:33
 */
public interface handler<T, S> {
    T handle(S context);
}
