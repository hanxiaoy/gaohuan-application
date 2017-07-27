package com.gaohuan.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaohuan on 2017/7/20.
 */
public class JvmInfoMain {
    public static void main(String[] args) {
        memory();
    }

    private static void memory() {
        List<byte[]> byteList = new ArrayList<>();
        int kb = 1024;
        for (int i = 0; i < 5; i++) {
            // 可使用内存
            long totalMemory = Runtime.getRuntime().totalMemory() / kb;
            // 剩余内存
            long freeMemory = Runtime.getRuntime().freeMemory() / kb;
            // 最大可使用内存
            long maxMemory = Runtime.getRuntime().maxMemory() / kb;
            byteList.add(new byte[1 * 1000 * 1000]);
            System.out.println("totalMemory:" + totalMemory);
            System.out.println("freeMemory:" + freeMemory);
            System.out.println("maxMemory:" + maxMemory);
            System.out.println("=============================================================");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

}
