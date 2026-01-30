package me.test.sort;

import java.util.Arrays;

/**
 * 冒泡排序。
 * <p>
 * 每次比较相邻两个值，将如果大，则交换，一遍循环后，最大值将会到最后的位置。
 * <p>
 * 第一遍遍历后，倒数第一个元素是最大值。
 * 第二遍遍历后，倒数第二个元素是次大值。
 * ...
 * <p>
 * 时间复杂度：T(n) = O(n) * O(n-1) = O(n^2)
 */
public class BubbleSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[]{49, 38, 65, 97, 76, 13, 27, 49};
        bubbleSort(numbers);
    }


    public static void swap(Integer[] numbers, int i, int j) {
        Integer tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }


    public static void bubbleSort(Integer[] numbers) {

        int size = numbers.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {

                if (numbers[j] > numbers[j + 1]) {
                    swap(numbers, j, j + 1);
                }
            }

            System.out.println("loop [" + i + "] : " + Arrays.asList(numbers));
        }
    }
}
