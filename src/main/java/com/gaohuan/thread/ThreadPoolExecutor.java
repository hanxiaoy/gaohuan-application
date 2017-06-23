package com.gaohuan.thread;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by gaohuan on 2017/6/15.
 */
public class ThreadPoolExecutor implements Executor {

    private LinkedBlockingDeque<Runnable> workerQueue;

    private HashSet<WorkThread> workThreads;

    private int minPoolSize = 5;

    private int maxPoolSize = 10;

    private volatile boolean shutDown = false;

//    private volatile boolean terminated = false;

    public ThreadPoolExecutor() {
        workThreads = new HashSet<WorkThread>();
        for (int i = 0; i < minPoolSize; i++) {
            WorkThread workThread = new WorkThread();
            workThreads.add(workThread);
            workThread.start();
        }
        workerQueue = new LinkedBlockingDeque<Runnable>();
    }

    @Override
    public void execute(Runnable runnable) {
        workerQueue.addFirst(runnable);
        if (workerQueue.size() > minPoolSize) {

        }
    }

    @Override
    public void shutdown() {

    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public boolean isShutDown() {
        return shutDown;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }


    class WorkThread extends Thread {
        @Override
        public void run() {
            while (!isShutDown()) {
                Runnable r = workerQueue.pollLast();
                r.run();
            }
        }
    }

}
