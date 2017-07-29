package com.gaohuan.sort;

/**
 * 对相邻的元素进行两两比较，顺序相反则进行交换，这样，每一趟会将最小或最大的元素“浮”到顶端，最终达到完全有序
 */
public class Sort3 {
    public static void main(String[] args) {
        int[] array = new int[]{1, 5, 3, 2, 8, 9, 6};
        //排序
        sort(array);
        //打印
        PrintUtils.printArray(array);
    }

    /**
     * 冒泡排序
     *
     * @param array
     */
    private static void sort(int[] array) {
        int size = array.length;
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }

    }

    private static void sort2(int[] array) {
        int size = array.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
    }
}
