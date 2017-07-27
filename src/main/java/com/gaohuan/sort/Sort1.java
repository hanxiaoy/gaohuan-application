package com.gaohuan.sort;

/**
 * 快速排序
 * <p>
 * 1．先从数列中取出一个数作为基准数。
 * <p>
 * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * <p>
 * 3．再对左右区间重复第二步，直到各区间只有一个数。
 * <p>
 * 第一种：三者取中。将序列首、尾和中间位置上的记录进行比较，选择三者中值作为基准关键字。
 * 第二种：取left和right之间的一个随机数这里写图片描述，用n[m]作为基准关键字。采用这种方法得到的快速排序一般称为随机的快速排序。
 * <p>
 * 快速排序的平均时间复杂度为O(nlogn)
 */
public class Sort1 {
    public static void main(String[] args) {
        int[] array = new int[]{3, 6, 5, -9, 7, -1, 8, -2, 4};
        sort1(array, 0, array.length - 1);
        printArray(array, 0, 0);

    }

    /**
     * 绝对值排序
     *
     * @param arr
     * @param low
     * @param high
     */
    private static void sort1(int arr[], int low, int high) {
        if (low < high) {
            int i = low, j = high;
            int base = abs(arr[low]);//固定切分，随机切分和三数取中切分
            int originBase = arr[low];
            System.out.println("base=" + base);
            while (i < j) {
                while (i < j && abs(arr[j]) > base) {
                    j--;
                }
                if (arr[i] != arr[j]) {
                    arr[i] = arr[j];
                    printArray(arr, i, j);
                }

                while (i < j && abs(arr[i]) < base) {
                    i++;
                }
                if (arr[i] != arr[j]) {
                    arr[j] = arr[i];
                    printArray(arr, i, j);
                }

            }
            arr[i] = originBase;
            printArray(arr, i, j);
            System.out.println("=======================================================");
            sort1(arr, low, i - 1);
            sort1(arr, i + 1, high);
        }

    }

    /**
     * 正常排序
     *
     * @param arr  数组
     * @param low
     * @param high
     */
    private static void sort2(int arr[], int low, int high) {
        if (low < high) {
            int i = low, j = high;
            int base = arr[low];//固定切分，随机切分和三数取中切分
            System.out.println("base=" + base);
            while (i < j) {
                while (i < j && arr[j] > base) {
                    j--;
                }
                if (arr[i] != arr[j]) {
                    arr[i] = arr[j];
                    printArray(arr, i, j);
                }

                while (i < j && arr[i] < base) {
                    i++;
                }
                if (arr[i] != arr[j]) {
                    arr[j] = arr[i];
                    printArray(arr, i, j);
                }

            }
            arr[i] = base;//把基准值设置回去
            printArray(arr, i, j);
            System.out.println("=======================================================");
            sort2(arr, low, i - 1);
            sort2(arr, i + 1, high);
        }

    }

    public static void printArray(int[] arr, int m, int k) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
        System.out.print("i=>" + m + " , j=>" + k);
        System.out.println();
    }

    public static int abs(int v) {
        return Math.abs(v);
    }

}
