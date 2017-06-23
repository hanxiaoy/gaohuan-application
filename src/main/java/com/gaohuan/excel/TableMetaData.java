package com.gaohuan.excel;

/**
 * Created by gaohuan on 2017/6/23.
 */
public class TableMetaData {


    private String columnName;

    private String type;

    private String remarks;

    public TableMetaData(String columnName, String type, String remarks) {
        this.columnName = columnName;
        this.type = type;
        this.remarks = remarks;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
