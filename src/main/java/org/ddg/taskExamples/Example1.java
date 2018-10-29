package org.ddg.taskExamples;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
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
import org.controlsfx.control.textfield.TextFields;
import org.ddg.utils.Constants;
import org.ddg.utils.CustomAlerts;

/**
 * Created by edutilos on 29.10.18.
 */
public class Example1  extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private VBox root;
    private Task<Boolean> task;
    private Thread taskThread;
    private ProgressBar taskStatusBar;
    private Button btnStart, btnStop, btnCancel;
    private TextArea areaTaskOutputs;
    private final int MAX_PROGRESS = 5;
    private final int MIN_PROGESS = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        task = createTask();
        taskThread = new Thread(task);
        taskThread.setDaemon(true); // that does not make any difference in this example
//        taskThread.start();
//        taskThread.interrupt();
//        taskThread.join();
        root = new VBox();
        addComponents();
        registerEvents();
        root.getChildren().add(generateSimpleForm());
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Task Example");
        primaryStage.show();
        taskThread.join();
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
        btnStop = new Button("Stop Task");
        btnCancel = new Button("Cancel Task");
        HBox hbButtons = new HBox();
        hbButtons.getChildren().addAll(btnStart, btnStop, btnCancel);
        root.getChildren().add(hbButtons);
        areaTaskOutputs = new TextArea();
        areaTaskOutputs.setPrefRowCount(20);
        areaTaskOutputs.setPrefColumnCount(20);
        root.getChildren().add(areaTaskOutputs);
    }

    private void registerEvents() {
        ObservableBooleanValue isCancelledProperty = new SimpleBooleanProperty(!task.isCancelled());
//        btnStart.disableProperty().bind(task.runningProperty());
//        btnCancel.disableProperty().bind(task.runningProperty().not());
//        btnStop.disableProperty().bind(btnStart.disabledProperty().not().and(btnCancel.disabledProperty().not()).or(btnCancel.pressedProperty()));
        taskStatusBar.progressProperty().bind(task.progressProperty());
        areaTaskOutputs.textProperty().bind(task.messageProperty());
        btnStart.setOnAction(evt-> {
            taskThread.start();
        });
        btnStop.setOnAction(evt-> {
//            if(taskThread.isAlive() && !taskThread.isInterrupted())
            if(task.isRunning())
                try {
                    taskThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
        btnCancel.setOnAction(evt-> {
            if(task.isRunning()) {
                task.cancel();
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
                btnStop.setDisable(true);
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
                btnStop.setDisable(true);
                btnCancel.setDisable(true);
            }
        });

        task.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double totalWork = event.getSource().getTotalWork();
                double workDone = event.getSource().getWorkDone();
                System.out.println(String.format("setOnRunning: totalWork = %.2f, workDone = %.2f", totalWork, workDone));
                btnStart.setDisable(true);
                btnStop.setDisable(false);
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
                btnStop.setDisable(true);
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
                btnStop.setDisable(true);
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
