package org.ddg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.ddg.dao.WorkerDAO;
import org.ddg.model.Worker;
import org.ddg.utils.CustomAlerts;

/**
 * Created by edutilos on 26.10.18.
 */
public class WorkerUpdateController {
    @FXML
    private TextField fieldId, fieldFname, fieldLname, fieldAge, fieldWage, fieldActive,
    fieldCountry, fieldCity, fieldStreet, fieldPlz;
    @FXML
    private ListView<String> lvActivities;
    @FXML
    private Button btnUpdate, btnClear, btnCancel;
    private WorkerDAO dao;
    private Worker worker;

    public void setDAOAndWorker(WorkerDAO dao, Worker worker) {
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
        worker.getActivities().forEach(act-> {
            lvActivities.getSelectionModel().select(act);
        });
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
                dao.update(id, new Worker(id, fname, lname, age, wage, active, activities, country, city, street, plz));
                closeScene();
            } catch(Exception ex) {
                CustomAlerts.showErrorAlert(ex.getMessage());
            }
        });

        btnClear.setOnAction(e-> {
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
        });

        btnCancel.setOnAction(e-> {
            closeScene();
        });
    }

    private void closeScene() {
        ((Stage)btnCancel.getScene().getWindow()).close();
    }
}
