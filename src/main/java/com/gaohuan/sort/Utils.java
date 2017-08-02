package com.gaohuan.sort;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaohuan on 2017/7/29.
 */
public class Utils {

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
        System.out.println("\n===========================================");
    }

    public static int[] randomArray(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(RandomUtils.nextInt(0, 100));
        }
        return ArrayUtils.toPrimitive(list.toArray(new Integer[]{}));
    }
}
