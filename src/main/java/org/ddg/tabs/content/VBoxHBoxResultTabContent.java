package org.ddg.tabs.content;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ddg.utils.CustomFieldValidator;

import static org.ddg.utils.Constants.DELIMITER;
import static org.ddg.utils.Constants.SEPARATOR;
import static org.ddg.utils.CustomAlerts.showErrorAlert;
import static org.ddg.utils.CustomAlerts.showInfoAlert;

public class VBoxHBoxResultTabContent {
    private VBox root;
    private Label lblId, lblName, lblPassword, lblAge, lblWage, lblActive;
    private Label lblIdError, lblNameError, lblPasswordError, lblAgeError, lblWageError, lblActiveError;
    private TextField fieldId, fieldName, fieldAge, fieldWage, fieldActive;
    private PasswordField fieldPassword;
    private Button btnSubmit, btnClear;

    public VBoxHBoxResultTabContent() {
        root = buildContent();
    }

    public VBox buildContent() {
        root = new VBox();
        addComponents();
        root.getStylesheets().add("org/ddg/tabs/content/vBoxHBoxResultTabContent.css");
        registerEvents();
        return root;
    }

    private void addComponents() {
        //Id
        lblId = new Label("Id");
        fieldId = new TextField();
        fieldId.setPromptText("Insert id");
        lblIdError = new Label();
        lblIdError.setId("lbl-error");
        HBox hbId = new HBox();
        hbId.setSpacing(10);
        hbId.getChildren().addAll(lblId, fieldId, lblIdError);
        //Name
        lblName = new Label("Name");
        fieldName = new TextField();
        fieldName.setPromptText("Insert name");
        lblNameError= new Label();
        lblNameError.setId("lbl-error");
        HBox hbName = new HBox();
        hbName.setSpacing(10);
        hbName.getChildren().addAll(lblName, fieldName, lblNameError);
        //Password
        lblPassword = new Label("Password");
        fieldPassword = new PasswordField();
        fieldPassword.setPromptText("Insert password");
        lblPasswordError= new Label();
        lblPasswordError.setId("lbl-error");
        HBox hbPassword = new HBox();
        hbPassword.setSpacing(10);
        hbPassword.getChildren().addAll(lblPassword, fieldPassword, lblPasswordError);
        //Age
        lblAge = new Label("Age");
        fieldAge = new TextField();
        fieldAge.setPromptText("Insert age");
        lblAgeError= new Label();
        lblAgeError.setId("lbl-error");
        HBox hbAge = new HBox();
        hbAge.setSpacing(10);
        hbAge.getChildren().addAll(lblAge, fieldAge, lblAgeError);
        //Wage
        lblWage = new Label("Wage");
        fieldWage = new TextField();
        fieldWage.setPromptText("Insert wage");
        lblWageError= new Label();
        lblWageError.setId("lbl-error");
        HBox hbWage = new HBox();
        hbWage.setSpacing(10);
        hbWage.getChildren().addAll(lblWage, fieldWage, lblWageError);
        //Active
        lblActive = new Label("Active");
        fieldActive = new TextField();
        fieldActive.setPromptText("Insert active");
        lblActiveError = new Label();
        lblActiveError.setId("lbl-error");
        HBox hbActive = new HBox();
        hbActive.setSpacing(10);
        hbActive.getChildren().addAll(lblActive, fieldActive, lblActiveError);
        //Buttons
        btnSubmit = new Button("Submit");
        btnClear = new Button("Clear");
        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10);
        hbButtons.getChildren().addAll(btnSubmit, btnClear);

        root.getChildren().addAll(hbId, hbName, hbPassword, hbAge, hbWage, hbActive, hbButtons);

//        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_TYPED,
//                "a", "",
//                KeyCode.UNDEFINED, false, false, false, false);
//        fieldId.fireEvent(keyEvent);
        batchValidate();
        setBindings();
    }

    private void setBindings() {
 /*       btnSubmit.disableProperty().bind(lblIdError.textProperty().isNotEmpty());
        btnSubmit.disableProperty().bind(lblNameError.textProperty().isNotEmpty());
        btnSubmit.disableProperty().bind(lblPasswordError.textProperty().isNotEmpty());
        btnSubmit.disableProperty().bind(lblAgeError.textProperty().isNotEmpty());
        btnSubmit.disableProperty().bind(lblWageError.textProperty().isNotEmpty());
        btnSubmit.disableProperty().bind(lblActiveError.textProperty().isNotEmpty());*/


        btnSubmit.disableProperty().bind(
                lblIdError.textProperty().isNotEmpty()
                .or(lblNameError.textProperty().isNotEmpty())
                .or(lblPasswordError.textProperty().isNotEmpty())
                .or(lblAgeError.textProperty().isNotEmpty())
                .or(lblWageError.textProperty().isNotEmpty())
                .or(lblActiveError.textProperty().isNotEmpty())
        );
        btnClear.disableProperty().bind(
                fieldId.textProperty().isEmpty()
                .and(fieldName.textProperty().isEmpty())
                .and(fieldPassword.textProperty().isEmpty())
                .and(fieldAge.textProperty().isEmpty())
                .and(fieldWage.textProperty().isEmpty())
                .and(fieldActive.textProperty().isEmpty())
        );
    }

    private void batchValidate() {
        lblIdError.setText(CustomFieldValidator.validateInteger(fieldId.getText()));
        lblNameError.setText(CustomFieldValidator.validateString(fieldName.getText()));
        lblPasswordError.setText(CustomFieldValidator.validateString(fieldPassword.getText()));
        lblAgeError.setText(CustomFieldValidator.validateInteger(fieldAge.getText()));
        lblWageError.setText(CustomFieldValidator.validateDouble(fieldWage.getText()));
        lblActiveError.setText(CustomFieldValidator.validateBoolean(fieldActive.getText()));
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
            batchValidate();
        });

        fieldId.setOnKeyReleased(e-> {
            lblIdError.setText(CustomFieldValidator.validateLong(fieldId.getText()));
        });
        fieldName.setOnKeyReleased(e-> {
            lblNameError.setText(CustomFieldValidator.validateString(fieldName.getText()));
        });
        fieldPassword.setOnKeyReleased(e-> {
            lblPasswordError.setText(CustomFieldValidator.validateString(fieldPassword.getText()));
        });
        fieldAge.setOnKeyReleased(e-> {
            lblAgeError.setText(CustomFieldValidator.validateInteger(fieldAge.getText()));
        });
        fieldWage.setOnKeyReleased(e-> {
            lblWageError.setText(CustomFieldValidator.validateDouble(fieldWage.getText()));
        });
        fieldActive.setOnKeyReleased(e-> {
            lblActiveError.setText(CustomFieldValidator.validateBoolean(fieldActive.getText()));
        });
    }

}
