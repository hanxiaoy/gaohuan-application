package com.gaohuan.sql.common;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.gaohuan.vo.ParamInfo;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.update.Update;

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
        update.getWhere().accept(new WhereExpressionVisitor(tableSet, paramInfoList));
    }

    /**
     * 解析insert语句
     *
     * @param insert
     */
    @Override
    public void visit(Insert insert) {
        //todo 处理插入操作
    }
}

