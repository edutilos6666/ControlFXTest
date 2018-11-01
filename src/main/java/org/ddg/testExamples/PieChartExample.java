package org.ddg.testExamples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Created by edutilos on 01.11.18.
 */
public class PieChartExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        primaryStage.setScene(scene);
        primaryStage.setTitle("PieChart Example");
        primaryStage.show();
    }
    private Scene scene;
    private VBox root;
    private Label lblTitle;
    private PieChart pieChart;

    private void addComponents() {
        root = new VBox();
        scene = new Scene(root, 500, 500);
        lblTitle = new Label("PieChart Example");
        pieChart = new PieChart();
        pieChart.getData().addAll(
                new PieChart.Data("Chrome", generateRandomValue()),
                new PieChart.Data("Firefox", generateRandomValue()),
                new PieChart.Data("Opera", generateRandomValue()),
                new PieChart.Data("Microsoft Edge", generateRandomValue()),
                new PieChart.Data("Safari", generateRandomValue()),
                new PieChart.Data("IE 12", generateRandomValue()));
        pieChart.setClockwise(true);
        pieChart.setStartAngle(100);
        pieChart.setAnimated(true);
        root.getChildren().addAll(lblTitle, pieChart);
    }

    private double generateRandomValue() {
        return new Random().nextDouble()*200;
    }
}
