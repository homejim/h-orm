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

    SELECT("select","查询"),
    UPDATE("select","更新"),
    DELETE("select","删除"),
    INSERT("select", "插入");

    private String code;
    private String desc;
}