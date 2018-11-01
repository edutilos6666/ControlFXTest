package org.ddg.taskExamples;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by edutilos on 01.11.18.
 */
public class PathTransitionExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        primaryStage.setScene(scene);
        primaryStage.setTitle("PathTransition Example");
        primaryStage.show();
    }
    private Scene scene;
    private Pane root;
    private Path path;
    private Circle circle;

    private void addComponents() {
        root = new Pane();
        scene = new Scene(root, 500, 500);
        path = new Path();
        path.getElements().add(new MoveTo(20, 120));
        path.getElements().add(new CubicCurveTo(180, 60, 250, 340, 420, 240));
        path.getElements().add(new CubicCurveTo(420, 240, 250, 60, 180, 60));
        path.getElements().add(new MoveTo(20, 120));
        circle = new Circle(20, 120, 10, Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        root.getChildren().addAll(path, circle);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(6));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(2);
        pathTransition.setOnFinished(evt-> {
            System.out.println("PathTransition is finished");
        });
        pathTransition.play();
    }


}
