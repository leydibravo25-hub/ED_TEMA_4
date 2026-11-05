
package com.leydisystems.menuarboles.controller;

import com.leydisystems.menuarboles.classes.TrieTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class TrieTreeController {

    @FXML
    private TextField wordField;

    @FXML
    private Label statusLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private Pane treePane;

    private TrieTree trieTree = new TrieTree();

    @FXML
    public void initialize() {
        statusLabel.setText("Escribe una palabra para comenzar.");
        resultLabel.setText("");
        drawTree();
    }

    @FXML
    public void addWord() {
        String word = wordField.getText();

        if (word == null || word.trim().isEmpty()) {
            statusLabel.setText("Escribe una palabra.");
            return;
        }

        word = word.trim().toLowerCase();

        if (!word.matches("[a-záéíóúüñ]+")) {
            statusLabel.setText("Usa solamente letras.");
            return;
        }

        if (trieTree.searchWord(word)) {
            statusLabel.setText("Esa palabra ya existe.");
            return;
        }

        trieTree.addWord(word);
        statusLabel.setText("Palabra agregada correctamente.");
        wordField.clear();
        drawTree();
    }

    @FXML
    public void searchWord() {
        String word = wordField.getText();

        if (word == null || word.trim().isEmpty()) {
            statusLabel.setText("Escribe la palabra que deseas buscar.");
            return;
        }

        word = word.trim().toLowerCase();

        if (trieTree.searchWord(word)) {
            statusLabel.setText("La palabra sí existe en el árbol.");
        } else {
            statusLabel.setText("La palabra no existe en el árbol.");
        }
    }

    @FXML
    public void showWords() {
        resultLabel.setText("Palabras: " + trieTree.getWords());
        statusLabel.setText("Lista de palabras registradas.");
    }

    // Preorder: visit the node before its children.
    @FXML
    public void showPreorder() {
        List<String> result = new ArrayList<>();
        preorder(trieTree.getRoot(), "Raíz", result);
        resultLabel.setText("Preorden: " + result);
        statusLabel.setText("Recorrido en preorden realizado.");
    }

    private void preorder(
            TrieTree.Node node,
            String label,
            List<String> result
    ) {
        result.add(label);

        for (Map.Entry<Character, TrieTree.Node> entry
                : node.children.entrySet()) {
            preorder(
                    entry.getValue(),
                    String.valueOf(entry.getKey()),
                    result
            );
        }
    }

    // Postorder: visit children before the node.
    @FXML
    public void showPostorder() {
        List<String> result = new ArrayList<>();
        postorder(trieTree.getRoot(), "Raíz", result);
        resultLabel.setText("Posorden: " + result);
        statusLabel.setText("Recorrido en posorden realizado.");
    }

    private void postorder(
            TrieTree.Node node,
            String label,
            List<String> result
    ) {
        for (Map.Entry<Character, TrieTree.Node> entry
                : node.children.entrySet()) {
            postorder(
                    entry.getValue(),
                    String.valueOf(entry.getKey()),
                    result
            );
        }

        result.add(label);
    }

    // Inorder for a multi-child Trie:
    // visit the first child, the node, and the remaining children.
    @FXML
    public void showInorder() {
        List<String> result = new ArrayList<>();
        inorder(trieTree.getRoot(), "Raíz", result);
        resultLabel.setText("Inorden: " + result);
        statusLabel.setText("Recorrido en inorden realizado.");
    }

    private void inorder(
            TrieTree.Node node,
            String label,
            List<String> result
    ) {
        List<Map.Entry<Character, TrieTree.Node>> children =
                new ArrayList<>(node.children.entrySet());

        if (!children.isEmpty()) {
            Map.Entry<Character, TrieTree.Node> first = children.get(0);

            inorder(
                    first.getValue(),
                    String.valueOf(first.getKey()),
                    result
            );
        }

        result.add(label);

        for (int i = 1; i < children.size(); i++) {
            Map.Entry<Character, TrieTree.Node> entry = children.get(i);

            inorder(
                    entry.getValue(),
                    String.valueOf(entry.getKey()),
                    result
            );
        }
    }

    // Level-order: visit nodes one level at a time.
    @FXML
    public void showLevels() {
        List<String> result = new ArrayList<>();
        Queue<TrieTree.Node> nodes = new LinkedList<>();
        Queue<String> labels = new LinkedList<>();

        nodes.add(trieTree.getRoot());
        labels.add("Raíz");

        while (!nodes.isEmpty()) {
            TrieTree.Node current = nodes.poll();
            String label = labels.poll();

            result.add(label);

            for (Map.Entry<Character, TrieTree.Node> entry
                    : current.children.entrySet()) {
                nodes.add(entry.getValue());
                labels.add(String.valueOf(entry.getKey()));
            }
        }

        resultLabel.setText("Por niveles: " + result);
        statusLabel.setText("Recorrido por niveles realizado.");
    }

    @FXML
    public void clearWords() {
        trieTree = new TrieTree();
        wordField.clear();
        resultLabel.setText("");
        statusLabel.setText("El árbol se ha limpiado.");
        drawTree();
    }

    @FXML
    public void returnToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/leydisystems/menuarboles/menu-view.fxml"
                    )
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root, 1100, 760));
            stage.setTitle("MenuArboles");
            stage.show();

        } catch (Exception exception) {
            statusLabel.setText("No se pudo regresar al menú.");
            exception.printStackTrace();
        }
    }

    private void drawTree() {
        treePane.getChildren().clear();

        TrieTree.Node root = trieTree.getRoot();

        double centerX = Math.max(treePane.getWidth() / 2, 350);
        double startY = 45;

        Circle rootCircle = new Circle(centerX, startY, 22);
        rootCircle.setFill(Color.web("#2563EB"));
        rootCircle.setStroke(Color.web("#1E3A8A"));
        rootCircle.setStrokeWidth(2);

        Label rootLabel = new Label("Raíz");
        rootLabel.setTextFill(Color.WHITE);
        rootLabel.setFont(Font.font("Arial", 11));
        rootLabel.setLayoutX(centerX - 14);
        rootLabel.setLayoutY(startY - 8);

        treePane.getChildren().addAll(rootCircle, rootLabel);

        drawChildren(root, centerX, startY, 120, 1);
    }

    private void drawChildren(
            TrieTree.Node node,
            double x,
            double y,
            double spacing,
            int depth
    ) {
        if (node == null || depth > 10) {
            return;
        }

        double nextY = y + 65;
        double childSpacing = Math.max(spacing / 2, 35);
        int childIndex = 0;

        for (Map.Entry<Character, TrieTree.Node> entry
                : node.children.entrySet()) {

            TrieTree.Node child = entry.getValue();
            char letter = entry.getKey();

            double childX = x
                    + (childIndex - (node.children.size() - 1) / 2.0)
                    * spacing;

            Line line = new Line(x, y + 22, childX, nextY - 20);
            line.setStroke(Color.web("#94A3B8"));
            line.setStrokeWidth(2);

            Circle circle = new Circle(childX, nextY, 20);

            if (child.isEndOfWord) {
                circle.setFill(Color.web("#BBF7D0"));
                circle.setStroke(Color.web("#15803D"));
            } else {
                circle.setFill(Color.web("#DBEAFE"));
                circle.setStroke(Color.web("#2563EB"));
            }

            circle.setStrokeWidth(2);

            Label letterLabel = new Label(String.valueOf(letter));
            letterLabel.setFont(Font.font("Arial", 14));
            letterLabel.setLayoutX(childX - 5);
            letterLabel.setLayoutY(nextY - 10);

            treePane.getChildren().addAll(line, circle, letterLabel);

            drawChildren(
                    child,
                    childX,
                    nextY,
                    childSpacing,
                    depth + 1
            );

            childIndex++;
        }
    }
}
