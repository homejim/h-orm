package com.homejim.framework.session;

import com.homejim.framework.sql.MappingProperty;
import com.homejim.framework.sql.SqlEntity;
import com.homejim.framework.sql.SqlPool;
import com.homejim.framework.sql.SqlTypeEnum;
import com.homejim.framework.sql.mapping.MappedStatement;
import com.homejim.framework.sql.mapping.SqlSegment;
import com.homejim.framework.sql.mapping.StatementContext;
import org.springframework.util.StringUtils;

import java.sql.*;
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
            }else {
                sql.append(" ").append(segment.getParsedSql());
            }
        }
        statementContext.setParams(finalParams);
        statementContext.setPreparedSql(sql.toString());

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/horm_sample", "root", "123456");
            preparedStatement = connection.prepareStatement(statementContext.getPreparedSql());

            for (int i = 0; i < statementContext.getParams().size(); i++) {
                preparedStatement.setObject(i + 1, statementContext.getParams().get(i));
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("user_name");
                System.out.println(username);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //第六步：倒叙释放资源resultSet-》preparedStatement-》connection
            try {
                if (resultSet!=null && !resultSet.isClosed()){
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(preparedStatement!=null &&
                        !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(connection!=null && connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;

    }
}
