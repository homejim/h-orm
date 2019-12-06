package com.homejim.framework.sql.parse;

import com.homejim.framework.annotation.Column;
import com.homejim.framework.annotation.Id;
import com.homejim.framework.annotation.Table;
import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlFactory;
import com.homejim.framework.sql.mapping.MappedStatement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description 实体类解析器
 * @author homejim
 * @create: 2019-12-03 22:57
 */
public class EntitySqlParser implements SqlParser {

    private Set<Class<?>> classes;

    public EntitySqlParser(Set<Class<?>> classes) {
        this.classes = classes;
    }
    public void parse() {
        if (classes == null) {
            return;
        }
        for (Class aClass : classes) {
            SqlEntity sqlEntity = parse(aClass);
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setSqlEntity(sqlEntity);
            SqlFactory.addSql(sqlEntity.getClassFullName(), mappedStatement);
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
            } else {
                mappings.add(mappingProperty);
            }
        }
        sqlEntity.setProperties(mappings);

        return sqlEntity;
    }
}
