package com.gaohuan.sql.common;

import com.gaohuan.vo.ParamInfo;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.update.Update;

import java.util.List;
import java.util.Set;

/**
 * Created by gaohuan on 2017/10/23.
 */
public class CustomStatementVisitor extends StatementVisitorAdapter {

    private Set<Table> tableSet;

    private List<ParamInfo> paramInfoList;

    public CustomStatementVisitor(Set<Table> tablesSet, List<ParamInfo> paramInfoList) {
        this.tableSet = tablesSet;
        this.paramInfoList = paramInfoList;
    }

    @Override
    public void visit(Select select) {
        select.getSelectBody().accept(new SelectVisitorAdapter() {
            @Override
            public void visit(PlainSelect plainSelect) {
                plainSelect.getWhere().accept(new WhereExpressionVisitor(tableSet, paramInfoList));
                for (SelectItem selectItem : plainSelect.getSelectItems()) {
                    selectItem.accept(new SelectExpressionVisitor(tableSet));
                }
            }
        });
    }

    @Override
    public void visit(Delete delete) {
        delete.getWhere().accept(new WhereExpressionVisitor(tableSet, paramInfoList));
    }

    @Override
    public void visit(Update update) {
        //todo 处理 set a=? or set a='1'
        update.getWhere().accept(new WhereExpressionVisitor(tableSet, paramInfoList));
    }

    @Override
    public void visit(Insert insert) {
        //todo 处理插入操作
    }
}

