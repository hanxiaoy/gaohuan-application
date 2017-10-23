package com.gaohuan.sql.common;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.gaohuan.utils.Constants;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 转换select项到对应的解密格式
 * <p>
 * Created by gaohuan on 2017/10/23.
 */
public class CustomSelectItemVisitor extends SelectItemVisitorAdapter {

    private Set<Table> tablesSet;

    private List<SelectItem> itemList;

    private ConnectionProxy connection;


    public CustomSelectItemVisitor(Set<Table> tablesSet, List<SelectItem> itemList, ConnectionProxy connection) {
        this.tablesSet = tablesSet;
        this.itemList = itemList;
        this.connection = connection;

    }

    @Override
    public void visit(AllColumns columns) {
        boolean mark = false;
        Iterator<Table> iterator = tablesSet.iterator();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            List<String> decryptColumns = Constants.TABLE_TO_COLUMN.get(table.getName().toUpperCase());
            if (CollectionUtils.isNotEmpty(decryptColumns)) {
                List<String> columnList = Commons.columns(connection, table.getName());
                if (CollectionUtils.isNotEmpty(columnList)) {
                    mark = true;
                    for (String columnName : columnList) {
                        if (decryptColumns.contains(columnName)) {
                            itemList.add(new SelectExpressionItem(buildExpression(table, columnName)));
                        } else {
                            itemList.add(new SelectExpressionItem(new Column(new Table(table.getAlias().getName()), columnName)));
                        }
                    }
                }
            }
        }
        if (!mark) {
            itemList.add(columns);
        }
    }

    @Override
    public void visit(SelectExpressionItem item) {
        boolean mark = false;
        if (item.getExpression() instanceof Column) {
            Column column = (Column) item.getExpression();
            String tableName = Commons.tableName(tablesSet, column.getTable().getName());
            List<String> decryptColumns = Constants.TABLE_TO_COLUMN.get(tableName.toUpperCase());
            if (CollectionUtils.isNotEmpty(decryptColumns) && decryptColumns.contains(column.getColumnName())) {
                SelectExpressionItem newItem = new SelectExpressionItem(buildExpression(Commons.table(tablesSet, column.getTable().getName()), column.getColumnName()));
                newItem.setAlias(item.getAlias());
                itemList.add(newItem);
                mark = true;
            }
        }
        if (!mark) {
            itemList.add(item);
        }
    }

    /**
     * 构建一个函数表达式
     *
     * @param table
     * @param columnName
     * @return
     */
    private Function buildExpression(Table table, String columnName) {
        Function fromBase64 = new Function();
        fromBase64.setName("from_base64");
        fromBase64.setParameters(new ExpressionList(Arrays.asList(new Column(new Table(table.getAlias().getName()), columnName))));
        Function decryptFunction = new Function();
        decryptFunction.setName("AES_DECRYPT");
        decryptFunction.setParameters(new ExpressionList(Arrays.asList(fromBase64, new StringValue(Constants.MYSQL_SECRET_KEY))));
        return decryptFunction;
    }

}