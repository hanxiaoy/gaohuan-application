package com.gaohuan.sort;

/**
 * 插入排序
 * <p>
 * 每步将一个待排序的记录按其关键字的大小插到前面已经排序的序列中的适当位置，直到全部记录插入完毕为止。
 * <p>
 * 最佳效率O（n）；最糟效率O（n²）与冒泡、选择相同，适用于排序小列表
 * 若列表基本有序，则插入排序比冒泡、选择更有效率。
 */
public class Sort2 {
    public static void main(String[] args) {
        int[] array = new int[]{3, 5, 2, 1, 8, 6, 9};
        sort(array, array.length);
        printArray(array);

    }

    /**
     * 插入排序
     *
     * @param array
     * @param size
     */
    public static void sort(int[] array, int size) {
        for (int i = 1; i < size; i++) {
            int k = array[i];
            int j = i - 1;
            /*
                从后向前比较，大于待排序数，后移一位，直到找到待插入位置,并设置插入值
             */
            for (; j >= 0 && array[j] > k; j--) {
                array[j + 1] = array[j];
            }
            array[j + 1] = k;
        }
    }

    /**
     * 打印数组
     *
     * @param array
     */
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
    }

}
