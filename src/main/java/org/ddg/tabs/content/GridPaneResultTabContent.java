package org.ddg.tabs.content;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import static org.ddg.utils.Constants.DELIMITER;
import static org.ddg.utils.Constants.SEPARATOR;
import static org.ddg.utils.CustomAlerts.showErrorAlert;
import static org.ddg.utils.CustomAlerts.showInfoAlert;

public class GridPaneResultTabContent {
    private GridPane root;
    private Label lblId, lblName, lblPassword, lblAge, lblWage, lblActive;
    private TextField fieldId, fieldName, fieldAge, fieldWage, fieldActive;
    private PasswordField fieldPassword;
    private Button btnSubmit, btnClear;

    public GridPaneResultTabContent() {
        root = buildContent();
    }

    public GridPane buildContent() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        addComponents();
        root.getStylesheets().add("org/ddg/tabs/content/gridPaneResultTabContent.css");
        registerEvents();
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
