package org.ddg.taskExamples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by edutilos on 01.11.18.
 */
public class ScatterChartExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        primaryStage.setScene(scene);
        primaryStage.setTitle("ScatterChart Example");
        primaryStage.show();
    }

    private Scene scene;
    private VBox root;
    private Label lblTitle;
    private ScatterChart<String, Number> scatterChart;

    private void addComponents() {
        root = new VBox();
        scene = new Scene(root, 500, 500);
        lblTitle = new Label("ScatterChart Example");
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Browsers");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Market Value");

        scatterChart = new ScatterChart<String, Number>(xAxis, yAxis);
        List<Integer> years = Arrays.asList(2014, 2015, 2016, 2017, 2018, 2019);
        years.forEach(year-> {
            scatterChart.getData().add(generateRandomDataSeries(String.valueOf(year)));
        });
        root.getChildren().addAll(lblTitle, scatterChart);
    }

    private XYChart.Series<String,Number> generateRandomDataSeries(String name) {
        XYChart.Series<String,Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName(name);
        dataSeries.getData().addAll(
          generateChrome(),
                generateFirefox(),
                generateOpera(),
                generateSafari(),
                generateMicrosoftEdge(),
                generateIE12()
        );
        return dataSeries;
    }

    private double generateRandomDouble() {
        return new Random().nextDouble()*100;
    }
    private XYChart.Data<String,Number> generateChrome() {
        return new XYChart.Data<>("Chrome", generateRandomDouble());
    }
    private XYChart.Data<String,Number> generateFirefox() {
        return new XYChart.Data<>("Firefox", generateRandomDouble());
    }
    private XYChart.Data<String,Number> generateOpera() {
        return new XYChart.Data<>("Opera", generateRandomDouble());
    }
    private XYChart.Data<String,Number> generateMicrosoftEdge() {
        return new XYChart.Data<>("Microsoft Edge", generateRandomDouble());
    }
    private XYChart.Data<String,Number> generateSafari() {
        return new XYChart.Data<>("Safari", generateRandomDouble());
    }
    private XYChart.Data<String,Number> generateIE12() {
        return new XYChart.Data<>("IE 12", generateRandomDouble());
    }
}
