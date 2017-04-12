package com.gaohuan.misc;

/**
 * volatile 关键字练习
 * 可见性：是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。volatile只能让被他修饰内容具有可见性，但不能保证它具有原子性
 * 原子性：
 * 原子是世界上的最小单位，具有不可分割性
 * 有序性：
 * <p>
 * 　　Java 语言提供了 volatile 和 synchronized 两个关键字来保证线程之间操作的有序性，volatile 是因为其本身包含“禁止指令重排序”的语义，synchronized 是由“一个变量在同一个时刻只允许一条线程对其进行 lock 操作”这条规则获得的，此规则决定了持有同一个对象锁的两个同步块只能串行执行。
 *
 * @author gao.h  2017-04-12
 */
public class VolatilePractice {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    /**
     * 方法入口
     * 循环可能一直运行先去，因为读线程可能永远都看不到ready的值
     *
     * @param args
     */
    public static void main(String[] args) {
        ReaderThread readerThread = new ReaderThread();
        readerThread.start();
        number = 42;
        ready = true;
    }

}
