package com.gaohuan.spring.service;

import org.springframework.stereotype.Service;

/**
 * Created by gaohuan on 2017/7/10.
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String print1(String arg1, String arg2) {
        print2(arg1, arg2);
        return "result # arg1:" + arg1 + ",arg2:" + arg2;
    }

    @Override
    public String print2(String arg1, String arg2) {
        return "result # arg1:" + arg1 + ",arg2:" + arg2;
    }
}
