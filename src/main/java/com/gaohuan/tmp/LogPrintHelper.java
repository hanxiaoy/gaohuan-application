package com.gaohuan.tmp;

/**
 * 根据参数拼接日志
 */
public class LogPrintHelper {
    public static void main(String[] args) {
        String paramsStr = " String logId, Integer userId, String investAmount, BigDecimal bAmount,\n" +
                "                BigDecimal projectAmount, BigDecimal projectBalance, BigDecimal perMoney,\n" +
                "        int type, Integer redId, BigDecimal bRedAmount, Date startDate, Date endDate";
        String msg = "投资条件验证";
        //输出
        printLogger(msg, paramsStr);
    }

    public static void printLogger(String msg, String paramsStr) {
        StringBuilder builder = new StringBuilder();
        builder.append("logger.info(\"").append(msg).append(" # ");
        for (String bb : paramsStr.split(",")) {
            builder.append(bb.trim().split("\\s+")[1]).append(":{},");
        }
        builder.replace(builder.lastIndexOf(","), builder.length(), "\",\n");
        for (String bb : paramsStr.split(",")) {
            builder.append(bb.trim().split("\\s+")[1]).append(",");
        }
        builder.deleteCharAt(builder.lastIndexOf(",")).append(");");

        System.out.println(builder.toString());

    }
}
