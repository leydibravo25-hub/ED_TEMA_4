module com.leydisystems.grafos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.leydisystems.grafos.controller to javafx.fxml;
    opens com.leydisystems.grafos.classes to javafx.fxml;
    exports com.leydisystems.grafos;
}