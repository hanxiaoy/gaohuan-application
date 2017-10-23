package com.gaohuan.sql.common;

import net.sf.jsqlparser.schema.Table;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * Created by gaohuan on 2017/10/23.
 */
public class TableName {
    /**
     * 根据表名或别名查找对应表名
     *
     * @param tableSet
     * @param tableOrAlias
     * @return
     */
    public static String get(Set<Table> tableSet, String tableOrAlias) {
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
}
