package com.gaohuan.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaohuan on 2017/10/19.
 */
public class Constants {

    public static final String MYSQL_SECRET_KEY = "1234";

    public static final Map<String, List<String>> TABLE_TO_COLUMN = new HashMap<String, List<String>>();

    static {
        //test_1
        TABLE_TO_COLUMN.put("TEST_1", Arrays.asList("phone","bank_no"));
        //test_2
        TABLE_TO_COLUMN.put("TEST_2", Arrays.asList("phone"));
    }

    private Constants() {}

}
