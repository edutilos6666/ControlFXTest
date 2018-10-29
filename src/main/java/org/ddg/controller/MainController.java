package org.ddg.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Data;
import org.ddg.tabs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML
    private TextField fieldSearch;
    @FXML
    private ListView<CustomListItem> listItems;
    @FXML
    private TabPane tabPaneDisplay;

    private List<CustomListItem> listContent = new ArrayList<>();

    private TableViewTabs tableViewTabs;
    private TableViewTabsWithProperty tableViewTabsWithProperty;
    private TableViewTabsWithDate tableViewTabsWithDate;

    public void registerKeyBindings(Scene scene) {
        KeyCombination keyCombination = new KeyCodeCombination(
                KeyCode.F1, KeyCodeCombination.CONTROL_DOWN
        );
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(keyCombination.match(event)) {
                    fieldSearch.requestFocus();
                } else if(event.getCode() == KeyCode.ESCAPE) {
                    ((Stage)scene.getWindow()).close();
                }
            }
        });

        tableViewTabs.setScene(scene);
        tableViewTabsWithProperty.setScene(scene);
        tableViewTabsWithDate.setScene(scene);
    }

    @FXML
    public void initialize() {
//        VBox.setVgrow(tabPaneDisplay, Priority.ALWAYS);
        tabPaneDisplay.setPadding(new Insets(10));
//        tabPaneDisplay.getStylesheets().add(getClass().getResource("/main.css").getFile());
        tabPaneDisplay.getStylesheets().add("main.css");
        listItems.setCellFactory(new Callback<ListView<CustomListItem>, ListCell<CustomListItem>>() {
            @Override
            public ListCell<CustomListItem> call(ListView<CustomListItem> param) {
                return new ListCell<CustomListItem>() {
                    @Override
                    protected void updateItem(CustomListItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.getContent().getText());
                        }
                    }
                };
            }
        });
        listItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableViewTabs = new TableViewTabs();
        tableViewTabsWithProperty = new TableViewTabsWithProperty();
        tableViewTabsWithDate = new TableViewTabsWithDate();
        ObservableList<CustomListItem> items = FXCollections.observableArrayList(
                new CustomListItem(new Label("GridPane"), new GridPaneTabs()),
                new CustomListItem(new Label("VBoxHBox"), new VBoxHBoxTabs()),
                new CustomListItem(new Label("TableView"), tableViewTabs),
                new CustomListItem(new Label("TreeView"), new TreeViewTabs()),
                new CustomListItem(new Label("TableViewWithProperty"), tableViewTabsWithProperty),
                new CustomListItem(new Label("TableViewWithDate"), tableViewTabsWithDate),
                new CustomListItem(new Label("ControlsFX TextFields"), new ControlsFXTextFieldsTabs()),
                new CustomListItem(new Label("ControlsFX TableViewWithTableFilter"), new TableViewTabsWithTableFilter()),
                new CustomListItem(new Label("SimpleFormTabs"), new SimpleFormTabs())
        );
        listContent.addAll(items);
        refreshListView(listContent);
        registerEvents();

    }

    private void refreshListView(List<CustomListItem> items) {
        listItems.getItems().clear();
        listItems.getItems().addAll(items);
    }


    private void registerEvents() {
       fieldSearch.setOnKeyReleased(evt-> {
           List<CustomListItem> tmp = listContent.stream().filter(el-> {
              return el.getContent().getText().toLowerCase().contains(fieldSearch.getText().toLowerCase());
           }).collect(Collectors.toList());
           refreshListView(tmp);
       });
        listItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) return;
            tabPaneDisplay.getTabs().clear();
            tabPaneDisplay.getTabs().addAll(newValue.getTabsGenerator().getAllTabs());
        });
    }

    @Data
    private class CustomListItem {
        private Label content;
        private ITabsGenerator tabsGenerator;

        public CustomListItem(Label content, ITabsGenerator tabsGenerator) {
            this.content = content;
            this.tabsGenerator = tabsGenerator;
        }
    }
}


