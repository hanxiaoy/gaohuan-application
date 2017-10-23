package com.gaohuan.vo;

import java.util.List;

/**
 * Created by gaohuan on 2017/10/23.
 */
public class ProcessedInfo {
    private String sql;
    private List<ParamInfo> paramInfoList;

    public ProcessedInfo(String sql, List<ParamInfo> paramInfoList) {
        this.sql = sql;
        this.paramInfoList = paramInfoList;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParamInfo> getParamInfoList() {
        return paramInfoList;
    }

    public void setParamInfoList(List<ParamInfo> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }
}
