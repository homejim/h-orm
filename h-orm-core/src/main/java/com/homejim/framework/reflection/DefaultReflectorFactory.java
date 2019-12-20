package com.homejim.framework.reflection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * mybatis 提供的默认 ReflectorFactory 实现类
 */
public class DefaultReflectorFactory implements ReflectorFactory {
    // 是否缓存
    private boolean classCacheEnabled = true;
    // 使用 ConcurrentMap 缓存 Reflector 对象
    private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<>();

    public DefaultReflectorFactory() {
    }

    @Override
    public boolean isClassCacheEnabled() {
        return classCacheEnabled;
    }

    @Override
    public void setClassCacheEnabled(boolean classCacheEnabled) {
        this.classCacheEnabled = classCacheEnabled;
    }

    /**
     * 如果开启缓存， 则从缓存中取出 Reflector 对象
     * 否则创建一个新的
     *
     * @param type 需要创建 Reflector 对象对应的 Class
     * @return 相应的 Reflector 对象
     */
    @Override
    public Reflector findForClass(Class<?> type) {
        if (classCacheEnabled) {
            // synchronized (type) removed see issue #461
            return reflectorMap.computeIfAbsent(type, Reflector::new);
        } else {
            return new Reflector(type);
        }
    }

}
