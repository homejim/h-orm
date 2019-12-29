package com.homejim.framework.sql.parse;

import com.homejim.framework.annotation.Column;
import com.homejim.framework.annotation.Id;
import com.homejim.framework.annotation.Table;
import com.homejim.framework.reflection.DefaultReflectorFactory;
import com.homejim.framework.reflection.Reflector;
import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlPool;
import com.homejim.framework.sql.generator.DeleteSqlGenerator;
import com.homejim.framework.sql.generator.SelectSqlGenerator;
import com.homejim.framework.sql.generator.SqlGenerator;
import com.homejim.framework.sql.generator.UpdateSqlGenerator;
import com.homejim.framework.sql.mapping.MappedStatement;
import com.homejim.framework.sql.mapping.SqlSegment;
import com.homejim.framework.token.GenericTokenParser;
import com.homejim.framework.token.SegmentTokenHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author homejim
 * @description 实体类解析器
 * @create: 2019-12-03 22:57
 */
public class EntitySqlParser implements SqlParser {

    private Set<Class<?>> classes;

    private List<SqlGenerator> generators = Arrays.asList(
            SelectSqlGenerator.INSTANCE,
            DeleteSqlGenerator.INSTANCE,
            UpdateSqlGenerator.INSTANCE);
    /**
     * sql 符号解析器
     */
    private GenericTokenParser tokenParser = new GenericTokenParser("{", "}", new SegmentTokenHandler());

    public EntitySqlParser(Set<Class<?>> classes) {
        this.classes = classes;
    }

    public void parse() {
        if (classes == null) {
            return;
        }
        for (Class aClass : classes) {
            Reflector reflector = DefaultReflectorFactory.INSTANCE.findForClass(aClass);
            SqlEntity sqlEntity = parse(aClass);

            for (SqlGenerator generator : generators) {
                MappedStatement mappedStatement = new MappedStatement();
                List<SqlSegment> sqlSegments = tokenParser.parse(generator.generate(sqlEntity));
                mappedStatement.setSegments(sqlSegments);
                mappedStatement.setSqlEntity(sqlEntity);
                mappedStatement.setReflector(reflector);
                SqlPool.addSql(SqlPool.sqlKey(sqlEntity.getClassFullName(), "mysql", generator.getSqlType()), mappedStatement);
            }
        }
    }

    /**
     * 解析类实体
     *
     * @param clazz
     * @return
     */
    private SqlEntity parse(Class clazz) {
        SqlEntity sqlEntity = new SqlEntity();
        List<MappingProperty> mappings = new ArrayList<MappingProperty>();

        Table table = (Table) clazz.getAnnotation(Table.class);
        String value = table.value();
        sqlEntity.setTable(value);
        sqlEntity.setClassFullName(clazz.getName());

        // 获取所有的属性字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            MappingProperty mappingProperty = new MappingProperty();
            mappingProperty.setField(field.getName());

            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                mappingProperty.setColumn(field.getName());
            } else {
                mappingProperty.setColumn(column.value());
            }

            // Id 处理
            if (field.isAnnotationPresent(Id.class)) {
                if (sqlEntity.getPrimaryKey() != null) {
                    throw new RuntimeException("同一个类中不能存在两个 Id");
                }
                sqlEntity.setPrimaryKey(mappingProperty);
            }
            mappings.add(mappingProperty);
            sqlEntity.getColumnFileMap().put(mappingProperty.getColumn(), mappingProperty.getField());
        }
        sqlEntity.setProperties(mappings);

        return sqlEntity;
    }
}
