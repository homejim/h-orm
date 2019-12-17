package com.homejim.framework.token;

import com.homejim.framework.sql.mapping.SqlSegment;

/**
 * @author Clinton Begin
 */
public interface TokenHandler {
  SqlSegment handleToken(String content);
}