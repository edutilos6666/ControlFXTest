package org.ddg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.ddg.dao.WorkerDAO;
import org.ddg.model.Worker;
import org.ddg.utils.CustomAlerts;

import java.util.List;

/**
 * Created by edutilos on 26.10.18.
 */
public class WorkerCreateController {
    @FXML
    private TextField fieldId, fieldFname, fieldLname, fieldAge, fieldWage, fieldActive,
    fieldCountry, fieldCity, fieldStreet, fieldPlz;
    @FXML
    private ListView<String> lvActivities;
    @FXML
    private Button btnCreate, btnClear, btnCancel;
    private WorkerDAO dao;

    public void setDAO(WorkerDAO dao) {
        this.dao = dao;
    }
    @FXML
    public void initialize() {
        lvActivities.setItems(FXCollections.observableArrayList(
                "Reading","Writing", "Speaking", "Listening", "Swimming"
        ));
        lvActivities.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        registerEvents();
    }

    private void registerEvents() {
        btnCreate.setOnAction(e-> {
            executeCreate();
        });

        btnClear.setOnAction(e-> {
            executeClear();
        });

        btnCancel.setOnAction(e-> {
            closeScene();
        });

        btnCreate.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.ENTER)
                executeCreate();
        });

        btnClear.setOnKeyPressed(evt -> {
            if(evt.getCode() == KeyCode.ENTER)
                executeClear();
        });

        btnCancel.setOnKeyPressed(evt -> {
            if(evt.getCode() == KeyCode.ENTER)
                closeScene();
        });
    }

    public void registerKeyBindings(Scene scene)  {
        scene.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.ESCAPE) {
                closeScene();
            }
        });
    }



    private void executeCreate() {
        try {
            long id = Long.parseLong(fieldId.getText());
            String fname = fieldFname.getText();
            String lname = fieldLname.getText();
            int age = Integer.parseInt(fieldAge.getText());
            double wage = Double.parseDouble(fieldWage.getText());
            boolean active = Boolean.parseBoolean(fieldActive.getText());
            ObservableList<String> activities = lvActivities.getSelectionModel().getSelectedItems();
            String country = fieldCountry.getText();
            String city = fieldCity.getText();
            String street = fieldStreet.getText();
            String plz = fieldPlz.getText();
//            dao.create(new Worker(id, fname, lname, age, wage, active, activities, country, city, street, plz));
            Scene scene = fieldAge.getScene();
            if(scene == null) return;
            scene.setCursor(Cursor.WAIT);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    dao.create(new Worker(fname, lname, age, wage, active, activities, country, city, street, plz));
                    return null;
                }
            };
            task.setOnSucceeded(evt-> {
                scene.setCursor(Cursor.DEFAULT);
                closeScene();
            });

            new Thread(task).start();
        } catch(Exception ex) {
            CustomAlerts.showErrorAlert(ex.getMessage());
        }
    }

    private void executeClear() {
        fieldId.clear();
        fieldFname.clear();
        fieldLname.clear();
        fieldAge.clear();
        fieldWage.clear();
        fieldActive.clear();
        lvActivities.getSelectionModel().clearSelection();
        fieldCountry.clear();
        fieldCity.clear();
        fieldStreet.clear();
        fieldPlz.clear();
    }

    private void closeScene() {
        ((Stage)btnCancel.getScene().getWindow()).close();
    }
}
