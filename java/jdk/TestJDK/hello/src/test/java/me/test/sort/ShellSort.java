package me.test.sort;

import java.util.Arrays;

/**
 * 插入排序—希尔排序（Shell`s Sort,渐减增量排序diminishing increment sort）
 * <p>
 * - 先将整个列表分若干段
 * - 每段中相同位置的元素进行比较，交换
 * - 步长最小为1
 */
public class ShellSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[]{49, 38, 65, 97, 76, 13, 27, 49};
        shellSort(numbers);
    }

    public static void shellSort(Integer[] numbers) {

        // 步长初始为序列长度的一半，每次都折半
        for (int increment = numbers.length / 2; increment > 0; increment /= 2) {

            // 步长固定时，每个两两分组的第i个元素进行比较
            for (int i = increment; i < numbers.length; i++) {

                int temp = numbers[i];
                int j = 0;
                for (j = i - increment; j >= 0; j -= increment) {
                    if (temp < numbers[j]) {
                        numbers[j + increment] = numbers[j];
                    } else {
                        break;
                    }
                }
                numbers[j + increment] = temp;
            }

            System.out.println("" + increment + " ： " + Arrays.asList(numbers));

        }
    }


    public static void subInertSort(Integer[] numbers, int start, int len) {
        for (int i = 1; i < numbers.length; i++) {
            for (int j = i; j > 0; j--) {
                if (numbers[j] < numbers[j - 1]) {
                    swap(numbers, j - 1, j);
                }
            }
            System.out.println("loop [" + i + "] : " + Arrays.asList(numbers));
        }
    }

    public static void swap(Integer[] numbers, int i, int j) {
        Integer tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }
}
