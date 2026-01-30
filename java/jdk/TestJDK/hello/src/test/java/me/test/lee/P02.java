package me.test.lee;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * maximum subarray
 *
 * @author dangqian.zll
 * @date 2024/5/22
 */
public class P02 {


    public int maxSubArray(int[] nums) {

        int maxStartIndex = 0;
        int maxEndIndex = 0;
        int maxSum = Integer.MIN_VALUE;
        // System.out.println(nums.length);
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j <= nums.length; j++) {
                int sum = Arrays.stream(nums, i, j).sum();
                // System.out.println("i:" + i + ", j:" + j + ", sum:" + sum);
                if (sum > maxSum) {
                    maxStartIndex = i;
                    maxEndIndex = j;
                    maxSum = sum;
                }
            }
        }

        System.out.println("maxStartIndex:" + maxStartIndex + ", maxEndIndex:" + maxEndIndex + ", maxSum:" + maxSum);
        String str = Arrays.stream(nums, maxStartIndex, maxEndIndex)
                .boxed()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(str);
        return maxSum;

    }

    int sum(int[] nums, int startIndex, int count) {
        int sum = 0;
        for (int i = startIndex; i < startIndex + count; i++) {
            sum += nums[i];
        }
        return sum;
    }

    @Test
    public void test01() {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result = maxSubArray(nums);
        System.out.println(result);
    }

    @Test
    public void sum01() {
        assertEquals(0, sum(new int[]{1, 2, 3, 4, 5}, 0, 0));
        assertEquals(1, sum(new int[]{1, 2, 3, 4, 5}, 0, 1));
        assertEquals(3, sum(new int[]{1, 2, 3, 4, 5}, 0, 2));
        assertEquals(6, sum(new int[]{1, 2, 3, 4, 5}, 0, 3));
        assertEquals(15, sum(new int[]{1, 2, 3, 4, 5}, 0, 5));
    }
}
