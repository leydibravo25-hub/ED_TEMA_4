package com.leydisystems.menuarboles.controller;

import com.leydisystems.menuarboles.classes.BinarySearchTree;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTreeController extends TreeControllerBase {

    private final BinarySearchTree tree = new BinarySearchTree();

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
        collect(tree.getRoot(), values);
        return values;
    }

    private void collect(
            BinarySearchTree.Node node, List<Integer> values) {

        if (node != null) {
            values.add(node.value);
            collect(node.left, values);
            collect(node.right, values);
        }
    }
}