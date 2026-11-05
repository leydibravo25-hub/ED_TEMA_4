package com.leydisystems.menuarboles.controller;

import com.leydisystems.menuarboles.classes.BinaryTree;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeController extends TreeControllerBase {

    private final BinaryTree tree = new BinaryTree();

    @Override
    protected boolean containsValue(int value) {
        return tree.search(value);
    }

    @Override
    protected void insertValue(int value) {
        tree.insert(value);
    }

    @Override
    protected void clearValues() {
        tree.clear();
    }

    @Override
    protected List<Integer> getValuesForDrawing() {
        List<Integer> values = new ArrayList<>();
        Queue<BinaryTree.Node> queue = new LinkedList<>();

        if (tree.getRoot() != null) {
            queue.add(tree.getRoot());
        }

        while (!queue.isEmpty()) {
            BinaryTree.Node node = queue.remove();
            values.add(node.value);

            if (node.left != null) {
                queue.add(node.left);
            }

            if (node.right != null) {
                queue.add(node.right);
            }
        }

        return values;
    }
}