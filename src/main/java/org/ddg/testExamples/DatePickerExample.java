package org.ddg.testExamples;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Created by edutilos on 02.11.18.
 */
public class DatePickerExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Scene scene;
    private VBox root;
    private Label lblTitle;
    private DatePicker datePicker;
    private Label lblErrorDatePicker;
    private Button btnSubmit;

    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        registerEvents();
        primaryStage.setScene(scene);
        primaryStage.setTitle("DatePicker Example");
        primaryStage.show();
    }

    private void addComponents() {
        root = new VBox();
        scene = new Scene(root, 500, 500);
        lblTitle = new Label("DatePicker");
        datePicker = new DatePicker();
        datePicker.setPromptText("dd/MM/yyyy");
//        datePicker.setEditable(false);
        lblErrorDatePicker = new Label();
        lblErrorDatePicker.setStyle("-fx-text-fill:red");
        btnSubmit = new Button("Submit form");
        root.getChildren().addAll(lblTitle, datePicker, lblErrorDatePicker, btnSubmit);
    }

    private void registerEvents() {
        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                System.out.println("valueProperty = "+newValue);
            }
        });

        datePicker.addEventFilter(KeyEvent.KEY_RELEASED, evt-> {
            try {
                String text = datePicker.getEditor().getText();
                String[] splitted = text.split("/");
//                System.out.printf("%d, %s, %s, %s\n", splitted.length, splitted[0], splitted[1], splitted[2]);
                if(splitted.length != 3) throw new Exception("");
                Integer day = Integer.parseInt(splitted[0]);
                Integer month = Integer.parseInt(splitted[1]);
                Integer year = Integer.parseInt(splitted[2]);
//                System.out.printf("%d, %d, %d\n", day, month, year);
                LocalDate ld = LocalDate.of(year, month , day);
                lblErrorDatePicker.setText("");
            } catch(Exception ex) {
                lblErrorDatePicker.setText("Wrong datePicker Value");
            }
        });


        btnSubmit.addEventFilter(ActionEvent.ACTION, evt-> {
            System.out.printf("datePickerValue = %s\n", datePicker.getValue());
        });
    }
}
