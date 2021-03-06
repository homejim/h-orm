package com.homejim.framework.token;

import com.homejim.framework.sql.mapping.SqlSegment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author homejim
 * @description SQL 片段解析器
 * @create: 2019-12-16 23:52
 */
public class SegmentTokenHandler implements TokenHandler {

    private Pattern PARAM_PATTERN = Pattern.compile("#([a-zA-Z0-9_]+)#");

    private Pattern FLAG_PATTERN = Pattern.compile("@([a-zA-Z0-9_]+)@");

    @Override
    public SqlSegment handleToken(String content) {
        SqlSegment sqlSegment = new SqlSegment();
        sqlSegment.setParsedSql(content);
        sqlSegment.setSegment(content);
        segmentPattern(sqlSegment);
        findParam(sqlSegment);
        findNotPutParam(sqlSegment);
        return sqlSegment;
    }

    private void segmentPattern(SqlSegment sqlSegment) {
        String segment = sqlSegment.getSegment().trim();
        if (segment.startsWith("??")) {
            sqlSegment.setParsedSql(segment.substring(2).trim());
            sqlSegment.setCheckIfExist(false);
        } else if (segment.startsWith("?")) {
            sqlSegment.setParsedSql(segment.substring(1).trim());
            sqlSegment.setCheckIfExist(true);
        }
    }

    private void findParam(SqlSegment sqlSegment) {
        Matcher paramMatcher = PARAM_PATTERN.matcher(sqlSegment.getParsedSql());
        if (paramMatcher.find()) {
            sqlSegment.setParam(paramMatcher.group(1));
            sqlSegment.setPutParam(true);
            sqlSegment.setParsedSql(sqlSegment.getParsedSql().replace("#" + sqlSegment.getParam() + "#", "?"));
        }
    }

    private void findNotPutParam(SqlSegment sqlSegment) {
        Matcher paramMatcher = FLAG_PATTERN.matcher(sqlSegment.getParsedSql());
        if (paramMatcher.find()) {
            sqlSegment.setParam(paramMatcher.group(1));
            sqlSegment.setPutParam(false);
            sqlSegment.setParsedSql(sqlSegment.getParsedSql().replace("@" + sqlSegment.getParam() + "@", ""));
        }
    }
}
