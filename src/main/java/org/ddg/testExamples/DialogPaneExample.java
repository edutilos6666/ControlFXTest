package org.ddg.testExamples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ddg.utils.CustomDialogs;

import java.util.Optional;

/**
 * Created by edutilos on 06.11.18.
 */
public class DialogPaneExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        registerEvents();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dialog(Pane) Example");
        primaryStage.show();
    }

    private Scene scene;
    private VBox root;
    private TextArea areaCriteria;
    private Button btnSubmit;

    private void addComponents() {
        root = new VBox();
        scene = new Scene(root, 600, 600);
        areaCriteria = new TextArea();
        btnSubmit = new Button("Submit");
        root.getChildren().addAll(areaCriteria, btnSubmit);
    }

    private void registerEvents() {
        btnSubmit.setOnAction(evt-> {
            Optional<ButtonType> answer = CustomDialogs.showConfirmationDialog("Warning",
                    "Are you sure?");
            System.out.println(answer.get().getButtonData());
            answer = CustomDialogs.showInfoWarningErrorDialog("Warning", "Are You sure?");
            System.out.println(answer.get().getButtonData());
        });

        btnSubmit.setOnKeyPressed(evt-> {
           if(evt.getCode().equals(KeyCode.ENTER)) {
               btnSubmit.fire();
           } else if(evt.getCode().equals(KeyCode.BACK_SPACE)) {
               evt.consume();
           }
        });
    }
}
