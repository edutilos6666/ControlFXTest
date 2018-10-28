package org.ddg.tabs.content;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.Arrays;
import java.util.Collection;

import static org.ddg.utils.Constants.DELIMITER;
import static org.ddg.utils.Constants.SEPARATOR;
import static org.ddg.utils.CustomAlerts.showErrorAlert;
import static org.ddg.utils.CustomAlerts.showInfoAlert;

public class ControlsFXTextFieldsResultTabContent {
    private GridPane root;
    private Label lblId, lblName, lblPassword, lblAge, lblWage, lblActive;
    private TextField fieldId, fieldName, fieldAge, fieldWage, fieldActive;
    private PasswordField fieldPassword;
    private Button btnSubmit, btnClear;

    public ControlsFXTextFieldsResultTabContent() {
        root = buildContent();
    }

    public GridPane buildContent() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        addComponents();
//        root.getStylesheets().add("org/ddg/tabs/content/gridPaneResultTabContent.css");
        registerEvents();
        return root;
    }

    private void addComponents() {
        //Id
        lblId = new Label("Id");
        fieldId = TextFields.createClearableTextField();
        fieldId.setPromptText("Insert id");
        TextFields.bindAutoCompletion(fieldId, 1, 11, 111, 1111, 11111, 1111111, 11111111);
        //Name
        lblName = new Label("Name");
        fieldName = TextFields.createClearableTextField();
        fieldName.setPromptText("Insert name");
        TextFields.bindAutoCompletion(fieldName, "foo", "foo-old", "foo-new", "foo-young", "foo-student", "foo-teacher");
        //Password
        lblPassword = new Label("Password");
        fieldPassword = TextFields.createClearablePasswordField();
        fieldPassword.setPromptText("Insert password");

        //Age
        lblAge = new Label("Age");
        fieldAge = TextFields.createClearableTextField();
        fieldAge.setPromptText("Insert age");
        TextFields.bindAutoCompletion(fieldAge, 10, 100, 1000, 10000);

        //Wage
        lblWage = new Label("Wage");
        fieldWage = TextFields.createClearableTextField();
        fieldWage.setPromptText("Insert wage");
        TextFields.bindAutoCompletion(fieldWage, 10.0, 100.0, 1000.0, 10000.0, 100000.0);

        //Active
        lblActive = new Label("Active");
        fieldActive = TextFields.createClearableTextField();
        fieldActive.setPromptText("Insert active");
        TextFields.bindAutoCompletion(fieldActive, true, false);
        //Buttons
        btnSubmit = new Button("Submit");
        btnClear = new Button("Clear");
        root.addRow(0, lblId, fieldId);
        root.addRow(1, lblName, fieldName);
        root.addRow(2, lblPassword, fieldPassword);
        root.addRow(3, lblAge, fieldAge);
        root.addRow(4, lblWage, fieldWage);
        root.addRow(5, lblActive, fieldActive);
        root.addRow(6, btnSubmit, btnClear);
    }

    private void registerEvents() {
        btnSubmit.setOnAction(evt-> {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(lblId.getText()).append(DELIMITER).append(Integer.parseInt(fieldId.getText())).append(SEPARATOR)
                        .append(lblName.getText()).append(DELIMITER).append(fieldName.getText()).append(SEPARATOR)
                        .append(lblPassword.getText()).append(DELIMITER).append(fieldPassword.getText()).append(SEPARATOR)
                        .append(lblAge.getText()).append(DELIMITER).append(Integer.parseInt(fieldAge.getText())).append(SEPARATOR)
                        .append(lblWage.getText()).append(DELIMITER).append(Double.parseDouble(fieldWage.getText())).append(SEPARATOR)
                        .append(lblActive.getText()).append(DELIMITER).append(Boolean.parseBoolean(fieldActive.getText())).append(SEPARATOR);
                showInfoAlert(sb.toString());
            } catch(Exception ex) {
                showErrorAlert(ex.getMessage());
            }
        });

        btnClear.setOnAction(evt-> {
            fieldId.clear();
            fieldName.clear();
            fieldPassword.clear();
            fieldAge.clear();
            fieldWage.clear();
            fieldActive.clear();
        });
    }

}
