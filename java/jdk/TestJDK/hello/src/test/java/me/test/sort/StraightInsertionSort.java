package me.test.sort;

import java.util.*;

/**
 * 插入排序—直接插入排序(Straight Insertion Sort)
 *
 * 将一个记录插入到已排序好的有序表中，从而得到一个新，记录数增1的有序表
 *
 * 时间复杂度：
 *
 *  T(n) = O(n-1) * O(n-2)
 *
 */
public class StraightInsertionSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[]{49, 38, 65, 97, 76, 13, 27, 49};
        insertSort(numbers);
        System.out.println(Arrays.asList(numbers));
    }

    public static void swap(Integer[] numbers, int i, int j) {
        Integer tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }

    /**
     *
     */
    public static void insertSort(Integer[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            for (int j = i; j > 0; j--) {
                if (numbers[j] < numbers[j - 1]) {
                    swap(numbers, j - 1, j);
                }
            }
            System.out.println("loop [" + i + "] : " + Arrays.asList(numbers));
        }
    }
}
