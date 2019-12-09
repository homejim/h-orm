package com.homejim.framework.sql.mapping;

import lombok.Getter;
import lombok.Setter;

/**
 * @author homejim
 * @description
 * @create: 2019-12-10 00:06
 */
@Setter
@Getter
public class SqlSegment {

    /**
     * sql 片段
     */
    private String segment;

    /**
     * 是否进行非空校验
     */
    private boolean checkIfExist;

    /**
     * 对应的参数名
     */
    private String param;

}
