package com.gaohuan.sort;

/**
 * 希尔排序
 * <p>
 * 先将整个待排元素序列分割成若干个子序列（由相隔某个“增量”的元素组成的）分别进行直接插入排序，
 * 然后依次缩减增量再进行排序，待整个序列中的元素基本有序（增量足够小）时，再对全体元素进行一次直接插入排序
 * <p>
 * 希尔排序是不稳定算法
 * <p>
 * 两种方式 1、移位 2、交换
 */

public class Sort5 {
    public static void main(String[] args) {
        int[] array = new int[]{1, 5, 3, 2, 8, 9, 6};
        //排序
        sort2(array);
        //打印
        PrintUtils.printArray(array);
    }

    /**
     * 希尔排序1（标准写法)
     *
     * @param array
     */
    private static void sort1(int[] array) {
        int size = array.length;
        for (int gap = size / 2; gap > 0; gap--) {//依次缩减步长
            for (int i = 0; i < gap; i++) {
                //分别对每组插入排序
                for (int j = i + gap; j < size; j += gap) {
                    int k = array[j];
                    int l = j - gap;
                    for (; l >= 0 && array[l] > k; l -= gap) {
                        array[l + gap] = array[l];//后移一位
                    }
                    array[l + gap] = k;
                }

            }

        }

    }

    /**
     * 希尔排序2（改进2）
     *
     * @param array
     */
    private static void sort2(int[] array) {
        int size = array.length;
        for (int gap = size / 2; gap > 0; gap--) {
            //每组的最后一个向前进行排序
            for (int i = gap; i < size; i++) {
                for (int j = i - gap; j >= 0 && array[j] > array[j + gap]; j -= gap) {
                    swap(array, j, j + gap);
                }

            }

        }

    }

    /**
     * 交换
     *
     * @param array
     * @param i
     * @param j
     */
    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }


}
