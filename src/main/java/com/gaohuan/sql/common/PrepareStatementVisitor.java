package com.gaohuan.sql.common;

import java.util.List;
import java.util.Set;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.gaohuan.utils.Constants;
import com.gaohuan.utils.MysqlAesUtils;
import com.gaohuan.vo.ParamInfo;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 预编译参数处理类
 * <p>
 * Created by gaohuan on 2017/10/23.
 */
public class PrepareStatementVisitor extends StatementVisitorAdapter {
    /**
     * sql包含表集合
     */
    private Set<Table> tableSet;
    /**
     * 返回参数
     */
    private List<ParamInfo> paramInfoList;
    /**
     * 连接对象
     */
    private ConnectionProxy connection;

    public PrepareStatementVisitor(Set<Table> tablesSet, List<ParamInfo> paramInfoList, ConnectionProxy connection) {
        this.tableSet = tablesSet;
        this.paramInfoList = paramInfoList;
        this.connection = connection;
    }

    /**
     * 解析select语句
     *
     * @param select
     */
    @Override
    public void visit(Select select) {
        select.getSelectBody().accept(new SelectVisitorAdapter() {
            @Override
            public void visit(PlainSelect plainSelect) {
                //处理where条件
                plainSelect.getWhere().accept(new WhereExpressionVisitor(tableSet, paramInfoList));

            }
        });
    }

    /**
     * 解析delete语句
     *
     * @param delete
     */
    @Override
    public void visit(Delete delete) {
        delete.getWhere().accept(new WhereExpressionVisitor(tableSet, paramInfoList));
    }

    /**
     * 解析update语句
     *
     * @param update
     */
    @Override
    public void visit(Update update) {
        //todo 处理 set a=? or set a='1'
        List<Column> colList = update.getColumns();
        List<Expression> expList = update.getExpressions();
        Column col;
        Expression exp;
        for(int i = 0; i< colList.size();i++){
            col = colList.get(i);
            String tableName = Commons.tableName(tableSet, col.getTable().getName());
            List<String> columnList = Constants.TABLE_TO_COLUMN.get(tableName.toUpperCase());
            if(columnList.contains(col.getColumnName())){
                exp = expList.get(i);
                if (exp instanceof JdbcParameter) {
                    JdbcParameter jdbc = (JdbcParameter) exp;
                    paramInfoList.add(new ParamInfo(tableName, jdbc.getIndex(), col.getColumnName()));
                }else if(exp instanceof StringValue){
                    StringValue str = (StringValue) exp;
                    str.setValue(MysqlAesUtils.encrypt(str.getValue(), Constants.MYSQL_SECRET_KEY));
                }
            }
        }
        update.getWhere().accept(new WhereExpressionVisitor(tableSet, paramInfoList));
    }

    /**
     * 解析insert语句
     *
     * @param insert
     */
    @Override
    public void visit(Insert insert) {
        //TODO 处理插入操作
        List<Expression> iterable = ((ExpressionList)insert.getItemsList()).getExpressions();  // 值   ?
        List<Column> columns = insert.getColumns();  // 字段
        String tableName = insert.getTable().getName(); // 表
        List<String> columnsStr = null ;
        if(columns == null || CollectionUtils.isEmpty(columns)){
            columnsStr = Commons.columns(connection, tableName) ;
        } else {
            columnsStr = Lists.newArrayList();
            for(Column column : columns){
                columnsStr.add(column.getColumnName());
            }
        }
        for (int i =0;i<columnsStr.size(); i++) {
            String column = columnsStr.get(i) ;
            List<String> columnList = Constants.TABLE_TO_COLUMN.get(tableName.toUpperCase());  // 加密字段

            if(columnList.contains(column)){
                Expression expression = iterable.get(i);
                if (expression instanceof  JdbcParameter){
                    JdbcParameter jdbcParameter = (JdbcParameter) expression;
                    paramInfoList.add(new ParamInfo(tableName, jdbcParameter.getIndex(), MysqlAesUtils.encrypt(column , Constants.MYSQL_SECRET_KEY)));
                }
            }

        }

    }
}

