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
public class WorkerWithDateUpdateController {
    @FXML
    private TextField fieldId, fieldFname, fieldLname, fieldAge, fieldWage, fieldActive,
    fieldCountry, fieldCity, fieldStreet, fieldPlz, fieldHeute;
    @FXML
    private ListView<String> lvActivities;
    @FXML
    private DatePicker pickerDateFrom, pickerDateTo, pickerSqlDateFrom, pickerSqlDateTo;
    @FXML
    private Button btnUpdate, btnClear, btnCancel;
    private WorkerWithDateDAO dao;
    private WorkerWithDate worker;

    public void setDAOAndWorker(WorkerWithDateDAO dao, WorkerWithDate worker) {
        this.dao = dao;
        this.worker = worker;
        fieldId.setText(worker.getId()+"");
        fieldFname.setText(worker.getFname());
        fieldLname.setText(worker.getLname());
        fieldAge.setText(worker.getAge()+"");
        fieldWage.setText(worker.getWage()+"");
        fieldActive.setText(worker.isActive()+"");
        fieldCountry.setText(worker.getCountry());
        fieldCity.setText(worker.getCity());
        fieldStreet.setText(worker.getStreet());
        fieldPlz.setText(worker.getPlz());
        pickerDateFrom.setValue(JavafxControllerUtils.convertUtilDateToLocalDate(worker.getDateFrom()));
        pickerDateTo.setValue(JavafxControllerUtils.convertUtilDateToLocalDate(worker.getDateTo()));
        pickerSqlDateFrom.setValue(JavafxControllerUtils.convertSqlDateToLocalDate(worker.getSqlDateFrom()));
        pickerSqlDateTo.setValue(JavafxControllerUtils.convertSqlDateToLocalDate(worker.getSqlDateTo()));
        fieldHeute.setText(Constants.TIME_FORMAT_1.format(worker.getHeute()));
        //because of hibernate
   /*     worker.getActivities().forEach(act-> {
            lvActivities.getSelectionModel().select(act);
        });*/
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
        btnUpdate.setOnAction(e-> {
            executeUpdate();
        });

        btnClear.setOnAction(e-> {
            executeClear();
        });

        btnCancel.setOnAction(e-> {
            closeScene();
        });

        btnUpdate.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.ENTER)
                executeUpdate();
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

    private void executeUpdate() {
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
            dao.update(id, new WorkerWithDate(id, fname, lname, age, wage, active, activities, country, city, street, plz,
                    dateFrom, dateTo, sqlDateFrom, sqlDateTo, heute));
            closeScene();
        } catch(Exception ex) {
            CustomAlerts.showErrorAlert(ex.getMessage());
        }
    }

    private void executeClear() {
        fieldId.setText(worker.getId()+"");
        fieldFname.setText(worker.getFname());
        fieldLname.setText(worker.getLname());
        fieldAge.setText(worker.getAge()+"");
        fieldWage.setText(worker.getWage()+"");
        fieldActive.setText(worker.isActive()+"");
        fieldCountry.setText(worker.getCountry());
        fieldCity.setText(worker.getCity());
        fieldStreet.setText(worker.getStreet());
        fieldPlz.setText(worker.getPlz());
        lvActivities.getSelectionModel().clearSelection();
        worker.getActivities().forEach(act-> {
            lvActivities.getSelectionModel().select(act);
        });
        pickerDateFrom.setValue(JavafxControllerUtils.convertUtilDateToLocalDate(worker.getDateFrom()));
        pickerDateTo.setValue(JavafxControllerUtils.convertUtilDateToLocalDate(worker.getDateTo()));
        pickerSqlDateFrom.setValue(JavafxControllerUtils.convertSqlDateToLocalDate(worker.getSqlDateFrom()));
        pickerSqlDateTo.setValue(JavafxControllerUtils.convertSqlDateToLocalDate(worker.getSqlDateTo()));
        fieldHeute.setText(Constants.TIME_FORMAT_1.format(worker.getHeute()));
    }

    private void closeScene() {
        ((Stage)btnCancel.getScene().getWindow()).close();
    }
}
