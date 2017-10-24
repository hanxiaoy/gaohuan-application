package com.gaohuan.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by gaohuan on 2017/10/19.
 */
@Service("jdbcService")
public class JdbcService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void doUpdate(String sql, List<Object> params) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                if (CollectionUtils.isNotEmpty(params)) {
                    for (int i = 0; i < params.size(); i++) {
                        preparedStatement.setObject(i + 1, params.get(i));
                    }
                }
                return preparedStatement;
            }
        });

    }

    public List<Map<String, Object>> doQuery(String sql, List<Object> params) {

        return jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                if (CollectionUtils.isNotEmpty(params)) {
                    for (int i = 0; i < params.size(); i++) {
                        preparedStatement.setObject(i + 1, params.get(i));
                    }
                }
                return preparedStatement;
            }
        }, new ColumnMapRowMapper());

    }


    public <T> List<T> doQuery(String sql, List<Object> params, Class<T> tClass) {

        return jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                if (CollectionUtils.isNotEmpty(params)) {
                    for (int i = 0; i < params.size(); i++) {
                        preparedStatement.setObject(i + 1, params.get(i));
                    }
                }
                return preparedStatement;
            }
        }, new BeanPropertyRowMapper<T>(tClass));

    }


}
