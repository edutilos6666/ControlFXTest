package org.ddg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.ddg.dao.WorkerWithDateDAO;
import org.ddg.model.WorkerWithDate;
import org.ddg.utils.Constants;
import org.ddg.utils.CustomAlerts;
import org.ddg.utils.JavafxControllerUtils;

import java.sql.Time;
import java.util.Date;

/**
 * Created by edutilos on 26.10.18.
 */
public class WorkerWithDateCreateController {
    @FXML
    private TextField fieldId, fieldFname, fieldLname, fieldAge, fieldWage, fieldActive,
    fieldCountry, fieldCity, fieldStreet, fieldPlz, fieldHeute;
    @FXML
    private ListView<String> lvActivities;
    @FXML
    private DatePicker pickerDateFrom, pickerDateTo, pickerSqlDateFrom, pickerSqlDateTo;
    @FXML
    private Button btnCreate, btnClear, btnCancel;
    private WorkerWithDateDAO dao;

    public void setDAO(WorkerWithDateDAO dao) {
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
            Date dateFrom = JavafxControllerUtils.convertLocalDateToUtilDate(pickerDateFrom.getValue());
            Date dateTo = JavafxControllerUtils.convertLocalDateToUtilDate(pickerDateTo.getValue());
            java.sql.Date sqlDateFrom = JavafxControllerUtils.convertLocalDateToSqlDate(pickerSqlDateFrom.getValue());
            java.sql.Date sqlDateTo = JavafxControllerUtils.convertLocalDateToSqlDate(pickerSqlDateTo.getValue());
            Time heute = new Time(Constants.TIME_FORMAT_1.parse(fieldHeute.getText()).getTime());
//            dao.create(new WorkerWithDate(id, fname, lname, age, wage, active, activities, country, city, street, plz));
            dao.create(new WorkerWithDate(fname, lname, age, wage, active, activities,
                    country, city, street, plz, dateFrom, dateTo, sqlDateFrom, sqlDateTo, heute));
            closeScene();
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
        fieldHeute.clear();
    }

    private void closeScene() {
        ((Stage)btnCancel.getScene().getWindow()).close();
    }
}
