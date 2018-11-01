package org.ddg.testExamples;

import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by edutilos on 01.11.18.
 */
public class SimpleStage extends Stage {
    private SimpleWorkerRepo repo;
    public SimpleStage(SimpleWorkerRepo repo) {
        this.repo = repo;
        addComponents();
        registerEvents();
    }
    private Scene scene;
    private VBox root;
    private TableView<SimpleWorker> tvWorker;
    private Button btnClose;

    private void addComponents() {
        root = new VBox();
        tvWorker = new TableView<>();
        TableColumn<SimpleWorker, String> colName = new TableColumn<>();
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<SimpleWorker, Integer> colAge = new TableColumn<>();
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tvWorker.getColumns().addAll(colName, colAge);
        btnClose = new Button("Close Window");
        root.getChildren().addAll(tvWorker, btnClose);

        scene = new Scene(root, 500, 500);
        scene.setCursor(Cursor.WAIT);
        Task<List<SimpleWorker>> task = taskFetchWorkers();
        task.setOnSucceeded(e-> {
            scene.setCursor(Cursor.DEFAULT);
            tvWorker.getItems().addAll(task.getValue());
            }
        );
        new Thread(task).start();

        this.setScene(scene);
        this.setTitle("Another Stage");
    }

    private void registerEvents() {
        btnClose.setOnAction(evt-> {
            this.close();
        });
    }

    private Task<List<SimpleWorker>> taskFetchWorkers() {
        return new Task<List<SimpleWorker>>() {
            @Override
            protected List<SimpleWorker> call() throws Exception {
                return repo.fetchWorkers();
            }
        };
    }
}
