package com.leydisystems.menuarboles.controller;

import com.leydisystems.menuarboles.classes.AVLTree;
import com.leydisystems.menuarboles.classes.BinarySearchTree;
import com.leydisystems.menuarboles.classes.BinaryTree;
import com.leydisystems.menuarboles.classes.BTree;
import com.leydisystems.menuarboles.classes.HeapTree;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public abstract class TreeControllerBase {

    @FXML protected TextField valueField;
    @FXML protected Label statusLabel;
    @FXML protected Label resultLabel;
    @FXML protected Pane treePane;

    private Color nodeColor = Color.web("#2563eb");

    protected abstract boolean containsValue(int value);
    protected abstract void insertValue(int value);
    protected abstract void clearValues();
    protected abstract List<Integer> getValuesForDrawing();

    @FXML
    public void initialize() {
        treePane.widthProperty().addListener(
                (observable, oldValue, newValue) -> drawTree());
        drawTree();
    }

    @FXML
    public void addValue() {
        String input = valueField.getText();

        if (input == null || input.trim().isEmpty()) {
            showStatus("Escribe un número entero.");
            return;
        }

        int value;

        try {
            value = Integer.parseInt(input.trim());
        } catch (NumberFormatException exception) {
            showStatus("Solo se permiten números enteros.");
            return;
        }

        if (value <= 0) {
            showStatus("El número debe ser mayor que cero.");
            return;
        }

        if (containsValue(value)) {
            showStatus("Ese número ya existe.");
            return;
        }

        insertValue(value);
        valueField.clear();
        showStatus("Número agregado correctamente.");
        drawTree();
    }

    @FXML
    public void searchValueAction() {
        String input = valueField.getText();

        if (input == null || input.trim().isEmpty()) {
            showStatus("Escribe el número que deseas buscar.");
            return;
        }

        try {
            int value = Integer.parseInt(input.trim());

            if (value <= 0) {
                showStatus("Ingresa un número mayor que cero.");
                return;
            }

            showStatus(containsValue(value)
                    ? "El número sí existe en el árbol."
                    : "El número no se encuentra.");
        } catch (NumberFormatException exception) {
            showStatus("Ingresa un número entero válido.");
        }
    }

    @FXML
    public void clearTreeAction() {
        clearValues();
        valueField.clear();
        resultLabel.setText("Recorrido: —");
        showStatus("Árbol limpiado.");
        drawTree();
    }

    @FXML
    public void showPreorder() {
        resultLabel.setText("Preorden: " + getPreorder());
    }

    @FXML
    public void showInorder() {
        resultLabel.setText("Inorden: " + getInorder());
    }

    @FXML
    public void showPostorder() {
        resultLabel.setText("Posorden: " + getPostorder());
    }

    @FXML
    public void showLevels() {
        resultLabel.setText("Por niveles: " + getValuesForDrawing());
    }

    protected void showStatus(String message) {
        statusLabel.setText(message);
    }

    protected void setNodeColor(String color) {
        nodeColor = Color.web(color);
        drawTree();
    }

    private void drawTree() {
        if (treePane == null) {
            return;
        }

        treePane.getChildren().clear();

        List<Integer> values = getValuesForDrawing();

        if (values.isEmpty()) {
            Text emptyText = new Text("Agrega valores para visualizar el árbol");
            emptyText.setFill(Color.web("#94a3b8"));
            emptyText.setFont(Font.font(16));
            emptyText.setX(30);
            emptyText.setY(50);
            treePane.getChildren().add(emptyText);
            return;
        }

        double width = Math.max(treePane.getWidth(), 700);
        double center = width / 2;
        int size = values.size();

        double[] xPositions = new double[size];
        double[] yPositions = new double[size];

        for (int i = 0; i < size; i++) {
            int level = (int) (Math.log(i + 1) / Math.log(2));
            int firstAtLevel = (1 << level) - 1;
            int position = i - firstAtLevel;
            int count = 1 << level;

            xPositions[i] = width * (position + 1.0) / (count + 1.0);
            yPositions[i] = 55 + level * 90;
        }

        for (int i = 1; i < size; i++) {
            int parent = (i - 1) / 2;

            Line line = new Line(
                    xPositions[parent], yPositions[parent],
                    xPositions[i], yPositions[i]);

            line.setStroke(Color.web("#64748b"));
            line.setStrokeWidth(2);
            treePane.getChildren().add(line);
        }

        for (int i = 0; i < size; i++) {
            Circle circle = new Circle(
                    xPositions[i], yPositions[i], 23, nodeColor);

            circle.setStroke(Color.WHITE);
            circle.setStrokeWidth(1.5);

            Text text = new Text(
                    xPositions[i] - 10, yPositions[i] + 5,
                    String.valueOf(values.get(i)));

            text.setFill(Color.WHITE);
            text.setFont(Font.font("Arial", 13));

            treePane.getChildren().addAll(circle, text);
        }
    }

    private String getPreorder() {
        return getValuesForDrawing().toString();
    }

    private String getInorder() {
        return getValuesForDrawing().toString();
    }

    private String getPostorder() {
        return getValuesForDrawing().toString();
    }

    @FXML
    public void returnToMenu() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource(
                            "/com/leydisystems/menuarboles/menu-view.fxml"));

            Stage stage = (Stage) treePane.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 760));
            stage.setTitle("MenuArboles | Menú principal");
        } catch (Exception exception) {
            showStatus("No se pudo regresar al menú.");
            exception.printStackTrace();
        }
    }
}