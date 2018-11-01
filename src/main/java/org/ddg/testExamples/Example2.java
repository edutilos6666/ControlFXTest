package org.ddg.testExamples;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import org.ddg.utils.Constants;
import org.ddg.utils.CustomAlerts;

/**
 * Created by edutilos on 29.10.18.
 */
public class Example2 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private VBox root;
    private Task<Boolean> task;
    private ScheduledService<Boolean> scheduledTask;
    private ProgressBar taskStatusBar;
    private Button btnStart, btnReset, btnCancel;
    private TextArea areaTaskOutputs;
    private final int MAX_PROGRESS = 5;
    private final int MIN_PROGESS = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        task = createTask();
        scheduledTask = new ScheduledService<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return task;
            }
        };
        scheduledTask.setDelay(Duration.seconds(1));
        scheduledTask.setPeriod(Duration.seconds(30));
        scheduledTask.setMaximumFailureCount(5);
        root = new VBox();
        addComponents();
        registerEvents();
        root.getChildren().add(generateSimpleForm());
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Task Example");
        primaryStage.show();

    }


    private GridPane generateSimpleForm() {
        GridPane rootForm = new GridPane();
        //id
        Text txtId = new Text("Id");
        TextField fieldId = TextFields.createClearableTextField();
        rootForm.addRow(0, txtId, fieldId);
        //name
        Text txtName = new Text("Name");
        TextField fieldName = TextFields.createClearableTextField();
        rootForm.addRow(1, txtName, fieldName);
        //password
        Text txtPassword = new Text("Password");
        PasswordField fieldPassword = TextFields.createClearablePasswordField();
        rootForm.addRow(2, txtPassword, fieldPassword);
        //buttons
        Button btnSubmit = new Button("Submit");
        Button btnClear = new Button("Clear");
        rootForm.addRow(3, btnSubmit, btnClear);

        btnSubmit.setOnAction(evt-> {
            try {
                long id = Long.parseLong(fieldId.getText());
                String name = fieldName.getText();
                String password = fieldPassword.getText();
                StringBuilder sb = new StringBuilder();
                sb.append("<<SimpleForm Submission>>").append(Constants.SEPARATOR)
                        .append("id = ").append(id).append(Constants.SEPARATOR)
                        .append("name = ").append(name).append(Constants.SEPARATOR)
                        .append("password = ").append(password).append(Constants.SEPARATOR);
                CustomAlerts.showInfoAlert(sb.toString());
            } catch(Exception ex) {
                CustomAlerts.showErrorAlert(ex.getMessage());
            }
        });
        btnClear.setOnAction(evt-> {
            fieldId.clear();
            fieldName.clear();
            fieldPassword.clear();
        });
        return rootForm;
    }


    private void addComponents() {
        taskStatusBar = new ProgressBar();
        root.getChildren().add(taskStatusBar);
        btnStart = new Button("Start Task");
        btnReset = new Button("Reset Task");
        btnCancel = new Button("Cancel Task");
        HBox hbButtons = new HBox();
        hbButtons.getChildren().addAll(btnStart, btnReset, btnCancel);
        root.getChildren().add(hbButtons);
        areaTaskOutputs = new TextArea();
        areaTaskOutputs.setPrefRowCount(20);
        areaTaskOutputs.setPrefColumnCount(20);
        root.getChildren().add(areaTaskOutputs);
    }

    private boolean onceStarted = false;
    private void registerEvents() {
        ObservableBooleanValue isCancelledProperty = new SimpleBooleanProperty(!task.isCancelled());
//        btnStart.disableProperty().bind(task.runningProperty());
//        btnCancel.disableProperty().bind(task.runningProperty().not());
//        btnReset.disableProperty().bind(btnStart.disabledProperty().not().and(btnCancel.disabledProperty().not()).or(btnCancel.pressedProperty()));
        taskStatusBar.progressProperty().bind(task.progressProperty());
        areaTaskOutputs.textProperty().bind(task.messageProperty());
        btnStart.setOnAction(evt-> {
            if(onceStarted) {
                scheduledTask.start();
            } else {
                scheduledTask.start();
                onceStarted = true;
                btnStart.setText("Restart");
            }
        });
        btnReset.setOnAction(evt-> {
//            if(scheduledTask.isAlive() && !scheduledTask.isInterrupted())
            scheduledTask.reset();
        });
        btnCancel.setOnAction(evt-> {
            if(task.isRunning()) {
                scheduledTask.cancel();
            }
        });
    }



    private Task<Boolean> createTask() {
        Task<Boolean> task =  new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                for(int i=0; i<= MAX_PROGRESS; ++i) {
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException ex) {
                        updateMessage("Interrupted(or Cancelled)");
                        break;
                    }
                    /*if(isCancelled()) {
                        updateMessage("Cancelled");
                        break;
                    }*/
                    updateMessage(String.format("Content %d", i));
                    updateProgress(i, MAX_PROGRESS);
                }
                return true;
            }
        };


        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double totalWork = event.getSource().getTotalWork();
                double workDone = event.getSource().getWorkDone();
                System.out.println(String.format("setOnCancelled: totalWork = %.2f, workDone = %.2f", totalWork, workDone));
                btnStart.setDisable(true);
                btnReset.setDisable(false);
                btnCancel.setDisable(true);
            }
        });

        task.setOnScheduled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double totalWork = event.getSource().getTotalWork();
                double workDone = event.getSource().getWorkDone();
                System.out.println(String.format("setOnScheduled: totalWork = %.2f, workDone = %.2f", totalWork, workDone));
                btnStart.setDisable(false);
                btnReset.setDisable(true);
                btnCancel.setDisable(true);
            }
        });

        task.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double totalWork = event.getSource().getTotalWork();
                double workDone = event.getSource().getWorkDone();
                System.out.println(String.format("setOnRunning: totalWork = %.2f, workDone = %.2f", totalWork, workDone));
                btnStart.setDisable(false);
                btnReset.setDisable(false);
                btnCancel.setDisable(false);
            }
        });

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double totalWork = event.getSource().getTotalWork();
                double workDone = event.getSource().getWorkDone();
                System.out.println(String.format("setOnSucceeded: totalWork = %.2f, workDone = %.2f", totalWork, workDone));
//                Platform.exit();
                btnStart.setText("Restart Task");
                btnStart.setDisable(false);
//                taskStatusBar.progressProperty().unbind();
//                taskStatusBar.setProgress(0);
                btnReset.setDisable(true);
                btnCancel.setDisable(true);
            }
        });

        task.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double totalWork = event.getSource().getTotalWork();
                double workDone = event.getSource().getWorkDone();
                System.out.println(String.format("setOnFailed: totalWork = %.2f, workDone = %.2f", totalWork, workDone));
//                Platform.exit();
                btnStart.setDisable(true);
                btnReset.setDisable(true);
                btnCancel.setDisable(true);
            }
        });

        task.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
            }
        });

        return task;
    }
}
