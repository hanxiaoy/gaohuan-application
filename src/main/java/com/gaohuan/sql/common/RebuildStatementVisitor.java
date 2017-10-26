package com.gaohuan.sql.common;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.gaohuan.utils.Constants;
import com.gaohuan.utils.MysqlAesUtils;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.collections.CollectionUtils;

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

    /**
     * 重新构造sql语句
     *
     * @param select
     */
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
        String tableName = insert.getTable().getName();
        List<Expression> iterable = ((ExpressionList)insert.getItemsList()).getExpressions();  // 值   ?
        List<Column> columns = insert.getColumns();  // 字段
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

            if( CollectionUtils.isNotEmpty(columnList) && columnList.contains(column)){
                Expression expression = iterable.get(i);
                if(expression instanceof StringValue){
                    StringValue str = (StringValue) expression;
                    str.setValue(MysqlAesUtils.encrypt(str.getValue(), Constants.MYSQL_SECRET_KEY));

                }
            }

        }
    }

}

