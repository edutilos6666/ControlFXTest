package org.ddg.tabs.content;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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
public class LineChartResultTabContent {

    public VBox buildContent() {
        addComponents();
        return root;
    }

    private VBox root;
    private Label lblTitle;
    private LineChart<Number, Number> lineChart;

    private void addComponents() {
        root = new VBox();
        lblTitle = new Label("LineChart Example");
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Cities");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population Size");

        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        List<String> cities = Arrays.asList("Baku", "Istanbul", "Ankara", "Gence", "Moscow", "Berlin");
        cities.forEach(city-> {
            lineChart.getData().add(generateRandomDataSeries(city));
        });
        root.getChildren().addAll(lblTitle, lineChart);
    }

    private XYChart.Series<Number,Number> generateRandomDataSeries(String name) {
        XYChart.Series<Number,Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName(name);
        dataSeries.getData().addAll(
                generateData(),
                generateData(),
                generateData(),
                generateData(),
                generateData(),
                generateData()
        );
        return dataSeries;
    }

    private double generateRandomDouble() {
        return new Random().nextDouble()*100;
    }
    private XYChart.Data<Number,Number> generateData() {
        return new XYChart.Data<>(generateRandomDouble(), generateRandomDouble());
    }

}
