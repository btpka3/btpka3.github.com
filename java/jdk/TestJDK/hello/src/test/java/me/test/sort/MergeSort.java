package me.test.sort;

import java.util.Arrays;

/**
 * 归并排序（Merge Sort）
 * <p>
 * 将两个以上的有序序列合并为一个新的有序序列。
 *
 */
public class MergeSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[] {49, 38, 65, 97, 76, 13, 27, 49};
        mergeSort(numbers, 0, numbers.length - 1);
        System.out.println(Arrays.asList(numbers));
    }

    public static void mergeSort(Integer[] numbers, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            mergeSort(numbers, low, mid);
            // 右边
            mergeSort(numbers, mid + 1, high);
            // 左右归并
            merge(numbers, low, mid, high);
        }
    }

    public static void merge(Integer[] numbers, int low, int mid, int high) {

        // 临时序列，长度为两个序列长度之和。
        int[] temp = new int[high - low + 1];
        int leftIndex = low; // 左指针
        int rightIndex = mid + 1; // 右指针
        int k = 0;

        // 从两组数列中依次取出一个最小值，并将其放到临时序列 中
        while (leftIndex <= mid && rightIndex <= high) {
            if (numbers[leftIndex] < numbers[rightIndex]) {
                // 如果最小值在左序列中：复制，左指针和新序列指针分别+1
                temp[k++] = numbers[leftIndex++];
            } else {
                // 如果最小值在右序列中：复制，右指针和新序列指针分别+1
                temp[k++] = numbers[rightIndex++];
            }
        }

        // 把左边剩余的数移入数组
        while (leftIndex <= mid) {
            temp[k++] = numbers[leftIndex++];
        }

        // 把右边边剩余的数移入数组
        while (rightIndex <= high) {
            temp[k++] = numbers[rightIndex++];
        }

        // 把新数组中的数覆盖 numbers 数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            numbers[k2 + low] = temp[k2];
        }
    }
}
