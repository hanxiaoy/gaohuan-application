package com.gaohuan.sql.handler;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.gaohuan.sql.common.PrepareStatementVisitor;
import com.gaohuan.sql.common.RebuildStatementVisitor;
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

    /**
     * 处理预编译参数
     *
     * @param preparedStatement
     * @return
     * @throws SQLException
     */
    public PreparedStatementProxy processPrepareStatement(PreparedStatementProxy preparedStatement) throws SQLException {
        ProcessedInfo processedInfo = internalProcess(preparedStatement.getSql(), preparedStatement.getConnectionProxy(), Type.PREPARE);
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

    /**
     * 处理SQL
     *
     * @param statement
     * @param sql
     * @return
     * @throws SQLException
     */
    public String processSql(StatementProxy statement, String sql) throws SQLException {
        return internalProcess(sql, statement.getConnectionProxy(), Type.REBUILD).getSql();
    }

    /**
     * 处理SQL
     *
     * @param connectionProxy
     * @param sql
     * @return
     */
    public String processSql(ConnectionProxy connectionProxy, String sql) {
        return internalProcess(sql, connectionProxy, Type.REBUILD).getSql();
    }


    /**
     * 解析处理SQL
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
            if (type == Type.PREPARE) {
                statement.accept(new PrepareStatementVisitor(tableSet, paramInfoList, connection));
            } else if (type == Type.REBUILD) {
                statement.accept(new RebuildStatementVisitor(tableSet, connection));
            }
            return new ProcessedInfo(statement.toString(), paramInfoList);
        } catch (JSQLParserException e) {
            logger.error("sql解析失败", e);
        }
        return new ProcessedInfo(sql, Lists.newArrayList());
    }

    /**
     * 语句处理类型
     */
    private enum Type {
        /**
         * 重新构造sql
         */
        REBUILD,
        /**
         * 处理预编译参数
         */
        PREPARE
    }

}
