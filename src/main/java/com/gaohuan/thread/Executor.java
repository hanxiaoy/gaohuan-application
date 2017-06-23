package com.gaohuan.thread;

/**
 * Created by gaohuan on 2017/6/15.
 */
public interface Executor {

    void execute(Runnable runnable);

    void shutdown();

    boolean isTerminated();
}
