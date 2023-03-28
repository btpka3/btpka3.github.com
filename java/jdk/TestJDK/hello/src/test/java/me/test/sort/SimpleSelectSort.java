package me.test.sort;

import java.util.*;

/**
 * 选择排序—简单选择排序（Simple Selection Sort）
 *
 * 假设要按照升序排序。
 * - 使第0个位置上是最小值
 * - 使第1个位置上是次小值
 * - ...
 *
 * 时间复杂度：T(n) = O(n) * O(n-1) = O(n^2)
 */
public class SimpleSelectSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[]{49, 38, 65, 97, 76, 13, 27, 49};
        selectionSort(numbers);
    }

    public static void swap(Integer[] numbers, int i, int j) {
        Integer tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }

    public static void selectionSort(Integer[] numbers) {

        for (int i = 0; i < numbers.length - 1; i++) {
            int minNumIndex = i;

            // 找到剩余数列中 最小数值所在的位置。
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[minNumIndex] > numbers[j]) {
                    minNumIndex = j;
                }
            }

            swap(numbers, i, minNumIndex);
            System.out.println("loop [" + i + "] : " + Arrays.asList(numbers));
        }
    }

}
