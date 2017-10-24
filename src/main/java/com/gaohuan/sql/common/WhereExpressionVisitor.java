package com.gaohuan.sql.common;

import com.gaohuan.utils.Constants;
import com.gaohuan.utils.MysqlAesUtils;
import com.gaohuan.vo.ParamInfo;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * where表达式处理类
 * <p>
 * Created by gaohuan on 2017/10/23.
 */
public class WhereExpressionVisitor extends ExpressionVisitorAdapter {

    private Set<Table> tablesSet;

    private List<ParamInfo> paramInfoList;

    public WhereExpressionVisitor(Set<Table> tablesSet, List<ParamInfo> paramInfoList) {
        this.tablesSet = tablesSet;
        this.paramInfoList = paramInfoList;

    }

    public WhereExpressionVisitor(Set<Table> tablesSet) {
        this.tablesSet = tablesSet;

    }

    @Override
    public void visit(EqualsTo expr) {
        processExpression(expr);
    }

    @Override
    public void visit(GreaterThan expr) {
        processExpression(expr);
    }

    @Override
    public void visit(GreaterThanEquals expr) {
        processExpression(expr);
    }

    @Override
    public void visit(MinorThan expr) {
        processExpression(expr);
    }

    @Override
    public void visit(MinorThanEquals expr) {
        processExpression(expr);
    }

    @Override
    public void visit(NotEqualsTo expr) {
        processExpression(expr);
    }

    /**
     * 处理表达式
     * <p> 匹配待加密参数信息
     *
     * @param binaryExpression
     */
    private void processExpression(BinaryExpression binaryExpression) {
        if (binaryExpression.getLeftExpression() instanceof Column
                && binaryExpression.getRightExpression() instanceof JdbcParameter) {
            Column column = (Column) binaryExpression.getLeftExpression();
            JdbcParameter jdbcParameter = (JdbcParameter) binaryExpression.getRightExpression();
            String tableName = Commons.tableName(tablesSet, column.getTable().getName());
            List<String> columnList = Constants.TABLE_TO_COLUMN.get(tableName.toUpperCase());
            if (CollectionUtils.isNotEmpty(columnList) && columnList.contains(column.getColumnName())) {
                if (paramInfoList != null) {
                    paramInfoList.add(new ParamInfo(tableName, jdbcParameter.getIndex(), column.getColumnName()));
                }
            }
        } else if (binaryExpression.getLeftExpression() instanceof Column
                && binaryExpression.getRightExpression() instanceof StringValue) {
            Column column = (Column) binaryExpression.getLeftExpression();
            StringValue stringValue = (StringValue) binaryExpression.getRightExpression();
            String tableName = Commons.tableName(tablesSet, column.getTable().getName());
            List<String> columnList = Constants.TABLE_TO_COLUMN.get(tableName.toUpperCase());
            if (CollectionUtils.isNotEmpty(columnList) && columnList.contains(column.getColumnName())) {
                stringValue.setValue(MysqlAesUtils.encrypt(stringValue.getValue(), Constants.MYSQL_SECRET_KEY));
            }
        }


    }

}
