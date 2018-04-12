package com.learn.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hailong on 2018/4/11.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)//此处指定了作用到的JdbcType，此处如果不用注解, 那么，就可以在配置文件中通过"JdbcType"属性指定
@MappedTypes(String.class)//此处如果不用注解, 那么，就可以在配置文件中通过"java"属性指定
public class LearnTypeHandler extends BaseTypeHandler {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,"learnTypehandler");
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        //
        return rs.getString(columnName)==null? "default":rs.getString(columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }
}
