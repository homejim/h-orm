package com.homejim.framework.test.token;


import com.homejim.framework.sql.mapping.SqlSegment;
import com.homejim.framework.token.GenericTokenParser;
import com.homejim.framework.token.SegmentTokenHandler;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GenericTokenParserTest {

  @Test
  public void test() {
    GenericTokenParser parser = new GenericTokenParser("{", "}", new SegmentTokenHandler());
    String sql = "select user_name from user where 1=1 {? and user_id = #userId#}";
    List<SqlSegment> parses = parser.parse(sql);
    Assert.assertEquals(parses.size(), 2);
    Assert.assertEquals("? and user_id = #userId#", parses.get(1).getSegment());
  }

}