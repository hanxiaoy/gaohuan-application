package com.gaohuan.sort;

/**
 * 归并排序
 * <p>
 * 将两个（或两个以上）有序表合并成一个新的有序表 即把待排序序列分为若干个子序列，每个子序列是有序的。
 * 然后再把有序子序列合并为整体有序序列
 * <p>
 * 时间复杂度为O(nlogn)
 * <p>
 * 稳定排序
 */

public class Sort6 {
    public static void main(String[] args) {
        int[] array = new int[]{1, 5, 3, 2, 8, 9, 6};
        //排序
        sort(array, 0, array.length - 1);
        //打印
        Utils.printArray(array);
    }

    /**
     * 归并排序
     *
     * @param array
     */
    private static void sort(int[] array, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            //左侧排序
            sort(array, low, mid);
            //右侧排序
            sort(array, mid + 1, high);
            //合并
            merge(array, low, mid, high);
        }

    }

    /**
     * 合并
     *
     * @param array
     * @param low
     * @param mid
     * @param high
     */
    public static void merge(int[] array, int low, int mid, int high) {
        int[] tmpArray = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= high) {
            if (array[i] < array[j]) {
                tmpArray[k++] = array[i++];
            } else {
                tmpArray[k++] = array[j++];
            }
        }
        //拷贝左侧剩余的
        while (i <= mid) {
            tmpArray[k++] = array[i++];
        }
        //拷贝右侧剩余的
        while (j <= high) {
            tmpArray[k++] = array[j++];
        }
        //复制回原数组
        for (int l = 0; l < tmpArray.length; l++) {
            array[l + low] = tmpArray[l];
        }

    }


}
