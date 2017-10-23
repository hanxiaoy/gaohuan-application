package com.gaohuan.sql.common;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.gaohuan.vo.ParamInfo;
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
 * Created by gaohuan on 2017/10/23.
 */
public class SelectStatementVisitor extends StatementVisitorAdapter {

    private Set<Table> tableSet;

    private List<ParamInfo> paramInfoList;

    private ConnectionProxy connection;

    public SelectStatementVisitor(Set<Table> tablesSet, List<ParamInfo> paramInfoList, ConnectionProxy connection) {
        this.tableSet = tablesSet;
        this.paramInfoList = paramInfoList;
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

