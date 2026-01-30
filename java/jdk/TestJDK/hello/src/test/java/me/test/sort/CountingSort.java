package me.test.sort;

import java.util.Arrays;

/**
 * 计数排序 (Counting sort)
 * <p>
 * 非比较排序，需要大量空间。仅适用于数据比较集中的情况。
 * <p>
 * 时间复杂度：Ο(n+k)
 * min: O(n*log(n))
 */
public class CountingSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[]{49, 38, 65, 97, 76, 13, 27, 49};
        countingSort(numbers);
        System.out.println(Arrays.asList(numbers));
    }


    /**
     *
     */
    public static void countingSort(Integer[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return;
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        // 找出数组中的最大最小值
        for (int i = 0; i < numbers.length; i++) {
            max = Math.max(max, numbers[i]);
            min = Math.min(min, numbers[i]);
        }

        int help[] = new int[max];

        // 找出每个数字出现的次数
        for (int i = 0; i < numbers.length; i++) {
            int mapPos = numbers[i] - min;
            help[mapPos]++;
        }

        int index = 0;
        for (int i = 0; i < help.length; i++) {
            while (help[i]-- > 0) {
                numbers[index++] = i + min;
            }
        }

    }
}
