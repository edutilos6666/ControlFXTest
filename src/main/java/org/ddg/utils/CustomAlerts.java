package org.ddg.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class CustomAlerts {
    public static Optional<ButtonType> showInfoAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(content);
        alert.setContentText(null);
        return alert.showAndWait();
    }

    public static Optional<ButtonType> showWarningAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(content);
        alert.setContentText(null);
        return alert.showAndWait();
    }

    public static Optional<ButtonType> showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(content);
        alert.setContentText(null);
        return alert.showAndWait();
    }

}
