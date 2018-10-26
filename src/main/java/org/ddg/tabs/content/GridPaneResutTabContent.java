package org.ddg.tabs.content;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static org.ddg.utils.Constants.delimiter;
import static org.ddg.utils.Constants.separator;
import static org.ddg.utils.CustomAlerts.showErrorAlert;
import static org.ddg.utils.CustomAlerts.showInfoAlert;

public class GridPaneResutTabContent {
    private GridPane root;
    private Label lblId, lblName, lblPassword, lblAge, lblWage, lblActive;
    private TextField fieldId, fieldName, fieldAge, fieldWage, fieldActive;
    private PasswordField fieldPassword;
    private Button btnSubmit, btnClear;

    public GridPaneResutTabContent() {
        root = buildContent();
    }

    public GridPane buildContent() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        addComponents();
        root.getStylesheets().add("org/ddg/tabs/content/gridPaneResultTabContent.css");
        return root;
    }

    private void addComponents() {
        //Id
        lblId = new Label("Id");
        fieldId = new TextField();
        fieldId.setPromptText("Insert id");
        //Name
        lblName = new Label("Name");
        fieldName = new TextField();
        fieldName.setPromptText("Insert name");
        //Password
        lblPassword = new Label("Password");
        fieldPassword = new PasswordField();
        fieldPassword.setPromptText("Insert password");
        //Age
        lblAge = new Label("Age");
        fieldAge = new TextField();
        fieldAge.setPromptText("Insert age");
        //Wage
        lblWage = new Label("Wage");
        fieldWage = new TextField();
        fieldWage.setPromptText("Insert wage");
        //Active
        lblActive = new Label("Active");
        fieldActive = new TextField();
        fieldActive.setPromptText("Insert active");
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
                sb.append(lblId.getText()).append(delimiter).append(fieldId.getText()).append(separator)
                        .append(lblName.getText()).append(delimiter).append(fieldName.getText()).append(separator)
                        .append(lblPassword.getText()).append(delimiter).append(fieldPassword.getText()).append(separator)
                        .append(lblAge.getText()).append(delimiter).append(fieldAge.getText()).append(separator)
                        .append(lblWage.getText()).append(delimiter).append(fieldWage.getText()).append(separator)
                        .append(lblActive.getText()).append(delimiter).append(fieldActive.getText()).append(separator);
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
