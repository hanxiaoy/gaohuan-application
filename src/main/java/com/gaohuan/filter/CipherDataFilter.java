package com.gaohuan.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.gaohuan.sql.handler.CustomStatementHandler;

import java.sql.SQLException;

/**
 * 对指定字段进行数据加密解密处理
 * <p>CipherDataFilter
 * <p>
 * Created by gaohuan on 2017/10/19.
 */
public class CipherDataFilter extends FilterEventAdapter {

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sql) throws SQLException {
        return super.connection_prepareStatement(chain, connection, CustomStatementHandler.create().processSql(connection, sql));
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sql, int autoGeneratedKeys) throws SQLException {
        return super.connection_prepareStatement(chain, connection, CustomStatementHandler.create().processSql(connection, sql), autoGeneratedKeys);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return super.connection_prepareStatement(chain, connection, CustomStatementHandler.create().processSql(connection, sql), resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return super.connection_prepareStatement(chain, connection, CustomStatementHandler.create().processSql(connection, sql), resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sql, int[] columnIndexes) throws SQLException {
        return super.connection_prepareStatement(chain, connection, CustomStatementHandler.create().processSql(connection, sql), columnIndexes);
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sql, String[] columnNames) throws SQLException {
        return super.connection_prepareStatement(chain, connection, CustomStatementHandler.create().processSql(connection, sql), columnNames);
    }

    @Override
    public boolean preparedStatement_execute(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        return super.preparedStatement_execute(chain, CustomStatementHandler.create().processPrepareStatement(statement));
    }

    @Override
    public int preparedStatement_executeUpdate(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        return super.preparedStatement_executeUpdate(chain, CustomStatementHandler.create().processPrepareStatement(statement));
    }

    @Override
    public ResultSetProxy preparedStatement_executeQuery(FilterChain chain, PreparedStatementProxy statement) throws SQLException {
        return super.preparedStatement_executeQuery(chain, CustomStatementHandler.create().processPrepareStatement(statement));
    }

    @Override
    public ResultSetProxy statement_executeQuery(FilterChain chain, StatementProxy statement, String sql) throws SQLException {
        return super.statement_executeQuery(chain, statement, CustomStatementHandler.create().processSql(statement, sql));
    }

    @Override
    public int statement_executeUpdate(FilterChain chain, StatementProxy statement, String sql) throws SQLException {
        return super.statement_executeUpdate(chain, statement, CustomStatementHandler.create().processSql(statement, sql));
    }

    @Override
    public int statement_executeUpdate(FilterChain chain, StatementProxy statement, String sql, int autoGeneratedKeys) throws SQLException {
        return super.statement_executeUpdate(chain, statement, CustomStatementHandler.create().processSql(statement, sql), autoGeneratedKeys);
    }

    @Override
    public int statement_executeUpdate(FilterChain chain, StatementProxy statement, String sql, int[] columnIndexes) throws SQLException {
        return super.statement_executeUpdate(chain, statement, CustomStatementHandler.create().processSql(statement, sql), columnIndexes);
    }

    @Override
    public int statement_executeUpdate(FilterChain chain, StatementProxy statement, String sql, String[] columnNames) throws SQLException {
        return super.statement_executeUpdate(chain, statement, CustomStatementHandler.create().processSql(statement, sql), columnNames);
    }
}
