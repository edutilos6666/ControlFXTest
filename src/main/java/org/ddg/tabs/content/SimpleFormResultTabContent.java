package org.ddg.tabs.content;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.ddg.utils.Constants;
import org.ddg.utils.CustomAlerts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.ddg.utils.Constants.DELIMITER;
import static org.ddg.utils.Constants.SEPARATOR;
import static org.ddg.utils.CustomAlerts.showErrorAlert;
import static org.ddg.utils.CustomAlerts.showInfoAlert;

public class SimpleFormResultTabContent {
    private VBox root;
    private Label lblName, lblPassword, lblDOB, lblColor, lblGender, lblReservation, lblTechnologiesKnown, lblEducationalQualification, lblLocation,
            lblSlider, lblFileChooser;
    private TextField fieldName;
    private PasswordField fieldPassword;
    private DatePicker pickerDOB;
    private ColorPicker pickerColor;
    private RadioButton btnGenderMale, btnGenderFemale;
    private ToggleButton btnReserveYes, btnReserveNo;
    private CheckBox cbTechJava, cbTechDotNet, cbTechAndroid, cbTechEmber;
    private ListView<String> lvEducationalQualification;
    private ChoiceBox<String> choiceBoxLocation;
    private Slider slider;
    private FileChooser fileChooser;

    private Button btnRegister, btnClear, btnChooseFile;
    String fileContent;

    public SimpleFormResultTabContent() {
        root = buildContent();
    }

    public VBox buildContent() {
        root = new VBox();
        root.setSpacing(10);
        addComponents();
//        root.getStylesheets().add("org/ddg/tabs/content/gridPaneResultTabContent.css");
        registerEvents();
        return root;
    }

    private void addComponents() {
       //name
        lblName = new Label("Name");
        fieldName = TextFields.createClearableTextField();
        fieldName.setPrefColumnCount(30);
        HBox hbName = new HBox();
        hbName.getChildren().addAll(lblName, fieldName);
        root.getChildren().add(hbName);
        //password
        lblPassword = new Label("Password");
        fieldPassword = TextFields.createClearablePasswordField();
        fieldPassword.setPrefColumnCount(30);
        HBox hbPassword = new HBox();
        hbPassword.getChildren().addAll(lblPassword, fieldPassword);
        root.getChildren().add(hbPassword);
        //dob
        lblDOB = new Label("Date of birth");
        pickerDOB = new DatePicker();
        HBox hbDOB = new HBox();
        hbDOB.getChildren().addAll(lblDOB, pickerDOB);
        root.getChildren().add(hbDOB);
        //color
        lblColor = new Label("Favourite Color");
        pickerColor = new ColorPicker();
        HBox hbColor = new HBox();
        hbColor.getChildren().addAll(lblColor, pickerColor);
        root.getChildren().add(hbColor);
        //gender
        lblGender = new Label("Gender");
        btnGenderMale = new RadioButton("Male");
        btnGenderFemale = new RadioButton("Female");
        ToggleGroup groupGender = new ToggleGroup();
        btnGenderMale.setToggleGroup(groupGender);
        btnGenderFemale.setToggleGroup(groupGender);
        HBox hbGender = new HBox();
        hbGender.getChildren().addAll(lblGender, btnGenderMale, btnGenderFemale);
        root.getChildren().add(hbGender);
        //reservation
        lblReservation = new Label("Reservation");
        btnReserveYes = new ToggleButton("Yes");
        btnReserveNo = new ToggleButton("No");
        ToggleGroup groupReservation = new ToggleGroup();
        btnReserveYes.setToggleGroup(groupReservation);
        btnReserveNo.setToggleGroup(groupReservation);
        HBox hbReservation = new HBox();
        hbReservation.getChildren().addAll(lblReservation, btnReserveYes, btnReserveNo);
        root.getChildren().add(hbReservation);
        //technologies
        lblTechnologiesKnown = new Label("Technologies Known");
        cbTechAndroid = new CheckBox("Android");
        cbTechJava = new CheckBox("Java");
        cbTechEmber = new CheckBox("Ember");
        cbTechDotNet = new CheckBox("DotNet");
        HBox hbTech = new HBox();
        hbTech.getChildren().addAll(lblTechnologiesKnown, cbTechAndroid, cbTechJava, cbTechEmber, cbTechDotNet);
        root.getChildren().add(hbTech);
        //educational qualification
        lblEducationalQualification = new Label("Educational qualification");
        lvEducationalQualification = new ListView<>();
        lvEducationalQualification.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvEducationalQualification.getItems().addAll(
          "Engineering", "MCA", "MBA", "Graduation", "MTECH", "Mphil", "Phd"
        );
        HBox hbEducationalQualification = new HBox();
        hbEducationalQualification.getChildren().addAll(lblEducationalQualification, lvEducationalQualification);
        root.getChildren().add(hbEducationalQualification);
        //location
        lblLocation = new Label("Location");
        choiceBoxLocation = new ChoiceBox<>();
        choiceBoxLocation.getItems().addAll(
          "Hyderabad", "Chennai", "Delhi", "Mumbai", "Viskakhapatnam"
        );choiceBoxLocation.getSelectionModel().select(0);
        HBox hbLocation = new HBox();
        hbLocation.getChildren().addAll(lblLocation, choiceBoxLocation);
        root.getChildren().add(hbLocation);
        //slider
        lblSlider = new Label("Slider");
        slider = new Slider(0, 200, 35);
        HBox hbSlider = new HBox();
        hbSlider.getChildren().addAll(lblSlider, slider);
        root.getChildren().add(hbSlider);
        //fileChooser
        lblFileChooser = new Label("Choose File");
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text Files", Arrays.asList("txt", "java", "csv", "xml", "fxml")));
        btnChooseFile = new Button("Choose");
        HBox hbFileChooser = new HBox();
        hbFileChooser.getChildren().addAll(lblFileChooser, btnChooseFile);
        root.getChildren().add(hbFileChooser);
        //buttons
        btnRegister = new Button("Register");
        btnClear = new Button("Clear");
        HBox hbButtons =new HBox();
        hbButtons.getChildren().addAll(btnRegister, btnClear);
        root.getChildren().add(hbButtons);
    }

    private void registerEvents() {
        btnRegister.setOnAction(evt-> {
            handleBtnRegister();
        });
        btnRegister.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.ENTER) handleBtnRegister();
        });

        btnClear.setOnAction(evt-> {
            handleBtnClear();
        });
        btnClear.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.ENTER) handleBtnClear();
        });

        btnChooseFile.setOnAction(evt-> {
            handleBtnChooseFile();
        });
        btnChooseFile.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.ENTER) handleBtnChooseFile();
        });
    }


    private void handleBtnRegister() {
        try {
            String name = fieldName.getText();
            String password = fieldPassword.getText();
            LocalDate dob = pickerDOB.getValue();
            Color color = pickerColor.getValue();
            String gender = "None";
            if(btnGenderMale.isSelected()) {
                gender = btnGenderMale.getText();
            } else if(btnGenderFemale.isSelected()) {
                gender = btnGenderFemale.getText();
            }
            String reservation = "None";
            if(btnReserveYes.isSelected()) {
                reservation= "Yes";
            } else if(btnReserveNo.isSelected()) {
                reservation = "No";
            }
            ObservableList<String> technologiesKnown = FXCollections.observableArrayList();
            Arrays.asList(cbTechJava, cbTechAndroid, cbTechEmber, cbTechDotNet).forEach(cb-> {
                if(cb.isSelected()) technologiesKnown.add(cb.getText());
            });
            ObservableList<String> educationalQualifications = lvEducationalQualification.getSelectionModel().getSelectedItems();
            String location = choiceBoxLocation.getSelectionModel().getSelectedItem();
            double sliderValue = slider.getValue();

            GridPane root = new GridPane();
            root.addRow(0, new Text("<<Infos about your selections>>"), new Label());
            root.addRow(1, new Text("Password"), new Label(password));
            root.addRow(2, new Text("Date Of Birth"), new Label(dob.toString()));
            root.addRow(3, new Text("Color"), new Label(color.toString()));
            root.addRow(4, new Text("Gender"), new Label(gender));
            root.addRow(5, new Text("Reservation"), new Label(reservation));
            root.addRow(6, new Text("TechnologiesKnown"), new Label(technologiesKnown.stream().collect(Collectors.joining("\n"))));
            root.addRow(7, new Text("Educational Qualifications"), new Label(educationalQualifications.stream().collect(Collectors.joining("\n"))));
            root.addRow(8, new Text("Location"), new Label(location));
            root.addRow(9, new Text("SliderValue"), new Label(sliderValue+""));
            root.addRow(10, new Text("Chosen File"), new Label(fileContent == null?"":fileContent));
            ScrollPane scrollPane = new ScrollPane(root);
            Scene scene = new Scene(scrollPane, 800, 800);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch(Exception ex) {
            CustomAlerts.showErrorAlert(ex.getMessage());
        }
    }

    /*
     private Label lblName, lblPassword, lblDOB, lblColor, lblGender, lblReservation, lblTechnologiesKnown, lblEducationalQualification, lblLocation,
         lblSlider, lblFileChooser;
  */
    private void handleBtnClear() {
        fieldName.clear();
        fieldPassword.clear();
        pickerDOB.setValue(null);
        pickerColor.setValue(null);
        btnGenderMale.setSelected(false);
        btnGenderFemale.setSelected(false);
        btnReserveYes.setSelected(false);
        btnReserveNo.setSelected(false);
        cbTechJava.setSelected(false);
        cbTechAndroid.setSelected(false);
        cbTechEmber.setSelected(false);
        cbTechDotNet.setSelected(false);
        lvEducationalQualification.getSelectionModel().clearSelection();
        choiceBoxLocation.getSelectionModel().select(0);
        slider.setValue(35);
    }
    private void handleBtnChooseFile() {
        File chosenFile = fileChooser.showOpenDialog(null);
        if(chosenFile != null) {
            try {
                fileContent =  String.join("\n", Files.readAllLines(chosenFile.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
