
package com.leydisystems.grafos.controller;

import com.leydisystems.grafos.classes.*;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.util.*;

public abstract class GraphController {

    @FXML
    protected TextField vertexField;

    @FXML
    protected TextField fromField;

    @FXML
    protected TextField toField;

    @FXML
    protected TextField weightField;

    @FXML
    protected TextField searchField;

    @FXML
    protected TextArea resultArea;

    @FXML
    protected Canvas graphCanvas;

    protected Graph graph;

    protected abstract GraphType getGraphType();

    @FXML
    public void initialize() {

        graph = new Graph(getGraphType());

        if (weightField != null) {
            weightField.setDisable(
                    getGraphType() != GraphType.WEIGHTED
            );
        }

        drawGraph();
        message("Agrega vértices para comenzar.");
    }

    @FXML
    protected void addVertex() {

        String vertex = vertexField.getText().trim();

        if (vertex.isEmpty()) {
            message("Escribe un vértice.");
            return;
        }

        if (graph.addVertex(vertex)) {
            vertexField.clear();
            drawGraph();
            message("Vértice agregado correctamente.");
        } else {
            message("El vértice ya existe o no es válido.");
        }
    }

    @FXML
    protected void addEdge() {

        String from = fromField.getText().trim();
        String to = toField.getText().trim();

        if (from.isEmpty() || to.isEmpty()) {
            message("Completa los dos vértices.");
            return;
        }

        double weight = 1;

        if (getGraphType() == GraphType.WEIGHTED) {
            try {
                weight = Double.parseDouble(weightField.getText().trim());
            } catch (NumberFormatException e) {
                message("Escribe un peso válido.");
                return;
            }
        }

        if (graph.addEdge(from, to, weight)) {
            fromField.clear();
            toField.clear();
            fromField.clear();
            toField.clear();

            if (weightField != null) {
                weightField.clear();
            }

            drawGraph();
            message("Conexión agregada correctamente.");
        } else {
            message("No se pudo crear la conexión.");
        }
    }

    @FXML
    protected void searchVertex() {

        String vertex = searchField.getText().trim();

        if (vertex.isEmpty()) {
            message("Escribe el vértice que deseas buscar.");
            return;
        }

        if (graph.containsVertex(vertex)) {
            message("El vértice " + vertex + " sí existe.");
        } else {
            message("El vértice no existe.");
        }
    }

    @FXML
    protected void removeVertex() {

        String vertex = searchField.getText().trim();

        if (graph.removeVertex(vertex)) {
            drawGraph();
            message("Vértice eliminado.");
        } else {
            message("No se encontró ese vértice.");
        }
    }

    @FXML
    protected void showGraph() {
        resultArea.setText(graph.describe());
    }

    @FXML
    protected void executeBFS() {

        String start = searchField.getText().trim();
        List<String> result = graph.bfs(start);

        if (result.isEmpty()) {
            message("No existe el vértice inicial.");
        } else {
            resultArea.setText("Recorrido BFS: " + result);
        }
    }

    @FXML
    protected void executeDFS() {

        String start = searchField.getText().trim();
        List<String> result = graph.dfs(start);

        if (result.isEmpty()) {
            message("No existe el vértice inicial.");
        } else {
            resultArea.setText("Recorrido DFS: " + result);
        }
    }

    @FXML
    protected void clearGraph() {
        graph.clear();
        drawGraph();
        message("Grafo limpiado.");
    }

    @FXML
    protected void goBack(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/leydisystems/grafos/view/MainView.fxml"
                    )
            );

            Scene scene = new Scene(loader.load(), 950, 650);

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.setTitle("Sistema de Grafos");
            stage.setScene(scene);

        } catch (Exception e) {
            message("No se pudo regresar al menú.");
        }
    }

    protected void message(String text) {
        if (resultArea != null) {
            resultArea.setText(text);
        }
    }

    protected void drawGraph() {

        if (graphCanvas == null) {
            return;
        }

        GraphicsContext gc = graphCanvas.getGraphicsContext2D();

        double width = graphCanvas.getWidth();
        double height = graphCanvas.getHeight();

        gc.setFill(Color.web("#F5F4FF"));
        gc.fillRect(0, 0, width, height);

        List<String> vertices =
                new ArrayList<>(graph.getVertices());

        if (vertices.isEmpty()) {
            return;
        }

        Map<String, double[]> positions = new HashMap<>();

        double centerX = width / 2;
        double centerY = height / 2;
        double radius = Math.min(width, height) * 0.35;

        for (int i = 0; i < vertices.size(); i++) {

            double angle = -Math.PI / 2
                    + 2 * Math.PI * i / vertices.size();

            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            positions.put(vertices.get(i), new double[]{x, y});
        }

        // Dibujar conexiones
        gc.setStroke(Color.web("#42466B"));
        gc.setLineWidth(2.5);

        for (String from : vertices) {

            for (Edge edge : graph.getAdjacency().get(from)) {

                String to = edge.getTo();

                if (!positions.containsKey(to)) {
                    continue;
                }

                if (getGraphType() != GraphType.DIRECTED
                        && from.compareTo(to) > 0) {
                    continue;
                }

                double[] a = positions.get(from);
                double[] b = positions.get(to);

                double dx = b[0] - a[0];
                double dy = b[1] - a[1];
                double length = Math.sqrt(dx * dx + dy * dy);

                if (length == 0) {
                    continue;
                }

                double ux = dx / length;
                double uy = dy / length;

                double startX = a[0] + ux * 25;
                double startY = a[1] + uy * 25;
                double endX = b[0] - ux * 28;
                double endY = b[1] - uy * 28;

                gc.strokeLine(startX, startY, endX, endY);

                if (getGraphType() == GraphType.DIRECTED) {
                    double px = -uy * 7;
                    double py = ux * 7;

                    gc.setFill(Color.web("#42466B"));
                    gc.fillPolygon(
                            new double[]{
                                    endX,
                                    endX - ux * 13 + px,
                                    endX - ux * 13 - px
                            },
                            new double[]{
                                    endY,
                                    endY - uy * 13 + py,
                                    endY - uy * 13 - py
                            },
                            3
                    );
                }

                if (getGraphType() == GraphType.WEIGHTED) {
                    gc.setFill(Color.BLACK);
                    gc.fillText(
                            String.valueOf(edge.getWeight()),
                            (a[0] + b[0]) / 2,
                            (a[1] + b[1]) / 2
                    );
                }
            }
        }

        // Dibujar vértices
        for (String vertex : vertices) {

            double[] point = positions.get(vertex);

            gc.setFill(Color.web("#4267D5"));
            gc.fillOval(
                    point[0] - 24,
                    point[1] - 24,
                    48,
                    48
            );

            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeOval(
                    point[0] - 24,
                    point[1] - 24,
                    48,
                    48
            );

            gc.setFill(Color.WHITE);
            gc.fillText(
                    vertex,
                    point[0] - vertex.length() * 3.5,
                    point[1] + 5
            );
        }
    }
}
