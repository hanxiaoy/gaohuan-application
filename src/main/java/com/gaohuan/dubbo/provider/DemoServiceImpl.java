package com.gaohuan.dubbo.provider;

import com.gaohuan.dubbo.DemoService;
import org.springframework.stereotype.Service;

/**
 * Created by gh on 2016/2/29 0029.
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
