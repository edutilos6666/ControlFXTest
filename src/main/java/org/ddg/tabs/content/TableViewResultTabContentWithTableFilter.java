package org.ddg.tabs.content;

import javafx.beans.property.ListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.table.TableFilter;
import org.ddg.controller.WorkerCreateController;
import org.ddg.controller.WorkerDetailsController;
import org.ddg.controller.WorkerUpdateController;
import org.ddg.dao.WorkerDAO;
import org.ddg.dao.WorkerDAOHibernateImpl;
import org.ddg.model.Worker;
import org.ddg.utils.Constants;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TableViewResultTabContentWithTableFilter {
    private BorderPane root;
    private TableView<Worker> workerTableView;
    private WorkerDAO dao;
    //controls
    private Button btnCreate;
    private CheckBox cbTableEditable;
    private Button btnRefreshTable;

    public TableViewResultTabContentWithTableFilter() {
        dao = new WorkerDAOHibernateImpl();
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
      /*  workerTableView.getItems().addListener(new ListChangeListener<Worker>() {
            @Override
            public void onChanged(Change<? extends Worker> c) {
                c.next();
                if(c.wasUpdated()) {
                    for(int i = c.getFrom(); i < c.getTo(); ++i) {
                        Worker w = workerTableView.getItems().get(i);
                        dao.update(w.getId(), w);
                    }
                }
                if(c.wasAdded()) {
                    for(Worker w: c.getAddedSubList()) {
                        dao.create(w);
                    }
                }

                if(c.wasRemoved()) {
                    for(Worker w: c.getRemoved()) {
                        dao.delete(w.getId());
                    }
                }

                workerTableView.refresh();
            }
        });*/
       /*workerTableView.setColumnResizePolicy((param)-> true);
       Platform.runLater(()-> customResize(workerTableView));*/
       setupTableViewColumns();
//       workerTableView.setRo
//       addMockDataToTableView();
       ScrollPane center = new ScrollPane(workerTableView);
       VBox boxCenter = new VBox();
       HBox hbCenter = new HBox();
       cbTableEditable = new CheckBox("Make TableView editable");
       btnRefreshTable = new Button("Refresh Table");
       hbCenter.getChildren().addAll(cbTableEditable, btnRefreshTable);
       boxCenter.getChildren().addAll(hbCenter, center);
       root.setCenter(boxCenter);
       VBox vbControls = new VBox();
       btnCreate = new Button("Create New Worker");
       vbControls.getChildren().addAll(
         btnCreate, new Label("Enter => WorkerDetailsWindow"),
               new Label("Ctrl+C => WorkerCreateWindow"),
               new Label("Ctrl+E => WorkerUpdateWindow"),
               new Label("Ctrl+D => Delete selected Worker")
       );
       root.setBottom(vbControls);


       workerTableView.editableProperty().bind(cbTableEditable.selectedProperty());
       TableFilter<Worker> filter = TableFilter.<Worker>forTableView(workerTableView).apply();
    }

    private void setupTableViewColumns() {
        TableColumn<Worker, String> fnameCol = new TableColumn<>("Fname");
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        fnameCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        fnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        fnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, String> event) {
                ((Worker)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setFname(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<Worker, String> lnameCol = new TableColumn<>("Lname");
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        lnameCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        lnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, String> event) {
                ((Worker)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setLname(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<Worker, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        ageCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                try {
                    return object.toString();
                } catch(NullPointerException ex) {
                    return null;
                }
            }

            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string);
                } catch(NumberFormatException | NullPointerException ex) {
                    return null;
                }
            }
        }));
        ageCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, Integer> event) {
//                System.out.println(event.getOldValue()+ " => "+ event.getNewValue());
                if(event.getNewValue() == null) {
                    ((Worker)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setAge(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((Worker)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setAge(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });

        TableColumn<Worker, Double> wageCol = new TableColumn<>("Wage");
        wageCol.setCellValueFactory(new PropertyValueFactory<>("wage"));
        wageCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        wageCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                try {
                    return object.toString();
                } catch(NullPointerException ex) {
                    return null;
                }
            }

            @Override
            public Double fromString(String string) {
                try {
                    return Double.parseDouble(string);
                } catch(NumberFormatException| NullPointerException ex) {
                    return null;
                }
            }
        }));
        wageCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, Double> event) {
                if(event.getNewValue() == null) {
                    ((Worker)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setWage(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((Worker)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setWage(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });

        TableColumn<Worker, Boolean> activeCol = new TableColumn<>("Active");
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        activeCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        activeCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean object) {
                try {
                    return object.toString();
                } catch(NullPointerException ex) {
                    return null;
                }
            }

            @Override
            public Boolean fromString(String string) {
                if(string.equalsIgnoreCase("true")) return true;
                else if(string.equalsIgnoreCase("false")) return false;
                return null;
            }
        }));
        activeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, Boolean>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, Boolean> event) {
                if(event.getNewValue() == null) {
                    ((Worker)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setActive(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((Worker)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setActive(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });

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
        countryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        countryCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, String> event) {
                ((Worker)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setCountry(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<Worker, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, String> event) {
                ((Worker)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setCity(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<Worker, String> streetCol = new TableColumn<>("Street");
        streetCol.setCellValueFactory(new PropertyValueFactory<>("street"));
        streetCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        streetCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, String> event) {
                ((Worker)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setStreet(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<Worker, String> plzCol = new TableColumn<>("PLZ");
        plzCol.setCellValueFactory(new PropertyValueFactory<>("plz"));
        plzCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        plzCol.setCellFactory(TextFieldTableCell.forTableColumn());
        plzCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Worker, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Worker, String> event) {
                ((Worker)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setPlz(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        workerTableView.getColumns().addAll(fnameCol, lnameCol, ageCol, wageCol, activeCol,
                activitiesCol, countryCol, cityCol, streetCol, plzCol);

    }

    private void addMockDataToTableView() {
        dao.create(new Worker(1L,"foo", "bar", 10, 100.0,
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

    private void updateDAO(ObservableList<Worker> items) {
//        dao.clear();
        items.forEach(w-> dao.update(w.getId(),w));
    }

    public void setScene(Scene scene) {
        ((Stage)scene.getWindow()).setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    ((WorkerDAOHibernateImpl)dao).closeSessionFactory();
                } catch(Exception ex) {
                } finally {
                    ((Stage)scene.getWindow()).close();
                }
            }
        });
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
                    controller.registerKeyBindings(scene);
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
                    controller.registerKeyBindings(scene);
                    stage.setTitle("Update Selected Worker");
                    stage.showAndWait();
                    refreshTable();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if(evt.isControlDown() && evt.getCode() == KeyCode.D) {
                Worker w = workerTableView.getSelectionModel().getSelectedItem();
                if(w == null) return;
                dao.delete(w.getId());
                refreshTable();
            }

            if(evt.isControlDown() && evt.getCode() == KeyCode.C) {
                showWorkerCreateWindow();
            }
        });

        btnCreate.setOnAction(e-> {
            showWorkerCreateWindow();
        });

        btnRefreshTable.setOnAction(e-> {
            refreshTable();
        });

    }

    private void showWorkerCreateWindow() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorkerCreateWindow.fxml"));
        AnchorPane root = null;
        try {
            root = (AnchorPane)loader.load();
            WorkerCreateController controller = loader.getController();
            controller.setDAO(dao);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            controller.registerKeyBindings(scene);
            stage.setTitle("Create New Worker");
            stage.showAndWait();
            refreshTable();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

}
