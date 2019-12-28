package com.homejim.framework.sql;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sql实体类
 *
 * @author homejim
 * @since 2019-12-03 19:22
 */
@Setter
@Getter
public class SqlEntity {
    /**
     * 映射字段
     */
    private List<MappingProperty> properties;

    private Map<String, String> columnFileMap = new HashMap<>();

    /**
     * 不更新的字段
     */
    private List<MappingProperty> notUpdateProperties = new ArrayList<>();

    /**
     * 不插入的字段
     */
    private List<MappingProperty> notInsertProperties = new ArrayList<>();

    /**
     * 不查询的字段
     */
    private List<MappingProperty> notSelectProperties = new ArrayList<>();

    /**
     * 主键
     */
    private MappingProperty primaryKey;

    /**
     * 表名
     */
    private String table;

    /**
     * 类全限定名
     */
    private String classFullName;

}