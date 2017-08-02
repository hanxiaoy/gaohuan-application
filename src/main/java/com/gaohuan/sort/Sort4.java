package com.gaohuan.sort;

/**
 * 选择排序
 * <p>
 * 在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
 * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
 * <p>
 * 效率O（n²），适用于排序小的列表
 */
public class Sort4 {
    public static void main(String[] args) {
        int[] array = new int[]{1, 5, 3, 2, 8, 9, 6};
        //排序
        sort(array);
        //打印
        Utils.printArray(array);
    }

    /**
     * 选择排序
     *
     * @param array
     */
    private static void sort(int[] array) {
        int size = array.length;
        for (int i = 0; i < size; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = tmp;
        }

    }

}
