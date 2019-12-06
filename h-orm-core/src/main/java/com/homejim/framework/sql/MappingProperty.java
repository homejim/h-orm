package com.homejim.framework.sql;

import lombok.Getter;
import lombok.Setter;

/**
 * 映射属性
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-03 19:23
 */
@Setter
@Getter
public class MappingProperty {

    /**
     * 对应的 Sql 列
     */
    private String column;

    /**
     * 对应的字段
     */
    private String field;

    private Boolean insert = true;

    private Boolean update = true;

    private Boolean delete = true;

    private Boolean select = true;
}