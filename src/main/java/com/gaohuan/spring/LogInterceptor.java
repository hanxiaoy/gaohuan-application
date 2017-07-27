package com.gaohuan.spring;

import com.alibaba.fastjson.JSON;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志记录方法拦截器
 *
 * @Deprecated 这个类不在使用，执行时间记录在thrift服务接口内
 */
public class LogInterceptor implements MethodInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String className = invocation.getThis().getClass().getSimpleName();
        String methodName = className + "." + invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        //过滤属性
        logger.info( methodName + " # params:{}", JSON.toJSONString(arguments));

        Object result = invocation.proceed();

        logger.info( methodName + " # return:{}", JSON.toJSONString(result));

        return result;
    }

}
