package me.test.sort;

import java.util.*;

/**
 * 交换排序—快速排序（Quick Sort）
 *
 * 将第一个元素作为中值元素，将该中值元素挪到中间位置，使一边是比他大的数，一边是比他小的数。
 *
 * 时间复杂度：
 * 最坏情况: n 个元素的数组被分割为 1,  n-1。
 *          T(n) = O(n) + T(1) + T(n-1)
 *               = 0(n^2)
 *
 * 最好情况: n 个元素的数组被分割为 (n-1)/2, (n-1)/2  。
 *          T(n) = O(n) + T(n/2) + T(n/2)
 *               = O(n*log2(n))
 */
public class QuickSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[]{49, 38, 65, 97, 76, 13, 27, 49};
        quickSort(numbers, 0, numbers.length - 1);
        System.out.println(Arrays.asList(numbers));
    }

    /**
     * 将第一个元素作为中值元素，将该中值元素挪到中间位置，使一边是比他大的数，一边是比他小的数。
     *
     * 该方法的时间复杂度是 O(n).
     */
    public static int partition(Integer[] numbers, int low, int high) {
        // 数组的第一个作为中轴
        int middleNum = numbers[low];


        while (low < high) {

            // 从后半部分向前扫描
            while (low < high && numbers[high] >= middleNum) {
                high--;
            }
            numbers[low] = numbers[high];// 比中轴小的记录移到低端

            // 从前半部分向后扫描
            while (low < high && numbers[low] <= middleNum) {
                low++;
            }
            numbers[high] = numbers[low]; // 比中轴大的记录移到高端
        }
        numbers[low] = middleNum; // 中轴记录到尾
        return low; // 返回中轴的位置
    }


    /**
     *
     * @param numbers 待排序数组
     * @param low  开始位置
     * @param high 结束位置
     */
    public static void quickSort(Integer[] numbers, int low, int high) {
        if (low >= high) {
            return;
        }

        // 将numbers数组进行一分为二
        int middle = partition(numbers, low, high);

        // 对低字段表进行递归排序
        quickSort(numbers, low, middle - 1);

        // 对高字段表进行递归排序
        quickSort(numbers, middle + 1, high);

    }
}
