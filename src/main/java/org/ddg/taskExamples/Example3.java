package org.ddg.taskExamples;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by edutilos on 01.11.18.
 */
public class Example3 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Scene scene;
    private VBox root;
    private TableView<SimpleWorker> tvWorker;
    private Button btnLoadDataFromDatabase, btnLoadAnotherStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        addComponents();
        scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        registerEvents();
        primaryStage.setTitle("Task Example");
        primaryStage.show();
    }

    private void addComponents() {
        root = new VBox();
        tvWorker = new TableView<>();
        TableColumn<SimpleWorker, String> colName = new TableColumn<>();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<SimpleWorker, Integer> colAge = new TableColumn<>();
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tvWorker.getColumns().addAll(colName, colAge);
        btnLoadDataFromDatabase = new Button("Load Data From Database");
        btnLoadAnotherStage = new Button("Load Another Stage");
        root.getChildren().addAll(tvWorker, btnLoadDataFromDatabase, btnLoadAnotherStage);
    }

    private void registerEvents() {
        btnLoadDataFromDatabase.setOnAction(evt-> {
            scene.setCursor(Cursor.WAIT);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    loadData();
                    return null;
                }
            };
            task.setOnSucceeded(e-> scene.setCursor(Cursor.DEFAULT));
            new Thread(task).start();
        });

        btnLoadAnotherStage.setOnAction(evt-> {
            Stage stage = new SimpleStage(new SimpleWorkerRepo());
            stage.showAndWait();
        });
    }

    private void loadData() {
        try {
            Thread.sleep(5000);
            tvWorker.getItems().addAll(
              new SimpleWorker("foo", 10),
                    new SimpleWorker("bar", 20),
                    new SimpleWorker("bim", 30),
                    new SimpleWorker("pako", 40)
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
