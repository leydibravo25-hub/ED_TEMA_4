package com.leydisystems.menuarboles.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    public void openBinaryTree(ActionEvent event) {
        openView(event, "binary-tree-view.fxml", "Árbol binario");
    }

    @FXML
    public void openBST(ActionEvent event) {
        openView(event, "binary-search-tree-view.fxml",
                "Árbol binario de búsqueda");
    }

    @FXML
    public void openAVL(ActionEvent event) {
        openView(event, "avl-tree-view.fxml", "Árbol AVL");
    }

    @FXML
    public void openHeap(ActionEvent event) {
        openView(event, "heap-tree-view.fxml", "Heap mínimo");
    }

    @FXML
    public void openBTree(ActionEvent event) {
        openView(event, "b-tree-view.fxml", "Árbol B");
    }

    @FXML
    public void openTrie(ActionEvent event) {
        openView(event, "trie-tree-view.fxml", "Árbol Trie");
    }

    private void openView(
            ActionEvent event, String fileName, String title) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/leydisystems/menuarboles/" + fileName));

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.setScene(new Scene(root, 1100, 760));
            stage.setTitle("MenuArboles | " + title);
            stage.show();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}