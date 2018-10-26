package org.ddg.tabs.content;

import javafx.beans.property.ListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ddg.controller.WorkerCreateController;
import org.ddg.controller.WorkerDetailsController;
import org.ddg.controller.WorkerUpdateController;
import org.ddg.dao.WorkerDAO;
import org.ddg.dao.WorkerDAOMemImpl;
import org.ddg.model.Worker;
import org.ddg.utils.Constants;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TableViewResultTabContent {
    private BorderPane root;
    private TableView<Worker> workerTableView;
    private WorkerDAO dao;
    //controls
    private Button btnCreate;

    public TableViewResultTabContent() {
        dao = new WorkerDAOMemImpl();
        root = buildContent();
    }

    public BorderPane buildContent() {
        root = new BorderPane();
        addComponents();
        root.getStylesheets().add("org/ddg/tabs/content/tableViewResultTabContent.css");
        registerEvents();
        return root;
    }

    public void customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
    }

    private void addComponents() {
       workerTableView = new TableView<>();
       /*workerTableView.setColumnResizePolicy((param)-> true);
       Platform.runLater(()-> customResize(workerTableView));*/
       setupTableViewColumns();
       addMockDataToTableView();
       ScrollPane center = new ScrollPane(workerTableView);
       root.setCenter(center);

       btnCreate = new Button("Create New Worker");
       root.setBottom(btnCreate);
    }

    private void setupTableViewColumns() {
        TableColumn<Worker, String> fnameCol = new TableColumn<>("Fname");
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        fnameCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        TableColumn<Worker, String> lnameCol = new TableColumn<>("Lname");
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        lnameCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        TableColumn<Worker, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        TableColumn<Worker, Double> wageCol = new TableColumn<>("Wage");
        wageCol.setCellValueFactory(new PropertyValueFactory<>("wage"));
        wageCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        TableColumn<Worker, Boolean> activeCol = new TableColumn<>("Active");
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        activeCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        TableColumn<Worker, ObservableList<String>> activitiesCol = new TableColumn<>("Activities");
//        activitiesCol.setCellValueFactory(new PropertyValueFactory<>("activities"));
        activitiesCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Worker, ObservableList<String>>, ObservableValue<ObservableList<String>>>() {
            @Override
            public ObservableValue<ObservableList<String>> call(TableColumn.CellDataFeatures<Worker, ObservableList<String>> param) {
                ListProperty<String> acts = param.getValue().activitiesProperty();
                return acts;
            }
        });
        activitiesCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        activitiesCol.setCellFactory(new Callback<TableColumn<Worker, ObservableList<String>>, TableCell<Worker, ObservableList<String>>>() {
            @Override
            public TableCell<Worker, ObservableList<String>> call(TableColumn<Worker, ObservableList<String>> param) {
                return new TableCell<Worker, ObservableList<String>>() {
                    @Override
                    protected void updateItem(ObservableList<String> item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.stream().collect(Collectors.joining("\n")));
                            setTextFill(Color.GREEN);
//                            setBackground(new Background(new BackgroundFill(Color.YELLOW, null, new Insets(1, 1, 1, 1))));
                        }
                    }
                };
            }
        });

        TableColumn<Worker, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        TableColumn<Worker, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        TableColumn<Worker, String> streetCol = new TableColumn<>("Street");
        streetCol.setCellValueFactory(new PropertyValueFactory<>("street"));
        streetCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        TableColumn<Worker, String> plzCol = new TableColumn<>("PLZ");
        plzCol.setCellValueFactory(new PropertyValueFactory<>("plz"));
        plzCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);

        workerTableView.getColumns().addAll(fnameCol, lnameCol, ageCol, wageCol, activeCol,
                activitiesCol, countryCol, cityCol, streetCol, plzCol);
    }

    private void addMockDataToTableView() {
        dao.create(new Worker(1L, "foo", "bar", 10, 100.0,
                true, FXCollections.observableArrayList("Reading", "Writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234"));
        dao.create(new Worker(2L, "leo", "messi", 20, 200.0,
                false, FXCollections.observableArrayList("Speaking", "Listening"), "Spain",
                "Barcelona", "Catalonia", "23456"));
        dao.create(new Worker(3L, "cris", "tiano", 30, 300.0,
                true, FXCollections.observableArrayList("Reading", "Listening"), "Italy",
                "Turin", "Juventus", "34567"));
        workerTableView.getItems().addAll(dao.findAll());
    }

    private void refreshTable() {
        workerTableView.getItems().clear();
        workerTableView.getItems().addAll(dao.findAll());
    }

    private void registerEvents() {
        workerTableView.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.ENTER) {
                Worker w = workerTableView.getSelectionModel().getSelectedItem();
                if(w == null) return;
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorkerDetailsWindow.fxml"));
                AnchorPane root = null;
                try {
                    root = (AnchorPane)loader.load();
                    WorkerDetailsController controller = loader.getController();
                    controller.setSelectedWorker(w);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("WorkerDetails");
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(evt.isControlDown() && evt.getCode() == KeyCode.E) {
                Worker w = workerTableView.getSelectionModel().getSelectedItem();
                if(w == null) return;
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorkerUpdateWindow.fxml"));
                AnchorPane root = null;
                try {
                    root = (AnchorPane)loader.load();
                    WorkerUpdateController controller = loader.getController();
                    controller.setDAOAndWorker(dao,w);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Update Selected Worker");
                    stage.showAndWait();
                    refreshTable();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } if(evt.isControlDown() && evt.getCode() == KeyCode.D) {
                Worker w = workerTableView.getSelectionModel().getSelectedItem();
                if(w == null) return;
                dao.delete(w.getId());
                refreshTable();
            }
        });

        btnCreate.setOnAction(e-> {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorkerCreateWindow.fxml"));
            AnchorPane root = null;
            try {
                root = (AnchorPane)loader.load();
                WorkerCreateController controller = loader.getController();
                controller.setDAO(dao);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Create New Worker");
                stage.showAndWait();
                refreshTable();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
    }

}
