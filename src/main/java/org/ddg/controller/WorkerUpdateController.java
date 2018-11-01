package org.ddg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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
            Scene scene = fieldAge.getScene();
            if(scene == null) return;
            scene.setCursor(Cursor.WAIT);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    dao.update(id, new Worker(id, fname, lname, age, wage, active, activities, country, city, street, plz));
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
    }

    private void closeScene() {
        ((Stage)btnCancel.getScene().getWindow()).close();
    }
}
