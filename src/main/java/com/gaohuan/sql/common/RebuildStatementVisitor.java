package com.gaohuan.sql.common;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.update.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 重新构建sql语句类
 * <p>
 * Created by gaohuan on 2017/10/23.
 */
public class RebuildStatementVisitor extends StatementVisitorAdapter {
    /**
     * 表信息集合
     */
    private Set<Table> tableSet;

    /**
     * 连接对象
     */
    private ConnectionProxy connection;

    public RebuildStatementVisitor(Set<Table> tablesSet, ConnectionProxy connection) {
        this.tableSet = tablesSet;
        this.connection = connection;
    }

    @Override
    public void visit(Select select) {
        select.getSelectBody().accept(new SelectVisitorAdapter() {
            @Override
            public void visit(PlainSelect plainSelect) {
                List<SelectItem> itemList = new ArrayList<SelectItem>();
                for (SelectItem selectItem : plainSelect.getSelectItems()) {
                    selectItem.accept(new CustomSelectItemVisitor(tableSet, itemList, connection));
                }
                //设置处理后新的itemList
                plainSelect.setSelectItems(itemList);
                plainSelect.getWhere().accept(new WhereExpressionVisitor(tableSet));

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
        delete.getWhere().accept(new WhereExpressionVisitor(tableSet));
    }

    /**
     * 解析update语句
     *
     * @param update
     */
    @Override
    public void visit(Update update) {
        //todo 处理 set a=? or set a='1'
        update.getWhere().accept(new WhereExpressionVisitor(tableSet));
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

