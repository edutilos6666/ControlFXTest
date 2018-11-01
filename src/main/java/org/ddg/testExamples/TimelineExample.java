package org.ddg.testExamples;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by edutilos on 01.11.18.
 */
public class TimelineExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        registerEvents();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Timeline Example");
        primaryStage.show();
    }
    private Scene scene;
    private VBox root;
    private Circle circle;
    private Timeline timeline;
    private HBox hbControls;
    private Button btnPlay, btnPlayFromStart, btnStop, btnPause;

    private void addComponents() {
        root = new VBox();
        root.setSpacing(100);
        scene = new Scene(root, 500, 500);
        circle = new Circle(50, 50, 10, Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        AnchorPane ap = new AnchorPane();
        ap.getChildren().add(circle);
        root.getChildren().add(ap);

        timeline = new Timeline();
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2);
        KeyValue kv1 = new KeyValue(circle.centerXProperty(), 300);
        KeyValue kv2 = new KeyValue(circle.centerYProperty(), 300);
        KeyValue kv3 = new KeyValue(circle.radiusProperty(), 30);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(5), kv1, kv2, kv3);
        timeline.getKeyFrames().add(keyFrame);
        hbControls = new HBox();
        btnPlay = new Button("Play");
        btnPlayFromStart = new Button("Play from start");
        btnStop = new Button("Stop");
        btnPause = new Button("Pause");
        hbControls.getChildren().addAll(btnPlay, btnPlayFromStart, btnStop, btnPause);
        root.getChildren().add(hbControls);
        btnPlay.setDisable(false);
        btnPlayFromStart.setDisable(true);
        btnStop.setDisable(true);
        btnPause.setDisable(true);
    }

    private void registerEvents() {
        timeline.setOnFinished(evt-> {
            System.out.println("Timeline finished.");
        });
        timeline.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observable, Animation.Status oldValue, Animation.Status newValue) {
                if(newValue == Animation.Status.RUNNING) {
                    btnPlay.setDisable(true);
                    btnPlayFromStart.setDisable(true);
                    btnStop.setDisable(false);
                    btnPause.setDisable(false);
                } else if(newValue == Animation.Status.PAUSED) {
                    btnPlay.setDisable(false);
                    btnPlayFromStart.setDisable(false);
                    btnStop.setDisable(true);
                    btnPause.setDisable(true);
                } else if(newValue == Animation.Status.STOPPED) {
                    btnPlay.setDisable(false);
                    btnPlayFromStart.setDisable(false);
                    btnStop.setDisable(true);
                    btnPause.setDisable(true);
                }
            }
        });

        btnPlay.setOnAction(evt-> {
            timeline.play();
        });
        btnPlayFromStart.setOnAction(evt-> {
            timeline.playFromStart();
        });
        btnPause.setOnAction(evt-> {
            timeline.pause();
        });
        btnStop.setOnAction(evt-> {
            timeline.stop();
        });
    }
}
