package com.gaohuan.excel;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据表元信息生成excel
 */
public class ExcelExportMain {
    private static final String TABLE_NAME = "vault_user_repayment_log";
    private static final String SCHEMA_PATTERN = "sxs_vault";
    private static final String URL = "jdbc:mysql://123.56.207.230:9399/sxs_vault?characterEncoding=utf8&useSSL=false";
    private static final String USER_NAME = "sxstest";
    private static final String PASSWORD = "sxstest2017";
    private static final String QUERY_SQL = "select * from  " + TABLE_NAME + " where 1 != 1";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

        //获取注释
        ResultSet resultSet = connection.getMetaData().getColumns(null, SCHEMA_PATTERN, TABLE_NAME, "%");
        Map<String, String> columnRemarkMap = new HashMap<>();
        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            String remarks = resultSet.getString("REMARKS");
            columnRemarkMap.put(columnName, remarks);

        }
        //获取其他字段属性
        List<TableMetaData> tableMetaDataList = new ArrayList<>();
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.query(connection, QUERY_SQL, new ResultSetHandler<Object>() {
            @Override
            public Object handle(ResultSet rs) throws SQLException {
                ResultSetMetaData metaData = rs.getMetaData();
                int count = metaData.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    String columnName = metaData.getColumnName(i);
                    int size = metaData.getColumnDisplaySize(i);
                    String typeName = metaData.getColumnTypeName(i);
                    String remark = columnRemarkMap.get(columnName);
                    tableMetaDataList.add(new TableMetaData(columnName, typeName + "(" + size + ")", remark));
                }
                return null;
            }
        });
        //生成文件
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("table");
            sheet.setColumnWidth(1, 25 * 256);
            sheet.setColumnWidth(2, 20 * 256);
            sheet.setColumnWidth(3, 50 * 256);

            HSSFRow row = sheet.createRow(1);
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE_GREY.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            HSSFCell cell = row.createCell((short) 1);
            cell.setCellValue("列名");
            cell.setCellStyle(style);
            cell = row.createCell((short) 2);
            cell.setCellValue("类型");
            cell.setCellStyle(style);
            cell = row.createCell((short) 3);
            cell.setCellValue("描述");
            cell.setCellStyle(style);

            HSSFCellStyle contentStyle = wb.createCellStyle();
            contentStyle.setAlignment(HorizontalAlignment.LEFT);
            contentStyle.setBorderBottom(BorderStyle.THIN);
            contentStyle.setBorderLeft(BorderStyle.THIN);
            contentStyle.setBorderRight(BorderStyle.THIN);
            contentStyle.setBorderTop(BorderStyle.THIN);
            contentStyle.setWrapText(true);
            for (int i = 1; i < tableMetaDataList.size(); i++) {
                row = sheet.createRow(i + 1);
                TableMetaData tableMetaData = tableMetaDataList.get(i);
                HSSFCell c = row.createCell(1);
                c.setCellValue(tableMetaData.getColumnName());
                c.setCellStyle(contentStyle);

                c = row.createCell(2);
                c.setCellValue(tableMetaData.getType());
                c.setCellStyle(contentStyle);

                c = row.createCell(3);
                c.setCellValue(tableMetaData.getRemarks());
                c.setCellStyle(contentStyle);

            }

            FileOutputStream fout = new FileOutputStream("F:\\tables.xls");
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
