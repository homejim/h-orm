package com.homejim.framework.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Sql 类型枚举（增删改查）
 *
 * @author homejim
 * @since 2019-12-03 12:51
 */
@AllArgsConstructor
@Getter
public enum SqlTypeEnum {

    SELECT("select", "查询"),
    UPDATE("update", "更新"),
    DELETE("delete", "删除"),
    INSERT("insert", "插入");

    private String code;
    private String desc;
}