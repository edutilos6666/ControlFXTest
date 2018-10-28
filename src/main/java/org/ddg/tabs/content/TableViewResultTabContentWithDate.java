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
import org.ddg.controller.*;
import org.ddg.dao.WorkerWithDateDAO;
import org.ddg.dao.WorkerWithDateDAOHibernateImpl;
import org.ddg.model.WorkerWithDate;
import org.ddg.model.WorkerWithDate;
import org.ddg.utils.Constants;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TableViewResultTabContentWithDate {
    private BorderPane root;
    private TableView<WorkerWithDate> workerTableView;
    private WorkerWithDateDAO dao;
    //controls
    private Button btnCreate;
    private CheckBox cbTableEditable;
    private Button btnRefreshTable;

    public TableViewResultTabContentWithDate() {
        dao = new WorkerWithDateDAOHibernateImpl();
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
      /*  workerTableView.getItems().addListener(new ListChangeListener<WorkerWithDate>() {
            @Override
            public void onChanged(Change<? extends WorkerWithDate> c) {
                c.next();
                if(c.wasUpdated()) {
                    for(int i = c.getFrom(); i < c.getTo(); ++i) {
                        WorkerWithDate w = workerTableView.getItems().get(i);
                        dao.update(w.getId(), w);
                    }
                }
                if(c.wasAdded()) {
                    for(WorkerWithDate w: c.getAddedSubList()) {
                        dao.create(w);
                    }
                }

                if(c.wasRemoved()) {
                    for(WorkerWithDate w: c.getRemoved()) {
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
       addMockDataToTableView();
       ScrollPane center = new ScrollPane(workerTableView);
       VBox boxCenter = new VBox();
       HBox hbCenter = new HBox();
       cbTableEditable = new CheckBox("Make TableView editable");
       btnRefreshTable = new Button("Refresh Table");
       hbCenter.getChildren().addAll(cbTableEditable, btnRefreshTable);
       boxCenter.getChildren().addAll(hbCenter, center);
       root.setCenter(boxCenter);
       VBox vbControls = new VBox();
       btnCreate = new Button("Create New WorkerWithDate");
       vbControls.getChildren().addAll(
         btnCreate, new Label("Enter => WorkerDetailsWindow"),
               new Label("Ctrl+C => WorkerCreateWindow"),
               new Label("Ctrl+E => WorkerUpdateWindow"),
               new Label("Ctrl+D => Delete selected WorkerWithDate")
       );
       root.setBottom(vbControls);


       workerTableView.editableProperty().bind(cbTableEditable.selectedProperty());
    }

    private void setupTableViewColumns() {
        TableColumn<WorkerWithDate, String> fnameCol = new TableColumn<>("Fname");
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        fnameCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        fnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        fnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, String> event) {
                ((WorkerWithDate)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setFname(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<WorkerWithDate, String> lnameCol = new TableColumn<>("Lname");
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        lnameCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        lnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, String> event) {
                ((WorkerWithDate)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setLname(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<WorkerWithDate, Integer> ageCol = new TableColumn<>("Age");
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
        ageCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, Integer> event) {
//                System.out.println(event.getOldValue()+ " => "+ event.getNewValue());
                if(event.getNewValue() == null) {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setAge(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setAge(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });

        TableColumn<WorkerWithDate, Double> wageCol = new TableColumn<>("Wage");
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
        wageCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, Double> event) {
                if(event.getNewValue() == null) {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setWage(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setWage(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });

        TableColumn<WorkerWithDate, Boolean> activeCol = new TableColumn<>("Active");
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
        activeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, Boolean>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, Boolean> event) {
                if(event.getNewValue() == null) {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setActive(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setActive(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });

        TableColumn<WorkerWithDate, ObservableList<String>> activitiesCol = new TableColumn<>("Activities");
//        activitiesCol.setCellValueFactory(new PropertyValueFactory<>("activities"));
        activitiesCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WorkerWithDate, ObservableList<String>>, ObservableValue<ObservableList<String>>>() {
            @Override
            public ObservableValue<ObservableList<String>> call(TableColumn.CellDataFeatures<WorkerWithDate, ObservableList<String>> param) {
                ListProperty<String> acts = param.getValue().activitiesProperty();
                return acts;
            }
        });
        activitiesCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        activitiesCol.setCellFactory(new Callback<TableColumn<WorkerWithDate, ObservableList<String>>, TableCell<WorkerWithDate, ObservableList<String>>>() {
            @Override
            public TableCell<WorkerWithDate, ObservableList<String>> call(TableColumn<WorkerWithDate, ObservableList<String>> param) {
                return new TableCell<WorkerWithDate, ObservableList<String>>() {
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

        TableColumn<WorkerWithDate, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        countryCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        countryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        countryCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, String> event) {
                ((WorkerWithDate)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setCountry(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<WorkerWithDate, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        cityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cityCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, String> event) {
                ((WorkerWithDate)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setCity(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<WorkerWithDate, String> streetCol = new TableColumn<>("Street");
        streetCol.setCellValueFactory(new PropertyValueFactory<>("street"));
        streetCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        streetCol.setCellFactory(TextFieldTableCell.forTableColumn());
        streetCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, String> event) {
                ((WorkerWithDate)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setStreet(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<WorkerWithDate, String> plzCol = new TableColumn<>("PLZ");
        plzCol.setCellValueFactory(new PropertyValueFactory<>("plz"));
        plzCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        plzCol.setCellFactory(TextFieldTableCell.forTableColumn());
        plzCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, String> event) {
                ((WorkerWithDate)event.getTableView().getItems().get(
                        event.getTablePosition().getRow())).setPlz(event.getNewValue());
                updateDAO(event.getTableView().getItems());
            }
        });

        TableColumn<WorkerWithDate, Date> dateFromCol = new TableColumn<>("DateFrom");
        dateFromCol.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
        dateFromCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        dateFromCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            @Override
            public String toString(Date object) {
                try {
                    return Constants.DATE_FORMAT_1.format(object);
                } catch(Exception ex) {
                    return null;
                }
            }

            @Override
            public Date fromString(String string) {
                try {
                    return Constants.DATE_FORMAT_1.parse(string);
                } catch(Exception ex) {
                    return null;
                }
            }
        }));

        dateFromCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, Date> event) {
                if(event.getNewValue() == null) {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setDateFrom(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setDateFrom(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });

        TableColumn<WorkerWithDate, Date> dateToCol = new TableColumn<>("DateTo");
        dateToCol.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
        dateToCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        dateToCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            @Override
            public String toString(Date object) {
                try {
                    return Constants.DATE_FORMAT_1.format(object);
                } catch(Exception ex) {
                    return null;
                }
            }

            @Override
            public Date fromString(String string) {
                try {
                    return Constants.DATE_FORMAT_1.parse(string);
                } catch(Exception ex) {
                    return null;
                }
            }
        }));

        dateToCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, Date> event) {
                if(event.getNewValue() == null) {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setDateTo(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setDateTo(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });



        TableColumn<WorkerWithDate, java.sql.Date> sqlDateFromCol = new TableColumn<>("SqlDateFrom");
        sqlDateFromCol.setCellValueFactory(new PropertyValueFactory<>("sqlDateFrom"));
        sqlDateFromCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        sqlDateFromCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<java.sql.Date>() {
            @Override
            public String toString(java.sql.Date object) {
                try {
                    return Constants.DATE_FORMAT_1.format(object);
                } catch(Exception ex) {
                    return null;
                }
            }

            @Override
            public java.sql.Date fromString(String string) {
                try {
                    Date date =  Constants.DATE_FORMAT_1.parse(string);
                    return new java.sql.Date(date.getTime());
                } catch(Exception ex) {
                    return null;
                }
            }
        }));

        sqlDateFromCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, java.sql.Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, java.sql.Date> event) {
                if(event.getNewValue() == null) {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setSqlDateFrom(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setSqlDateFrom(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });



        TableColumn<WorkerWithDate, java.sql.Date> sqlDateToCol = new TableColumn<>("SqlDateTo");
        sqlDateToCol.setCellValueFactory(new PropertyValueFactory<>("sqlDateTo"));
        sqlDateToCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        sqlDateToCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<java.sql.Date>() {
            @Override
            public String toString(java.sql.Date object) {
                try {
                    return Constants.DATE_FORMAT_1.format(object);
                } catch(Exception ex) {
                    return null;
                }
            }

            @Override
            public java.sql.Date fromString(String string) {
                try {
                    Date date =  Constants.DATE_FORMAT_1.parse(string);
                    return new java.sql.Date(date.getTime());
                } catch(Exception ex) {
                    return null;
                }
            }
        }));

        sqlDateToCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, java.sql.Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, java.sql.Date> event) {
                if(event.getNewValue() == null) {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setSqlDateTo(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setSqlDateTo(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });



        TableColumn<WorkerWithDate, Time> heuteCol = new TableColumn<>("Heute");
        heuteCol.setCellValueFactory(new PropertyValueFactory<>("heute"));
        heuteCol.setMinWidth(Constants.MIN_TABLE_COLUMN_WIDTH);
        heuteCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Time>() {
            @Override
            public String toString(Time object) {
                try {
                    return Constants.TIME_FORMAT_1.format(object);
                } catch(Exception ex) {
                    return null;
                }
            }

            @Override
            public Time fromString(String string) {
                try {
                    Date date =  Constants.TIME_FORMAT_1.parse(string);
                    return new Time(date.getTime());
                } catch(Exception ex) {
                    return null;
                }
            }
        }));

        heuteCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<WorkerWithDate, Time>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<WorkerWithDate, Time> event) {
                if(event.getNewValue() == null) {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setHeute(event.getOldValue());
                    //weird bug in javafx
                    ageCol.setVisible(false);
                    ageCol.setVisible(true);
                } else {
                    ((WorkerWithDate)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setHeute(event.getNewValue());
                    updateDAO(event.getTableView().getItems());
                }
            }
        });

        workerTableView.getColumns().addAll(fnameCol, lnameCol, ageCol, wageCol, activeCol,
                activitiesCol, countryCol, cityCol, streetCol, plzCol,
                dateFromCol, dateToCol, sqlDateFromCol, sqlDateToCol, heuteCol);

    }

    private void addMockDataToTableView() {
        dao.create(new WorkerWithDate("foo", "bar", 10, 100.0,
                true, FXCollections.observableArrayList("reading", "writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234",
                new GregorianCalendar(2010, Calendar.FEBRUARY, 10).getTime(),
                new GregorianCalendar(2013, Calendar.DECEMBER, 7).getTime(),
                java.sql.Date.valueOf(LocalDate.of(2010, 2, 10)),
                java.sql.Date.valueOf(LocalDate.of(2013, 12, 7)),
                java.sql.Time.valueOf(LocalTime.of(10, 10, 10))
        ));
        dao.create(new WorkerWithDate("leo", "messi", 20, 200.0,
                false, Arrays.asList("speaking", "listening"), "Spain",
                "Barcelona", "Catalonia", "23456",
                new GregorianCalendar(2009, Calendar.FEBRUARY, 10).getTime(),
                new GregorianCalendar(2014, Calendar.DECEMBER, 7).getTime(),
                java.sql.Date.valueOf(LocalDate.of(2009, 2, 10)),
                java.sql.Date.valueOf(LocalDate.of(2014, 12, 7)),
                java.sql.Time.valueOf(LocalTime.of(15, 15, 15))
        ));
        dao.create(new WorkerWithDate("cris", "tiano", 30, 300.0,
                true, FXCollections.observableArrayList("reading", "listening"), "Italy",
                "Turin", "Juventus", "34567",
                new GregorianCalendar(2008, Calendar.FEBRUARY, 10).getTime(),
                new GregorianCalendar(2015, Calendar.DECEMBER, 7).getTime(),
                java.sql.Date.valueOf(LocalDate.of(2008, 2, 10)),
                java.sql.Date.valueOf(LocalDate.of(2015, 12, 7)),
                java.sql.Time.valueOf(LocalTime.of(20, 20, 20))
        ));
        workerTableView.getItems().addAll(dao.findAll());
    }

    private void refreshTable() {
//        workerTableView.refresh();
        workerTableView.getItems().clear();
        workerTableView.getItems().addAll(dao.findAll());
    }

    private void updateDAO(ObservableList<WorkerWithDate> items) {
//        dao.clear();
        items.forEach(w-> dao.update(w.getId(),w));
    }

    public void setScene(Scene scene) {
        ((Stage)scene.getWindow()).setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    ((WorkerWithDateDAOHibernateImpl)dao).closeSessionFactory();
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
                WorkerWithDate w = workerTableView.getSelectionModel().getSelectedItem();
                if(w == null) return;
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorkerWithDateDetailsWindow.fxml"));
                AnchorPane root = null;
                try {
                    root = (AnchorPane)loader.load();
                    WorkerWithDateDetailsController controller = loader.getController();
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
                WorkerWithDate w = workerTableView.getSelectionModel().getSelectedItem();
                if(w == null) return;
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorkerWithDateUpdateWindow.fxml"));
                AnchorPane root = null;
                try {
                    root = (AnchorPane)loader.load();
                    WorkerWithDateUpdateController controller = loader.getController();
                    controller.setDAOAndWorker(dao,w);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    controller.registerKeyBindings(scene);
                    stage.setTitle("Update Selected WorkerWithDate");
                    stage.showAndWait();
                    refreshTable();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if(evt.isControlDown() && evt.getCode() == KeyCode.D) {
                WorkerWithDate w = workerTableView.getSelectionModel().getSelectedItem();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorkerWithDateCreateWindow.fxml"));
        AnchorPane root = null;
        try {
            root = (AnchorPane)loader.load();
            WorkerWithDateCreateController controller = loader.getController();
            controller.setDAO(dao);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            controller.registerKeyBindings(scene);
            stage.setTitle("Create New WorkerWithDate");
            stage.showAndWait();
            refreshTable();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

}
