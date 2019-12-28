package com.homejim.framework.token;

import com.homejim.framework.sql.mapping.SqlSegment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Clinton Begin
 */
public class GenericTokenParser {

    private final String openToken;
    private final String closeToken;
    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    public List<SqlSegment> parse(String text) {
        if (text == null || text.isEmpty()) {
            throw new RuntimeException("空字符串无法解析");
        }
        // search open token
        int start = text.indexOf(openToken, 0);
        if (start == -1) {
            SqlSegment sqlSegment = newCommonSqlSegment(text);
            return Collections.singletonList(sqlSegment);
        }
        List<SqlSegment> sqlSegments = new ArrayList<>();

        char[] src = text.toCharArray();
        int offset = 0;
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                SqlSegment sqlSegment = newCommonSqlSegment(new String(src, offset, start - offset - 1) + openToken);
                if (sqlSegment != null) {
                    sqlSegments.add(sqlSegment);
                }
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                // 处理 common 节点
                String s = new String(src, offset, start - offset);
                SqlSegment sqlSegment = newCommonSqlSegment(s);
                if (sqlSegment != null) {
                    sqlSegments.add(sqlSegment);
                }
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    sqlSegments.add(newCommonSqlSegment(new String(src, start, src.length - start)));
                    offset = src.length;
                } else {
                    sqlSegments.add(handler.handleToken(expression.toString()));
                    offset = end + closeToken.length();
                }
            }
            start = text.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            sqlSegments.add(newCommonSqlSegment(new String(src, offset, src.length - offset)));
        }
        return sqlSegments;
    }

    /**
     * 产生一般的 sql 片段（没有变量）
     *
     * @param text
     * @return
     */
    private SqlSegment newCommonSqlSegment(String text) {
        String trimText = text.trim();
        if (StringUtils.isEmpty(trimText)) {
            return null;
        }
        SqlSegment sqlSegment = new SqlSegment();
        sqlSegment.setSegment(trimText);
        sqlSegment.setParsedSql(trimText);
        sqlSegment.setCheckIfExist(false);
        sqlSegment.setParam(null);
        return sqlSegment;
    }
}