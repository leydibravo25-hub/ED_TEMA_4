
package com.leydisystems.grafos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;

public class MenuGraphController {

    @FXML
    private void openUndirected() {
        openWindow("UndirectedGraphView.fxml", "Grafo no dirigido");
    }

    @FXML
    private void openDirected() {
        openWindow("DirectedGraphView.fxml", "Grafo dirigido");
    }

    @FXML
    private void openWeighted() {
        openWindow("WeightedGraphView.fxml", "Grafo ponderado");
    }

    @FXML
    private void openComplete() {
        openWindow("CompleteGraphView.fxml", "Grafo completo");
    }

    @FXML
    private void openCyclic() {
        openWindow("CyclicGraphView.fxml", "Grafo cíclico");
    }

    private void openWindow(String fileName, String title) {

        String path = "/com/leydisystems/grafos/" + fileName;

        try {
            URL resource = getClass().getResource(path);

            if (resource == null) {
                throw new IllegalStateException(
                        "No se encontró el archivo: " + path
                );
            }

            Parent root = new FXMLLoader(resource).load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        } catch (Exception exception) {

            exception.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al abrir");
            alert.setHeaderText("No se pudo abrir " + title);
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }
}
