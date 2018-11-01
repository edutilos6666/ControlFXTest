package org.ddg.testExamples;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by edutilos on 01.11.18.
 */
public class ParallelTransitionExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        primaryStage.setScene(scene);
        registerEvents();
        primaryStage.setTitle("ParallelTransition Example");
        primaryStage.show();
    }


    private Scene scene;
    private VBox root;
    private Pane pane;
    private Rectangle rect;
    private Path path;
    private PathTransition pathTransition;
    private RotateTransition  rotateTransition;
    private ScaleTransition scaleTransition;
    private FillTransition fillTransition;
    private StrokeTransition strokeTransition;
    private ParallelTransition parallelTransition;
    private HBox hbControls;
    private Button btnPlay, btnPlayFromStart, btnPause, btnStop;

    private void addComponents() {
        root = new VBox();
        scene = new Scene(root, 500, 500);
        pane = new Pane();
        rect = new Rectangle(100, 120, 50, 50);
        rect.setFill(Color.BLUE);
        rect.setStroke(Color.GREEN);
        path = new Path();
        path.getElements().add(new MoveTo(100, 120));
        path.getElements().add(new CubicCurveTo(180, 60, 250, 340, 420, 240));
        path.getElements().add(new CubicCurveTo(420, 240, 250, 60, 180, 60));
        path.getElements().add(new MoveTo(100, 120));
        pane.getChildren().addAll(rect, path);
        pathTransition = new PathTransition(Duration.seconds(6), path, rect);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(2);
        rotateTransition = new RotateTransition(Duration.seconds(5), rect);
        rotateTransition.setByAngle(180);
//        rotateTransition.setAxis(Point3D.ZERO);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setCycleCount(2);
        scaleTransition = new ScaleTransition(Duration.seconds(4), rect);
        scaleTransition.setByX(2);
        scaleTransition.setByY(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        fillTransition = new FillTransition(Duration.seconds(5), rect, Color.BLUE, Color.GREEN);
        fillTransition.setAutoReverse(true);
        fillTransition.setCycleCount(2);
        strokeTransition = new StrokeTransition(Duration.seconds(5), rect, Color.GREEN,Color.BLUE);
        strokeTransition.setAutoReverse(true);
        strokeTransition.setCycleCount(2);
        parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(pathTransition, rotateTransition, scaleTransition,
                fillTransition, strokeTransition);
        parallelTransition.setOnFinished(evt-> {
            System.out.println("ParallelTransition finished.");
        });
//        parallelTransition.play();

        //controls
        hbControls = new HBox();
        btnPlay = new Button("Play");
        btnPlayFromStart = new Button("Play from start");
        btnPause = new Button("Pause");
        btnStop = new Button("Stop");
        hbControls.getChildren().addAll(btnPlay, btnPlayFromStart, btnPause, btnStop);
        root.getChildren().addAll(pane, hbControls);
        btnPlay.setDisable(false);
        btnPlayFromStart.setDisable(true);
        btnStop.setDisable(true);
        btnPause.setDisable(true);
    }

    private void registerEvents() {
        parallelTransition.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observable, Animation.Status oldValue, Animation.Status newValue) {
                if(newValue == Animation.Status.STOPPED) {
                    btnPlay.setDisable(false);
                    btnPlayFromStart.setDisable(false);
                    btnStop.setDisable(true);
                    btnPause.setDisable(true);
                } else if(newValue == Animation.Status.RUNNING) {
                    btnPlay.setDisable(true);
                    btnPlayFromStart.setDisable(true);
                    btnStop.setDisable(false);
                    btnPause.setDisable(false);
                } else if(newValue == Animation.Status.PAUSED) {
                    btnPlay.setDisable(false);
                    btnPlayFromStart.setDisable(false);
                    btnStop.setDisable(true);
                    btnPause.setDisable(true);
                }
            }
        });
        btnPlay.setOnAction(evt-> {
            parallelTransition.play();
        });
        btnPlayFromStart.setOnAction(evt-> {
            parallelTransition.playFromStart();
        });
        btnStop.setOnAction(evt-> {
            parallelTransition.stop();
        });
        btnPause.setOnAction(evt-> {
            parallelTransition.pause();
        });
    }
}
