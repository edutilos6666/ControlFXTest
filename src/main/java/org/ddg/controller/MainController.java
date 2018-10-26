package org.ddg.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.ddg.tabs.GridPaneTabs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML
    private TextField fieldSearch;
    @FXML
    private ListView<Label> listItems;
    @FXML
    private TabPane tabPaneDisplay;

    private List<String> listContent = new ArrayList<>();

    @FXML
    public void initialize() {
//        VBox.setVgrow(tabPaneDisplay, Priority.ALWAYS);
        tabPaneDisplay.setPadding(new Insets(10));
//        tabPaneDisplay.getStylesheets().add(getClass().getResource("/main.css").getFile());
        tabPaneDisplay.getStylesheets().add("main.css");
        for(int i=0; i < 1000; ++i) {
            listContent.add(String.format("Item %d", i));
        }


        refreshListView(listContent);
        registerEvents();

    }

    private void refreshListView(List<String> listContent) {
        listItems.getItems().clear();
        Label lblGridPaneTabs = new Label("GridPane");
        lblGridPaneTabs.setOnMouseClicked(evt-> {
            tabPaneDisplay.getTabs().clear();
            tabPaneDisplay.getTabs().addAll(new GridPaneTabs().getAllTabs());
        });
        listItems.getItems().add(lblGridPaneTabs);
        listItems.getItems().addAll(listContent.stream().map(txt-> new Label(txt)).collect(Collectors.toList()));
    }

    private void registerEvents() {
       fieldSearch.setOnKeyReleased(evt-> {
           List<String> tmp = listContent.stream().filter(el-> {
              return el.contains(fieldSearch.getText());
           }).collect(Collectors.toList());
           refreshListView(tmp);
       });
    }
}
