package com.nowander.common.database.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 可以用于将 集合 自动转为 JSON 格式存在数据库中
 * 使用时直接配合MybatisPlus
 * @author wang tengkun
 * @date 2022/3/4
 */
public class JsonSetTypeHandler extends BaseTypeHandler<Set<?>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Set<?> objects, JdbcType jdbcType) throws SQLException {
        try {
            String s = objectMapper.writeValueAsString(objects);
            preparedStatement.setString(i, s);
        } catch (JsonProcessingException e) {
            throw new SQLException("参数转换失败，无法将Set：'" + objects.toString() + "' 转换为JSON类型");
        }
    }

    @Override
    public Set<?> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return readJson(resultSet.getString(columnName));
    }

    @Override
    public Set<?> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return readJson(resultSet.getString(columnIndex));
    }

    @Override
    public Set<?> getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return readJson(callableStatement.getString(columnIndex));
    }

    private Set<?> readJson(String json) throws SQLException {
        if (json == null) {
            return new HashSet<>(0);
        }
        try {
            return objectMapper.readValue(json, Set.class);
        } catch (JsonProcessingException e) {
            throw new SQLException("参数转换失败，无法将JSON数据：'" + json + "' 转换为Set数据");
        }
    }
}
