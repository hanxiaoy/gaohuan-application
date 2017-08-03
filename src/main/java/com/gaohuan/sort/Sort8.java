package com.gaohuan.sort;

/**
 * 基数排序
 */

public class Sort8 {
    public static void main(String[] args) {
        int[] array = Utils.randomArray(40);
        //排序
        array = sort(array, 0, maxLength(array));
        //打印
        Utils.printArray(array);
    }

    /**
     * 堆排序
     *
     * @param array
     */
    public static int[] sort(int[] array, int digit, int maxLength) {
        if (digit >= maxLength) {
            return array;
        }
        final int radix = 10;
        int length = array.length;
        int[] count = new int[radix];
        int[] bucket = new int[length];
        for (int i = 0; i < length; i++) {
            count[digit(array[i], digit)]++;
        }

        for (int i = 1; i < radix; i++) {
            count[i] = count[i] + count[i - 1];
        }
        for (int i = length - 1; i >= 0; i--) {
            int number = array[i];
            int d = digit(number, digit);
            bucket[count[d] - 1] = number;
            Utils.printArray(bucket);
            Utils.printArray(count);
            count[d]--;

        }
        return sort(bucket, digit + 1, maxLength);
    }

    /**
     * 获取x在d位上的数字
     *
     * @param x
     * @param d
     * @return
     */
    public static int digit(int x, int d) {
        int[] array = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000};
        return (x / array[d]) % 10;

    }

    /**
     * 获取数组中最大值得长度
     *
     * @param array
     * @return
     */
    public static int maxLength(int[] array) {
        int maxLength = 0;
        for (int i = 0; i < array.length; i++) {
            int length = String.valueOf(array[i]).length();
            if (length > maxLength) {
                maxLength = length;
            }

        }
        return maxLength;
    }


}
