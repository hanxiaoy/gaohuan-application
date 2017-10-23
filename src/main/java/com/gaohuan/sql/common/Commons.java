package com.gaohuan.sql.common;

import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.schema.Table;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by gaohuan on 2017/10/23.
 */
public class Commons {
    private static final Logger logger = LoggerFactory.getLogger(Commons.class);

    /**
     * 根据表名或别名查找对应表名
     *
     * @param tableSet
     * @param tableOrAlias
     * @return
     */
    public static String tableName(Set<Table> tableSet, String tableOrAlias) {
        String defaultName = tableSet.iterator().next().getName();
        if (StringUtils.isEmpty(tableOrAlias)) {
            return defaultName;
        }
        for (Table table : tableSet) {
            if (table.getAlias().getName().equalsIgnoreCase(tableOrAlias)) {
                return table.getName();
            }
            if (table.getName().equalsIgnoreCase(tableOrAlias)) {
                return table.getName();
            }
        }
        return defaultName;
    }

    public static Table table(Set<Table> tableSet, String tableOrAlias) {
        Table defaultTable = tableSet.iterator().next();
        if (StringUtils.isEmpty(tableOrAlias)) {
            return defaultTable;
        }
        for (Table table : tableSet) {
            if (table.getAlias().getName().equalsIgnoreCase(tableOrAlias)) {
                return table;
            }
            if (table.getName().equalsIgnoreCase(tableOrAlias)) {
                return table;
            }
        }
        return defaultTable;
    }


    public static List<String> columns(ConnectionProxy connection, String tableName) {
        List<String> resultList = Lists.newArrayList();
        try {
            ResultSet resultSet = connection.getMetaData().getColumns(connection.getCatalog(), "%", tableName, "%");
            while (resultSet.next()) {
                resultList.add(resultSet.getString(4));//字段名
            }
        } catch (SQLException e) {
            logger.error("查询指定表的字段记录失败", e);
        }
        return resultList;
    }
}
