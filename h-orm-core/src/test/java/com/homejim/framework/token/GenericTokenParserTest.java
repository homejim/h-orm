package com.homejim.framework.token;

import com.homejim.framework.sql.mapping.SqlSegment;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GenericTokenParserTest {

    @Test
    public void test() {
        GenericTokenParser parser = new GenericTokenParser("{", "}", new SegmentTokenHandler());
        String sql = "select user_name from user where 1=1 {? and user_id = #userId#} limit 1";
        List<SqlSegment> parses = parser.parse(sql);
        Assert.assertEquals(parses.size(), 3);
        Assert.assertEquals("? and user_id = #userId#", parses.get(1).getSegment());
        Assert.assertEquals("and user_id = ?", parses.get(1).getParsedSql());
        Assert.assertEquals("limit 1", parses.get(2).getSegment());
        Assert.assertEquals("limit 1", parses.get(2).getParsedSql());
        Assert.assertEquals("userId", parses.get(1).getParam());
        Assert.assertTrue(parses.get(1).isCheckIfExist());
    }

    @Test
    public void shouldSkipSpaceTest() {
        GenericTokenParser parser = new GenericTokenParser("{", "}", new SegmentTokenHandler());
        String sql = "select * from user where 1=1 { ? and username = #username# }";
        List<SqlSegment> parses = parser.parse(sql);
        Assert.assertEquals(parses.size(), 2);
        Assert.assertEquals(" ? and username = #username# ", parses.get(1).getSegment());
        Assert.assertEquals("and username = ?", parses.get(1).getParsedSql());
        Assert.assertEquals("username", parses.get(1).getParam());
        Assert.assertTrue(parses.get(1).isCheckIfExist());
    }

    @Test
    public void shouldHasDoubleQuestionMarkTest() {
        GenericTokenParser parser = new GenericTokenParser("{", "}", new SegmentTokenHandler());
        String sql = "select user_name from user where 1=1 { ?? and user_id = #userId# }";
        List<SqlSegment> parses = parser.parse(sql);
        Assert.assertEquals(parses.size(), 2);
        Assert.assertEquals(" ?? and user_id = #userId# ", parses.get(1).getSegment());
        Assert.assertEquals("and user_id = ?", parses.get(1).getParsedSql());
        Assert.assertEquals("userId", parses.get(1).getParam());
        Assert.assertFalse(parses.get(1).isCheckIfExist());
    }

}