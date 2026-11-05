module com.leydisystems.menuarboles {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.leydisystems.menuarboles.controller to javafx.fxml;
    opens com.leydisystems.menuarboles.classes to javafx.fxml;
    exports com.leydisystems.menuarboles;
}