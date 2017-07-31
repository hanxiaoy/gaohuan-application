package com.gaohuan.sort;

/**
 * O(1)  < O(lgn)  <  O(n) <  O(nlgn)  < O(n2)< O(n3)<O(2n)  < O(n!) < O(nn)
 * <p>
 * 堆排序
 * <p>
 * 堆是一棵顺序存储的完全二叉树。
 * 其中每个结点的关键字都不大于其孩子结点的关键字，这样的堆称为小根堆。
 * 其中每个结点的关键字都不小于其孩子结点的关键字，这样的堆称为大根堆。
 * <p>
 * （1）根据初始数组去构造初始堆（构建一个完全二叉树，保证所有的父结点都比它的孩子结点数值大）。
 * <p>
 * （2）每次交换第一个和最后一个元素，输出最后一个元素（最大值），然后把剩下元素重新调整为大根堆。
 * <p>
 * O(nlog2n)
 * <p>
 * 不稳定
 */

public class Sort7 {
    public static void main(String[] args) {
        int[] array = new int[]{1, 5, 3, 2, 8, 9, 6};
        //排序
        sort(array);
        //打印
        PrintUtils.printArray(array);
    }

    /**
     * 堆排序
     *
     * @param array
     */
    public static void sort(int[] array) {
        int size = array.length;
        if (size <= 1) {
            return;
        }
        // (size / 2) - 1 最后一个非叶子节点 从下往上调整堆
        for (int i = (size / 2) - 1; i >= 0; i--) {
            adjust(array, i, size);
        }
        //每次交换第一个和最后一个元素，输出最后一个元素（最大值），然后把剩下元素重新调整为大根堆
        for (int i = size - 1; i > 0; i--) {
            int tmp = array[i];
            array[i] = array[0];
            array[0] = tmp;
            adjust(array, 0, i);
        }

    }

    /**
     * 调整堆
     *
     * @param array
     * @param i
     * @param len
     */
    public static void adjust(int[] array, int i, int len) {
        int tmp = array[i];
        int child = 2 * i + 1;
        while (child < len) {
            // 如果有右孩子结点，并且右孩子结点的值大于左孩子结点，则选取右孩子结点
            if (child + 1 < len && array[child] < array[child + 1]) {
                child = child + 1;
            }
            // 如果父结点的值已经大于孩子结点的值，则直接结束
            if (tmp >= array[child]) {
                break;
            }
            // 把孩子结点的值赋给父结点
            array[i] = array[child];
            //重新设置父节点,继续筛选
            i = child;
            child = 2 * i + 1;
        }
        //把临时值设置回去
        array[i] = tmp;

    }


}
