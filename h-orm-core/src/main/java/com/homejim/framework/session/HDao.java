package com.homejim.framework.session;

import com.homejim.framework.datasource.DataSourceFactory;
import com.homejim.framework.reflection.DefaultReflectorFactory;
import com.homejim.framework.reflection.Reflector;
import com.homejim.framework.reflection.involker.Invoker;
import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlPool;
import com.homejim.framework.sql.SqlTypeEnum;
import com.homejim.framework.sql.mapping.MappedStatement;
import com.homejim.framework.sql.mapping.SqlSegment;
import com.homejim.framework.sql.mapping.StatementContext;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author homejim
 * @description 全局访问层
 * @create: 2019-12-19 00:07
 */
public class HDao {

    @Setter
    private DataSourceFactory dataSourceFactory;

    public <T> T selectById(Class<T> tClass, Object id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Id is required!");
        }
        String selectSqlKey = SqlPool.sqlKey(tClass.getName(), "mysql", SqlTypeEnum.SELECT);
        MappedStatement mappedStatement = SqlPool.getSql(selectSqlKey);
        Map<String, Object> params = new HashMap<>();
        SqlEntity sqlEntity = mappedStatement.getSqlEntity();
        MappingProperty primaryKey = sqlEntity.getPrimaryKey();
        params.put(primaryKey.getField(), id);
        return queryEntity(tClass, mappedStatement, params);
    }

    public <T> T queryEntity(Class<T> tClass, MappedStatement mappedStatement, Map<String, Object> params) {
        StatementContext statementContext = new StatementContext();
        List<SqlSegment> segments = mappedStatement.getSegments();
        List<Object> finalParams = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        for (SqlSegment segment : segments) {
            if (segment.isCheckIfExist()) {
                Object param = params.get(segment.getParam());
                if (param != null) {
                    sql.append(" ").append(segment.getParsedSql());
                    finalParams.add(param);
                }
            } else {
                sql.append(" ").append(segment.getParsedSql());
            }
        }
        statementContext.setParams(finalParams);
        statementContext.setPreparedSql(sql.toString());

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSourceFactory.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(statementContext.getPreparedSql());

            for (int i = 0; i < statementContext.getParams().size(); i++) {
                preparedStatement.setObject(i + 1, statementContext.getParams().get(i));
            }
            resultSet = preparedStatement.executeQuery();
            T result = handleQueryResult(tClass, mappedStatement, resultSet);
            if (result != null) return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return null;

    }

    /**
     * 关闭连接
     *
     * @param connection
     * @param preparedStatement
     * @param resultSet
     */
    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        //第六步：倒叙释放资源resultSet-》preparedStatement-》connection
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null &&
                    !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null && connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理结果
     *
     * @param tClass
     * @param mappedStatement
     * @param resultSet
     * @param <T>
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private <T> T handleQueryResult(Class<T> tClass, MappedStatement mappedStatement, ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Reflector reflector = DefaultReflectorFactory.INSTANCE.findForClass(tClass);
        if (resultSet.next()) {
            Object result = reflector.getDefaultConstructor().newInstance();
            for (int i = 0; i < columnCount; i++) {
                String columnLabel = metaData.getColumnLabel(i + 1);
                String field = mappedStatement.getSqlEntity().getColumnFileMap().get(columnLabel);
                if (!StringUtils.isEmpty(field)) {
                    Invoker setInvoker = reflector.getSetInvoker(field);
                    Object value = resultSet.getObject(i + 1);
                    Object[] objects = new Object[1];
                    objects[0] = value;
                    setInvoker.invoke(result, objects);
                }
            }
            return (T) result;
        }
        return null;
    }
}
