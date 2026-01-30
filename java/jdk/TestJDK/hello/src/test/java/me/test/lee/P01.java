package me.test.lee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.Test;

/**
 * binary tree level order traversal
 *
 * @author dangqian.zll
 * @date 2024/5/22
 */
public class P01 {

    /**
     * Definition for a binary tree node.
     */
    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> result = new ArrayList<>(8);
        List<TreeNode> nextLevelNodes = Collections.singletonList(root);
        while (!nextLevelNodes.isEmpty()) {
            List<Integer> nexLevelValues = nextLevelNodes.stream()
                    .map(node -> node.val)
                    .collect(Collectors.toList());

            result.add(nexLevelValues);

            nextLevelNodes = getNextLevelNodes(nextLevelNodes);
        }
        return result;
    }

    @Nonnull
    List<TreeNode> getNextLevelNodes(List<TreeNode> someLevelNodes) {
        if (someLevelNodes == null || someLevelNodes.isEmpty()) {
            return Collections.emptyList();
        }
        return someLevelNodes.stream().flatMap(node -> Stream.of(node.left, node.right)
                        .filter(Objects::nonNull)
                )
                .collect(Collectors.toList());
    }


    @Test
    public void test01() {
        TreeNode root = new TreeNode(
                3,
                new TreeNode(9),
                new TreeNode(
                        20,
                        new TreeNode(15),
                        new TreeNode(17)
                )
        );
        List<List<Integer>> result = levelOrder(root);
        System.out.println(result);

    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
