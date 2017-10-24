package com.gaohuan.sql.common;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;

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

            }
        });
    }

}

