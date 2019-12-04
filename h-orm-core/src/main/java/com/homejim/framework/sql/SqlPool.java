package com.homejim.framework.sql;

import com.homejim.framework.sql.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * sql 池
 *
 * @author hejiajin hejiajin@3vjia.com
 * @since 2019-12-04 12:38
 */
public class SqlPool {

    // 存储 sql 对象的 map. key 是类的全限定名。 value 对应其产生的 statements
    private static Map<String, MappedStatement> statements = new HashMap<>();


}