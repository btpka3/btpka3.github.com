package me.test.sort;

import java.util.Arrays;

/**
 * 选择排序—堆排序（Heap Sort）
 * <p>
 * 堆排序是一种树形选择排序，是对直接选择排序的有效改进。
 * <p>
 * - 完全二叉树
 * 叶节点只能出现在最下层和次下层，并且最下面一层的结点都集中在该层最左边的若干位置的二叉树
 * <p>
 * 10   (完全二叉树, 堆: {10,9,8,3,1,4} )
 * /      \
 * 9       8
 * / \     /
 * 3  1   4
 * <p>
 * 10   (非完全二叉树)
 * /      \
 * 9       8
 * \     /
 * 1   4
 * -堆
 * 一棵顺序存储的完全二叉树。
 * <p>
 * - 小根堆
 * 每个结点的关键字都不大于其孩子结点的关键字，这样的堆称为小根堆。
 * R[i] <= R[2i+1] 且 R[i] <= R[2i+2]
 * - 大根堆
 * 每个结点的关键字都不小于其孩子结点的关键字，这样的堆称为。
 * R[i] >= R[2i+1] 且 R[i] >= R[2i+2]
 * <p>
 * - 步骤：
 * - 先构建大根堆
 * - 从倒数第二层最后一个节点开始，从右向左，从下向上，使之依次满足大根堆性质
 * - 多级时，如果子节点的与父节点交换了位置，则需要将该子节点递归调整为大根堆。
 * - 堆顶元素 R[0] 与 最后一个元素 R[n-1] 交换, 之后， 得到 无序区 R[0..n-2] 和有序区 R[n-1]
 * - 再将  R[0..n-2] 调整为大根堆，再和该堆区的最后一个元素交换，循环往复。
 * <p>
 * 时间复杂度:
 */
public class HeapSort {

    public static void main(String[] args) throws Exception {
        Integer[] numbers = new Integer[]{49, 38, 65, 97, 76, 13, 27, 49};
        heapSort(numbers);

        System.out.println(Arrays.asList(numbers));
    }


    public static void swap(Integer[] numbers, int i, int j) {
        Integer tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }


    public static void heapSort(Integer[] numbers) {
        if (numbers == null || numbers.length <= 1) {
            return;
        }
        System.out.println("source          : " + Arrays.asList(numbers));
        buildMaxHeap(numbers);

        for (int i = numbers.length - 1; i >= 1; i--) {
            swap(numbers, 0, i);

            maxHeap(numbers, i, 0);
            System.out.println("loop[" + i + "]         : " + Arrays.asList(numbers));
        }
    }

    private static void buildMaxHeap(Integer[] numbers) {
        if (numbers == null || numbers.length <= 1) {
            return;
        }

        int half = numbers.length / 2;
        for (int i = half; i >= 0; i--) {
            maxHeap(numbers, numbers.length, i);
            System.out.println("buildMaxHeap[" + i + "] : " + Arrays.asList(numbers));
        }
    }

    /**
     *
     * @param numbers       要排序的数组。
     * @param heapSize      堆的大小。本例中是从 n-1 依次递归减小
     * @param maxStoreIndex 堆中最大值应当存储的索引位置
     */
    private static void maxHeap(final Integer[] numbers, final int heapSize, final int maxStoreIndex) {
        int left = maxStoreIndex * 2 + 1;
        int right = maxStoreIndex * 2 + 2;

        // 当前节点、子节点中，最大值所在的索引位置。
        int largest = maxStoreIndex;

        // 当前节点和左子节点比较
        if (left < heapSize && numbers[left] > numbers[maxStoreIndex]) {
            largest = left;
        }

        // 当前节点和右子节点比较
        if (right < heapSize && numbers[right] > numbers[largest]) {
            largest = right;
        }

        if (maxStoreIndex != largest) {
            swap(numbers, maxStoreIndex, largest);

            maxHeap(numbers, heapSize, largest);
        }
    }
}
