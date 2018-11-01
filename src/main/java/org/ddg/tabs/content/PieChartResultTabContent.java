package org.ddg.tabs.content;

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
public class PieChartResultTabContent {

    public VBox buildContent() {
        addComponents();
        return root;
    }

    private VBox root;
    private Label lblTitle;
    private PieChart pieChart;

    private void addComponents() {
        root = new VBox();
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
