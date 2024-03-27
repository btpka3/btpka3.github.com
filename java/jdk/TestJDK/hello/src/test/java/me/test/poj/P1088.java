package me.test.poj;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author dangqian.zll
 * @date 2022/10/21
 */
public class P1088 {

    @Test
    public void x() {
        int[][] arr = {
                {1, 2, 3, 4, 5},
                {16, 17, 18, 19, 6},
                {15, 24, 25, 20, 7},
                {14, 23, 22, 21, 8},
                {13, 12, 11, 10, 9}
        };
    }

    public List<Integer> find(int[][] arr) {
        AtomicInteger foundMaxLength = new AtomicInteger(0);

        IntStream.range(0, arr.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, arr[i].length)
                        .boxed()
                        .map(j -> new int[]{i, j})
                )
                .map(k -> {
                    Stack<Integer> stack = new Stack<>();
                    findWithStart(stack, foundMaxLength, arr, k[0], k[1]);
                    return stack;
                });


        return null;

    }


    public void findWithStart(Stack<Integer> stack, AtomicInteger foundMaxLength, int[][] arr, int i, int j) {

        if (i < 0 || i >= arr.length) {
            return;
        }
        if (j < 0 || j >= arr[i].length) {
            return;
        }
        int value = arr[i][j];
        if (!stack.isEmpty()) {
            if (value >= stack.peek()) {
                return;
            }
        }
        stack.push(value);

        Stack<Integer> cloneStackUp = new Stack<>();
        cloneStackUp.addAll(stack);
        findWithStart(cloneStackUp, foundMaxLength, arr, i - 1, j);

        Stack<Integer> cloneStackDown = new Stack<>();
        cloneStackDown.addAll(stack);
        findWithStart(cloneStackDown, foundMaxLength, arr, i + 1, j);

        Stack<Integer> cloneStackLeft = new Stack<>();
        cloneStackLeft.addAll(stack);
        findWithStart(cloneStackLeft, foundMaxLength, arr, i, j - 1);

        Stack<Integer> cloneStackRight = new Stack<>();
        cloneStackRight.addAll(stack);
        findWithStart(cloneStackRight, foundMaxLength, arr, i, j + 1);

        Stack<Integer> newStack = Arrays.asList(cloneStackUp, cloneStackDown, cloneStackLeft, cloneStackRight).stream()
                .reduce((first, second) -> second.size() > first.size() ? second : first)
                .get();

        if (newStack.size() == stack.size()) {
            return;
        }

        if (newStack.size() > foundMaxLength.get()) {
            foundMaxLength.set(newStack.size());
        }
        stack.clear();
        stack.addAll(newStack);
    }


}
