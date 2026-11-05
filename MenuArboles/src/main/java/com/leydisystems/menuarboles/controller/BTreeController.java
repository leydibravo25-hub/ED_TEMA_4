package com.leydisystems.menuarboles.controller;

import com.leydisystems.menuarboles.classes.BTree;
import java.util.List;

public class BTreeController extends TreeControllerBase {

    private final BTree tree = new BTree();

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
        return tree.values();
    }
}