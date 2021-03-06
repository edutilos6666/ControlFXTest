package org.ddg.tabs.content;

import javafx.animation.*;
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
public class SequentialTransitionTimelineResultTabContent  {

    public VBox buildContent() {
        addComponents();
        registerEvents();
        return root;
    }

    private VBox root;
    private Circle circle;
    private SequentialTransition sequentialTransition;
    private Timeline tl1, tl2, tl3;
    private HBox hbControls;
    private Button btnPlay, btnPlayFromStart, btnStop, btnPause;

    private void addComponents() {
        root = new VBox();
        root.setSpacing(100);
        circle = new Circle(50, 50, 10, Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        AnchorPane ap = new AnchorPane();
        ap.getChildren().add(circle);
        root.getChildren().add(ap);

        tl1 = new Timeline();
        tl1.setAutoReverse(true);
        tl1.setCycleCount(2);
        KeyValue kv1 = new KeyValue(circle.centerXProperty(), 300);
        KeyFrame kf1 = new KeyFrame(Duration.seconds(2), kv1);
        tl1.getKeyFrames().add(kf1);

        tl2 = new Timeline();
        tl2.setAutoReverse(true);
        tl2.setCycleCount(2);
        KeyValue kv2 = new KeyValue(circle.centerYProperty(), 300);
        KeyFrame kf2 = new KeyFrame(Duration.seconds(2), kv2);
        tl2.getKeyFrames().add(kf2);

        tl3 = new Timeline();
        tl3.setAutoReverse(true);
        tl3.setCycleCount(2);
        KeyValue kv3 = new KeyValue(circle.radiusProperty(), 30);
        KeyFrame kf3 = new KeyFrame(Duration.seconds(2), kv3);
        tl3.getKeyFrames().add(kf3);

        sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(tl1, tl2, tl3);

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
        sequentialTransition.setOnFinished(evt-> {
            System.out.println("SequentialTransition finished.");
        });
        sequentialTransition.statusProperty().addListener(new ChangeListener<Animation.Status>() {
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
            sequentialTransition.play();
        });
        btnPlayFromStart.setOnAction(evt-> {
            sequentialTransition.playFromStart();
        });
        btnPause.setOnAction(evt-> {
            sequentialTransition.pause();
        });
        btnStop.setOnAction(evt-> {
            sequentialTransition.stop();
        });
    }
}
