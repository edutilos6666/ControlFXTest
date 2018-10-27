package org.ddg.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ddg.controller.MainController;

import java.io.IOException;

public class MainRunner extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainController controller = loader.getController();
        Scene scene = new Scene(loader.getRoot());
        primaryStage.setScene(scene);
        controller.registerKeyBindings(scene);
        primaryStage.setTitle("FX Sampler");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
