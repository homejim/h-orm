package com.homejim.framework.io;

import java.io.InputStream;
import java.net.URL;

/**
 * A class to wrap access to multiple class loaders making them work as one
 *
 * @author Clinton Begin
 */
public class ClassLoaderWrapper {

    // 默认的类加载器
    ClassLoader defaultClassLoader;
    // System ClassLoader
    ClassLoader systemClassLoader;

    ClassLoaderWrapper() {
        try {
            // 给 systemClassLoader 赋值
            systemClassLoader = ClassLoader.getSystemClassLoader();
        } catch (SecurityException ignored) {
            // AccessControlException on Google App Engine
        }
    }

    /**
     * Get a resource as a URL using the current class path
     *
     * @param resource - the resource to locate
     * @return the resource or null
     */
    public URL getResourceAsURL(String resource) {
        return getResourceAsURL(resource, getClassLoaders(null));
    }

    /**
     * Get a resource from the classpath, starting with a specific class loader
     *
     * @param resource    - the resource to find
     * @param classLoader - the first classloader to try
     * @return the stream or null
     */
    public URL getResourceAsURL(String resource, ClassLoader classLoader) {
        return getResourceAsURL(resource, getClassLoaders(classLoader));
    }

    /**
     * Get a resource from the classpath
     *
     * @param resource - the resource to find
     * @return the stream or null
     */
    public InputStream getResourceAsStream(String resource) {
        return getResourceAsStream(resource, getClassLoaders(null));
    }

    /**
     * Get a resource from the classpath, starting with a specific class loader
     *
     * @param resource    - the resource to find
     * @param classLoader - the first class loader to try
     * @return the stream or null
     */
    public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
        return getResourceAsStream(resource, getClassLoaders(classLoader));
    }

    /**
     * Find a class on the classpath (or die trying)
     *
     * @param name - the class to look for
     * @return - the class
     * @throws ClassNotFoundException Duh.
     */
    public Class<?> classForName(String name) throws ClassNotFoundException {
        return classForName(name, getClassLoaders(null));
    }

    /**
     * Find a class on the classpath, starting with a specific classloader (or die trying)
     *
     * @param name        - the class to look for
     * @param classLoader - the first classloader to try
     * @return - the class
     * @throws ClassNotFoundException Duh.
     */
    public Class<?> classForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
        return classForName(name, getClassLoaders(classLoader));
    }

    /**
     * Try to get a resource from a group of classloaders
     * <p>
     * 尝试从一组类加载器中获取相应的资源
     *
     * @param resource    - the resource to get
     * @param classLoader - the classloaders to examine
     * @return the resource or null
     */
    InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
        // 遍历
        for (ClassLoader cl : classLoader) {
            if (null != cl) {

                // try to find the resource as passed
                InputStream returnValue = cl.getResourceAsStream(resource);

                // now, some class loaders want this leading "/", so we'll add it and try again if we didn't find the resource
                // 类似， 有些类加载器前面需要加 "/"
                if (null == returnValue) {
                    returnValue = cl.getResourceAsStream("/" + resource);
                }

                if (null != returnValue) {
                    return returnValue;
                }
            }
        }
        return null;
    }

    /**
     * Get a resource as a URL using the current class path
     *
     * @param resource    - the resource to locate
     * @param classLoader - the class loaders to examine
     * @return the resource or null
     */
    URL getResourceAsURL(String resource, ClassLoader[] classLoader) {

        URL url;

        // 遍历类加载器数组
        for (ClassLoader cl : classLoader) {

            if (null != cl) {

                // look for the resource as passed in...
                // 查找传入的资源
                url = cl.getResource(resource);

                // ...but some class loaders want this leading "/", so we'll add it
                // and try again if we didn't find the resource
                // 部分类加载器以 "/" 开始， 因此找不到的话再加 "/" 再试一次
                if (null == url) {
                    url = cl.getResource("/" + resource);
                }

                // "It's always in the last place I look for it!"
                // ... because only an idiot would keep looking for it after finding it, so stop looking already.
                // 找到就返回
                if (null != url) {
                    return url;
                }

            }

        }

        // didn't find it anywhere.
        return null;

    }

    /**
     * Attempt to load a class from a group of classloaders
     * 根据类名， 从类加载器中获取类 Class 对象
     *
     * @param name        - the class to load
     * @param classLoader - the group of classloaders to examine
     * @return the class
     * @throws ClassNotFoundException - Remember the wisdom of Judge Smails: Well, the world needs ditch diggers, too.
     */
    Class<?> classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {

        // 遍历
        for (ClassLoader cl : classLoader) {

            if (null != cl) {

                try {
                    //  forName 这个方法应该很熟悉啦
                    Class<?> c = Class.forName(name, true, cl);
                    // 找到则返回
                    if (null != c) {
                        return c;
                    }

                } catch (ClassNotFoundException e) {
                    // we'll ignore this until all classloaders fail to locate the class
                }

            }

        }

        throw new ClassNotFoundException("Cannot find class: " + name);

    }

    /**
     * 类加载器数组， 里面指明了类加载器的使用顺序
     *
     * @param classLoader
     * @return
     */
    ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{
                classLoader,
                defaultClassLoader,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                systemClassLoader};
    }

}
