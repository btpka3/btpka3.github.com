package me.test.sort;

import java.util.*;

/**
 * 桶排序 (Bucket sort)
 *
 * 非比较排序，桶排序可用于最大最小值相差较大的数据情况。
 * 适用于数据的分布均匀的情况，否则可能导致数据都集中到一个桶中
 *
 * - 找出待排序数组中的最大值max、最小值min
 * - 创建 (max-min)/arr.length+1 个桶
 * - 每个元素放到桶内
 * - 各个桶内排序
 * - 遍历每个桶，依次取出桶内元素即可
 */
public class BucketSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[]{49, 38, 65, 97, 76, 13, 27, 49};
        bucketSort(numbers);
        System.out.println(Arrays.asList(numbers));
    }

    public static void bucketSort(Integer[] numbers) {

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < numbers.length; i++) {
            max = Math.max(max, numbers[i]);
            min = Math.min(min, numbers[i]);
        }

        // 桶数
        int bucketNum = (max - min) / numbers.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucketArr.add(new ArrayList<Integer>());
        }

        // 将每个元素放入桶
        for (int i = 0; i < numbers.length; i++) {
            int num = (numbers[i] - min) / (numbers.length);
            bucketArr.get(num).add(numbers[i]);
        }

        // 对每个桶进行排序
        for (int i = 0; i < bucketArr.size(); i++) {
            Collections.sort(bucketArr.get(i));
        }

        System.out.println(bucketArr.toString());

    }
}
