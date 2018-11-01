package org.ddg.taskExamples;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by edutilos on 01.11.18.
 */
public class AnimationTimerExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private double opacity = 1;
    private Label lblName;
    private VBox root;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        primaryStage.setScene(scene);
        primaryStage.setTitle("AnimationTimer Example");
        AnimationTimer timer = new CustomAnimationTimer();
        timer.start();
        primaryStage.show();
    }

    private void addComponents() {
        root = new VBox();
        scene = new Scene(root, 500, 500);
        lblName = new Label("foobar");
        root.getChildren().add(lblName);
    }

    private class CustomAnimationTimer extends AnimationTimer {

        @Override
        public void handle(long now) {
            opacity -= 0.01;
            lblName.setOpacity(opacity);
            if(opacity <= 0) {
                this.stop();
                System.out.println("Animation stopped.");
            }
        }
    }
}
