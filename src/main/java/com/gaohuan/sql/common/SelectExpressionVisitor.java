package com.gaohuan.sql.common;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;

import java.util.Set;

/**
 * 转换select项到对应的解密格式
 *
 * Created by gaohuan on 2017/10/23.
 */
public class SelectExpressionVisitor extends SelectItemVisitorAdapter {

    private Set<Table> tablesSet;


    public SelectExpressionVisitor(Set<Table> tablesSet) {
        this.tablesSet = tablesSet;

    }

    @Override
    public void visit(AllColumns columns) {
        //todo * 转换成具体列(to_base64(aes_encrypt(*,*)))
        super.visit(columns);
    }

    @Override
    public void visit(SelectExpressionItem item) {
        //todo 判断是否需要加密解密的字段
        super.visit(item);
    }
}
