package com.gaohuan.sql.handler;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.gaohuan.sql.common.CustomStatementVisitor;
import com.gaohuan.sql.common.SelectStatementVisitor;
import com.gaohuan.sql.common.TablesFinder;
import com.gaohuan.utils.Constants;
import com.gaohuan.utils.MysqlAesUtils;
import com.gaohuan.vo.ParamInfo;
import com.gaohuan.vo.ProcessedInfo;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * 自定语句处理
 * Created by gaohuan on 2017/10/20.
 */
public class CustomStatementHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomStatementHandler.class);

    private CustomStatementHandler() {

    }

    public static CustomStatementHandler create() {
        return new CustomStatementHandler();
    }

    public PreparedStatementProxy processPrepareStatement(PreparedStatementProxy preparedStatement) throws SQLException {
        ProcessedInfo processedInfo = internalProcess(preparedStatement.getSql(), preparedStatement.getConnectionProxy(), Type.INFO);
        List<ParamInfo> paramInfoList = processedInfo.getParamInfoList();
        if (CollectionUtils.isNotEmpty(paramInfoList)) {
            for (ParamInfo paramInfo : paramInfoList) {
                int index = paramInfo.getParameterIndex();
                Object value = preparedStatement.getParameter(index - 1).getValue();
                if (value != null && value instanceof String) {
                    preparedStatement.setObject(index, MysqlAesUtils.encrypt((String) value, Constants.MYSQL_SECRET_KEY));
                }
            }
        }
        return preparedStatement;
    }

    public String processSql(StatementProxy statement, String sql) throws SQLException {
        return internalProcess(sql, statement.getConnectionProxy(), Type.SQL).getSql();
    }

    public String processSql(ConnectionProxy connectionProxy, String sql) {
        return internalProcess(sql, connectionProxy, Type.SQL).getSql();
    }


    /**
     * 解析sql语句
     *
     * @param sql
     * @return
     */
    private ProcessedInfo internalProcess(String sql, ConnectionProxy connection, Type type) {
        try {
            CCJSqlParserManager sqlParserManager = new CCJSqlParserManager();
            Statement statement = sqlParserManager.parse(new StringReader(sql));
            Set<Table> tableSet = TablesFinder.create().getTables(statement);
            List<ParamInfo> paramInfoList = Lists.newArrayList();
            if (type == Type.INFO) {
                statement.accept(new CustomStatementVisitor(tableSet, paramInfoList, connection));
            } else if (type == Type.SQL) {
                statement.accept(new SelectStatementVisitor(tableSet, paramInfoList, connection));
            }
            return new ProcessedInfo(statement.toString(), paramInfoList);
        } catch (JSQLParserException e) {
            logger.error("sql解析失败", e);
        }
        return new ProcessedInfo(sql, Lists.newArrayList());
    }

    private enum Type {
        SQL,
        INFO
    }

}
