package org.ddg.tabs.content;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ddg.dao.WorkerDAO;
import org.ddg.dao.WorkerDAOMemImpl;
import org.ddg.model.Worker;
import org.ddg.utils.Constants;
import org.ddg.utils.CustomAlerts;

import java.util.List;

import static org.ddg.utils.Constants.DELIMITER;
import static org.ddg.utils.Constants.SEPARATOR;
import static org.ddg.utils.CustomAlerts.showErrorAlert;
import static org.ddg.utils.CustomAlerts.showInfoAlert;

public class TreeViewResultTabContent {
    private VBox root;

    private WorkerDAO dao;

    public TreeViewResultTabContent() {
        root = buildContent();
    }

    public VBox buildContent() {
        dao = new WorkerDAOMemImpl();
        root = new VBox();
        root.setSpacing(20);
        addComponents();
        root.getStylesheets().add("org/ddg/tabs/content/gridPaneResultTabContent.css");
        registerEvents();
        return root;
    }

    private void addComponents() {
        root.getChildren().addAll(generateTreeViewWorker(), generateTreeViewWithEvents());
    }

    private void registerEvents() {

    }


    private HBox generateTreeViewWithEvents() {
        HBox root = new HBox();
        //containerLeft
        VBox containerLeft = new VBox();
        //treeview
        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> products = new TreeItem<>("Products");
        treeView.setRoot(products);
        treeView.setPrefWidth(300);
        products.getChildren().addAll(
                generateTreeItemBrowsers(),
                generateTreeItemCars(),
                generateTreeItemCapitals()
        );
        //controls
        Button btnAddItem = new Button("Add New Item");
        TextField fieldAddItem = new TextField();
        fieldAddItem.setPromptText("Enter value of item to be added");
        HBox hbAddItem = new HBox();
        hbAddItem.getChildren().addAll(btnAddItem, fieldAddItem);
        CheckBox cbEnableEdit = new CheckBox("Make TreeView Editable");
        Button btnRemoveItem = new Button("Remove Selected Item");
        containerLeft.getChildren().addAll(
                treeView,
                new Label("I => Show TreeItem Content"),
                new Label("E => Expand Selected TreeItem"),
                new Label("C => Collapse Selected TreeItem"),
                hbAddItem, btnRemoveItem,
                cbEnableEdit
        );

        //containerRight
        VBox containerRight = new VBox();
        Label lblLogTitle = new Label("<<Logs>>");
        TextArea areaLog = new TextArea();
        areaLog.setPrefColumnCount(20);
        containerRight.getChildren().addAll(lblLogTitle, areaLog);
        //scroll areaLog to bottom
        areaLog.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                areaLog.setScrollTop(Double.MAX_VALUE);
            }
        });

        root.getChildren().addAll(containerLeft, containerRight);

        //registerEvents
        treeView.setEditable(true);
        treeView.setCellFactory(TextFieldTreeCell.forTreeView());
        treeView.setOnEditCancel(new EventHandler<TreeView.EditEvent<String>>() {
            @Override
            public void handle(TreeView.EditEvent<String> event) {
                StringBuilder sb = new StringBuilder();
                sb.append("<<EditCancel>>").append(SEPARATOR)
                        .append("oldValue = ").append(event.getOldValue()).append(SEPARATOR)
                        .append("newValue = ").append(event.getNewValue()).append(SEPARATOR);
                logMessage(areaLog, sb.toString());
            }
        });

        treeView.setOnEditStart(new EventHandler<TreeView.EditEvent<String>>() {
            @Override
            public void handle(TreeView.EditEvent<String> event) {
                StringBuilder sb = new StringBuilder();
                sb.append("<<EditStart>>").append(SEPARATOR)
                        .append("oldValue = ").append(event.getOldValue()).append(SEPARATOR)
                        .append("newValue = ").append(event.getNewValue()).append(SEPARATOR);
                logMessage(areaLog, sb.toString());
            }
        });

        treeView.setOnEditCommit(new EventHandler<TreeView.EditEvent<String>>() {
            @Override
            public void handle(TreeView.EditEvent<String> event) {
                StringBuilder sb = new StringBuilder();
                sb.append("<<EditCommit>>").append(SEPARATOR)
                        .append("oldValue = ").append(event.getOldValue()).append(SEPARATOR)
                        .append("newValue = ").append(event.getNewValue()).append(SEPARATOR);
                logMessage(areaLog, sb.toString());
            }
        });


        products.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Object> event) {
                StringBuilder sb = new StringBuilder();
                Object node = event.getSource().getValue();
                sb.append("<<TreeItem.branchExpandedEvent()>>").append(SEPARATOR)
                        .append("expanded node = ").append(node).append(SEPARATOR);
                logMessage(areaLog, sb.toString());
            }
        });

        products.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Object> event) {
                StringBuilder sb = new StringBuilder();
                Object node = event.getSource().getValue();
                sb.append("<<TreeItem.branchCollapsedEvent()>>").append(SEPARATOR)
                        .append("expanded node = ").append(node).append(SEPARATOR);
                logMessage(areaLog, sb.toString());
            }
        });

        products.addEventHandler(TreeItem.childrenModificationEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Object> event) {
                StringBuilder sb = new StringBuilder();
                Object node = event.getSource().getValue();
                sb.append("<<TreeItem.childrenModificationEvent()>>").append(SEPARATOR)
                        .append("expanded node = ").append(node).append(SEPARATOR);
                logMessage(areaLog, sb.toString());
            }
        });

        products.addEventHandler(TreeItem.expandedItemCountChangeEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Object> event) {
                StringBuilder sb = new StringBuilder();
                Object node = event.getSource().getValue();
                sb.append("<<TreeItem.expandedItemCountChangeEvent()>>").append(SEPARATOR)
                        .append("expanded node = ").append(node).append(SEPARATOR);
                logMessage(areaLog, sb.toString());
            }
        });

        products.addEventHandler(TreeItem.graphicChangedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Object>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<Object> event) {
                StringBuilder sb = new StringBuilder();
                Object node = event.getSource().getValue();
                sb.append("<<TreeItem.graphicChangedEvent()>>").append(SEPARATOR)
                        .append("expanded node = ").append(node).append(SEPARATOR);
                logMessage(areaLog, sb.toString());
            }
        });

        treeView.setOnKeyPressed(evt-> {
            if(evt.getCode() == KeyCode.I) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                if(selectedItem== null) return;
                CustomAlerts.showInfoAlert(selectedItem.getValue());
            }

            if(evt.getCode() == KeyCode.E) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                if(selectedItem == null) return;
                selectedItem.setExpanded(true);
            }

            if(evt.getCode() == KeyCode.C) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                if(selectedItem == null) return;
                selectedItem.setExpanded(false);
            }
        });


        btnAddItem.setOnAction(evt-> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if(selectedItem == null) return;
            if(selectedItem.getParent() == null) return;
            String fieldAddValue = fieldAddItem.getText();
            if(fieldAddValue.isEmpty()) return;
//            if(selectedItem.getChildren().size() == 0) return;
            for(TreeItem<String> item: selectedItem.getChildren()) {
                if(item.getValue().equalsIgnoreCase(fieldAddValue)) return;
            }
            selectedItem.getChildren().add(new TreeItem<>(fieldAddValue));
            if(!selectedItem.isExpanded()) {
                selectedItem.setExpanded(true);
            }
        });

        btnAddItem.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) btnAddItem.fire();
            }
        });

        btnRemoveItem.setOnAction(evt -> {
            TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if(selectedItem == null) return;
            TreeItem<String> parent = selectedItem.getParent();
            if(parent == null) return;
            parent.getChildren().remove(selectedItem);
            if(!parent.isExpanded()) {
                parent.setExpanded(true);
            }
        });

        btnRemoveItem.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) btnRemoveItem.fire();
            }
        });

        treeView.editableProperty().bind(cbEnableEdit.selectedProperty());

        return root;
    }


    private void logMessage(TextArea areaLog, String content) {
//        areaLog.setText(areaLog.getText()+ Constants.SEPARATOR+ content);
        areaLog.appendText(content+ SEPARATOR);
    }

    private TreeItem<String> generateTreeItemBrowsers() {
        TreeItem<String> root = new TreeItem<>("Browsers");
        root.getChildren().addAll(
          new TreeItem<String>("Chrome"),
                new TreeItem<String>("Firefox"),
                new TreeItem<String>("Opera"),
                new TreeItem<String>("Safari"),
                new TreeItem<String>("Microsoft Edge"),
                new TreeItem<>("IE 10"),
                new TreeItem<>("IE 11"),
                new TreeItem<>("IE 12")
        );
        return root;
    }

    private TreeItem<String> generateTreeItemCars() {
        TreeItem<String> root = new TreeItem<>("Cars");
        root.getChildren().addAll(
                new TreeItem<>("Mercedes Benz"),
                new TreeItem<>("BMW"),
                new TreeItem<>("Porsche"),
                new TreeItem<>("Volkswagen"),
                new TreeItem<>("Audi")
        );
        return root;
    }

    private TreeItem<String> generateTreeItemCapitals() {
        TreeItem<String> root =new TreeItem<>("Capitals");
        root.getChildren().addAll(
                new TreeItem<>("Baku"),
                new TreeItem<>("Istanbul"),
                new TreeItem<>("Berlin"),
                new TreeItem<>("Moscow"),
                new TreeItem<>("Tehran"),
                new TreeItem<>("Washington"),
                new TreeItem<>("Kiev")
        );
        return root;
    }



    private HBox generateTreeViewWorker() {
        HBox root = new HBox();
        VBox containerLeft = new VBox();
        Label lblTitleLeft = new Label("TreeView for Worker");
        addMockData();
        // treeViewLeft
        TreeView<Object> treeViewLeft = new TreeView<>();
        TreeItem<Object> rootItemLeft = new TreeItem<>("Workers");
        treeViewLeft.setRoot(rootItemLeft);
        List<Worker> all = dao.findAll();
        all.forEach(w-> {
           TreeItem<Object> itemParent = new TreeItem<>(String.format("Worker %d", w.getId()));
           TreeItem<Object> itemId = new TreeItem<>(w.getId());
           TreeItem<Object> itemFname = new TreeItem<>(w.getFname());
           TreeItem<Object> itemLname = new TreeItem<>(w.getLname());
           TreeItem<Object> itemAge = new TreeItem<>(w.getAge());
           TreeItem<Object> itemActive = new TreeItem<>(w.isActive());
           TreeItem<Object> itemActivities = new TreeItem<>("Activities");
           w.getActivities().forEach(act -> {
               itemActivities.getChildren().add(
                       new TreeItem<Object>(act)
               );
           });
           TreeItem<Object> itemCountry = new TreeItem<>(w.getCountry());
           TreeItem<Object> itemCity = new TreeItem<>(w.getCity());
           TreeItem<Object> itemStreet = new TreeItem<>(w.getStreet());
           TreeItem<Object> itemPlz = new TreeItem<>(w.getPlz());
           itemParent.getChildren().addAll(
             itemId, itemFname, itemLname, itemAge, itemActive,
                   itemActivities, itemCountry, itemCity, itemStreet, itemPlz
           );
           rootItemLeft.getChildren().add(itemParent);
        });
        containerLeft.getChildren().addAll(lblTitleLeft, treeViewLeft);

        VBox containerRight = new VBox();
        Label lblTitleRight = new Label("TreeView for Worker");
        TreeView<Worker> treeViewRight = new TreeView<>();
        treeViewRight.setPrefWidth(600);
        TreeItem<Worker> rootRight = new TreeItem<>();
        treeViewRight.setRoot(rootRight);
        all.forEach(w -> {
            rootRight.getChildren().add(new TreeItem<>(w));
        });
        containerRight.getChildren().addAll(lblTitleRight, treeViewRight);
        root.getChildren().addAll(containerLeft, containerRight);
        return root;
    }


    private void addMockData() {
        dao.create(new Worker(1L, "foo", "bar", 10, 100.0,
                true, FXCollections.observableArrayList("reading", "writing"), "Germany",
                "Bochum","Laerholzstrasse", "1234"));
        dao.create(new Worker(2L, "leo", "messi", 20, 200.0,
                false, FXCollections.observableArrayList("speaking", "listening"), "Spain",
                "Barcelona", "Catalonia", "23456"));
        dao.create(new Worker(3L, "cris", "tiano", 30, 300.0,
                true, FXCollections.observableArrayList("reading", "listening"), "Italy",
                "Turin", "Juventus", "34567"));
    }

}
