package com.homejim.framework.reflection.property;

import java.util.Iterator;

/**
 * 实现了迭代器接口
 */
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {
    // 当前表达式的名称
    private String name;
    // 当前表达式的索引名
    private final String indexedName;
    // 索引下标
    private String index;
    // 子表达式
    private final String children;

    /**
     * 构造函数
     *
     * @param fullname 需要解析的表达式
     */
    public PropertyTokenizer(String fullname) {
        // 查找 "." 的位置
        int delim = fullname.indexOf('.');
        if (delim > -1) {
            // 初始化 name
            name = fullname.substring(0, delim);
            // 初始化 children
            children = fullname.substring(delim + 1);
        } else {
            name = fullname;
            children = null;
        }
        // 初始化 indexedName
        indexedName = name;
        delim = name.indexOf('[');
        if (delim > -1) {
            // 初始化 index
            index = name.substring(delim + 1, name.length() - 1);
            name = name.substring(0, delim);
        }
    }

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }

    public String getIndexedName() {
        return indexedName;
    }

    public String getChildren() {
        return children;
    }

    @Override
    public boolean hasNext() {
        return children != null;
    }

    /**
     * 通过该方法进行迭代
     *
     * @return
     */
    @Override
    public PropertyTokenizer next() {
        return new PropertyTokenizer(children);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
    }
}