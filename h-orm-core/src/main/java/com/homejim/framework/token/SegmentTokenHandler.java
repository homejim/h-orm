package com.homejim.framework.token;

import com.homejim.framework.sql.mapping.SqlSegment;

/**
 * @author homejim
 * @description SQL 片段解析器
 * @create: 2019-12-16 23:52
 */
public class SegmentTokenHandler implements TokenHandler{

    @Override
    public SqlSegment handleToken(String content) {
        SqlSegment sqlSegment = new SqlSegment();
        sqlSegment.setParsedSql(content);
        sqlSegment.setSegment(content);
        return sqlSegment;
    }

}
