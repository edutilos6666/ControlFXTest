package org.ddg.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.ddg.model.Worker;

import java.util.List;

/**
 * Created by edutilos on 26.10.18.
 */
public class WorkerDetailsController {
    @FXML
    private TextField fieldId, fieldFname, fieldLname, fieldAge, fieldWage, fieldActive,
    fieldCountry, fieldCity, fieldStreet, fieldPlz;
    @FXML
    private ListView<String> lvActivities;
    @FXML
    private Button btnClose;
    private Worker selectedWorker;
    public void setSelectedWorker(Worker selectedWorker) {
        this.selectedWorker = selectedWorker;
        fieldId.setText(selectedWorker.getId()+"");
        fieldFname.setText(selectedWorker.getFname());
        fieldLname.setText(selectedWorker.getLname());
        fieldAge.setText(selectedWorker.getAge()+"");
        fieldWage.setText(selectedWorker.getWage()+"");
        fieldActive.setText(selectedWorker.isActive()+"");
        fieldCountry.setText(selectedWorker.getCountry());
        fieldCity.setText(selectedWorker.getCity());
        fieldStreet.setText(selectedWorker.getStreet());
        fieldPlz.setText(selectedWorker.getPlz());
        lvActivities.setItems(selectedWorker.getActivities());
    }
    @FXML
    public void initialize() {
        ObservableList<Node> nodes = FXCollections.observableArrayList(
                btnClose, fieldId, fieldFname, fieldLname,
                fieldAge, fieldWage, fieldActive, fieldCountry,
                fieldCity, fieldStreet, fieldPlz, lvActivities
        );

        nodes.forEach(n-> n.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.ENTER) closeScene();
        }));

        registerEvents();
    }

    private void registerEvents() {
        btnClose.requestFocus();
        btnClose.setOnAction(evt-> {
            closeScene();
        });

        btnClose.setOnKeyPressed(evt -> {
            if(evt.getCode() == KeyCode.ENTER)
                closeScene();
        });


    }

    public void registerKeyBindings(Scene scene) {
        scene.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.ESCAPE) {
                closeScene();
            }
        });
    }

    private void closeScene() {
        ((Stage)btnClose.getScene().getWindow()).close();
    }
}
