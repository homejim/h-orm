package com.homejim.framework.sql.parse;

import com.homejim.framework.entity.User;
import com.homejim.framework.sql.SqlPool;
import com.homejim.framework.sql.mapping.MappedStatement;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class EntitySqlParserTest {

    @Test
    public void parse() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(User.class);

        EntitySqlParser sqlParser = new EntitySqlParser(classes);
        sqlParser.parse();

        SqlPool.addSql("aa", new MappedStatement());
    }
}