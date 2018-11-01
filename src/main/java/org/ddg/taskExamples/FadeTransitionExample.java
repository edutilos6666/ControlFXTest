package org.ddg.taskExamples;


import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by edutilos on 01.11.18.
 */
public class FadeTransitionExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        primaryStage.setScene(scene);
        fadeTransition = new FadeTransition(Duration.seconds(3), rect);
        registerEvents();
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(evt-> {
            System.out.println("FadeTransition is finished.");
        });
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setByValue(0.1);
        fadeTransition.play();
        primaryStage.setTitle("FadeTransitionExample");
        primaryStage.show();
    }

    private Scene scene;
    private VBox root;
    private Rectangle rect;
    private Button btnPlay, btnPlayFromStart, btnStop, btnPause;
    private FadeTransition fadeTransition;

    private void addComponents() {
        root = new VBox();
        scene = new Scene(root, 500, 500);
        rect = new Rectangle(10, 10, 300, 200);
        rect.setFill(Color.GREEN);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(2.0);
        root.getChildren().add(rect);
        btnPlay = new Button("Start");
        btnPlayFromStart = new Button("Restart");
        btnStop = new Button("Stop");
        btnPause = new Button("Pause");
        HBox hbControls = new HBox();
        hbControls.getChildren().addAll(btnPlay, btnPlayFromStart, btnStop, btnPause);
        root.getChildren().add(hbControls);
        btnPlay.setDisable(false);
        btnPlayFromStart.setDisable(true);
        btnStop.setDisable(true);
        btnPause.setDisable(true);
    }

    private void registerEvents() {
        fadeTransition.statusProperty().addListener(new ChangeListener<Animation.Status>() {
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
            fadeTransition.play();
        });
        btnPlayFromStart.setOnAction(evt-> {
            fadeTransition.playFromStart();
        });
        btnStop.setOnAction(evt-> {
            fadeTransition.stop();
        });
        btnPause.setOnAction(evt-> {
            fadeTransition.pause();
        });
    }


}
