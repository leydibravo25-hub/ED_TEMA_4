package com.leydisystems.menuarboles.controller;

import com.leydisystems.menuarboles.classes.HeapTree;
import javafx.fxml.FXML;
import java.util.List;

public class HeapTreeController extends TreeControllerBase {

    private final HeapTree tree = new HeapTree();

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

    @FXML
    public void extractMinimum() {
        Integer value = tree.extractMin();

        if (value == null) {
            showStatus("El heap está vacío.");
        } else {
            showStatus("Mínimo extraído: " + value);
        }
    }

    @FXML
    public void showMinimum() {
        Integer value = tree.min();

        showStatus(value == null
                ? "El heap está vacío."
                : "Valor mínimo: " + value);
    }
}