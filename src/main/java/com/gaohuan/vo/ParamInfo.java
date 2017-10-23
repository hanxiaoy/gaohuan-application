package com.gaohuan.vo;

/**
 * Created by gaohuan on 2017/10/20.
 */
public class ParamInfo {
    private String tableName;
    private int parameterIndex;
    private String parameterName;

    public ParamInfo(String tableName, int parameterIndex, String parameterName) {
        this.tableName = tableName;
        this.parameterIndex = parameterIndex;
        this.parameterName = parameterName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public void setParameterIndex(int parameterIndex) {
        this.parameterIndex = parameterIndex;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
}
