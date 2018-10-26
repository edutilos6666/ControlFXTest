package org.ddg.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.ddg.tabs.TabNamesEnum;

import static org.ddg.tabs.TabNamesEnum.JAVA_DOC;

/**
 * Created by edutilos on 26.10.18.
 */
public class JavafxUtils {

    public static Tab generateSourceCodeTab(String fullPath) {
        Tab tab = new Tab(TabNamesEnum.SOURCE_CODE);
        AnchorPane root = new AnchorPane();
        TextArea area = new TextArea(CustomFileReader.readFromAbsoultePath(fullPath));
        area.setPrefWidth(Constants.PREF_WIDTH);
        area.setPrefHeight(Constants.PREF_HEIGHT);
        root.getChildren().add(area);
        tab.setContent(root);
        return tab;
    }
    public static Tab generateCssTab(String fullPath) {
        if(fullPath == null) {
            fullPath = System.getProperty("user.dir")+ "/src/main/resources/main.css";
        }
        Tab tab = new Tab(TabNamesEnum.CSS);
        AnchorPane root = new AnchorPane();
        TextArea area = new TextArea(CustomFileReader.readFromAbsoultePath(fullPath));
        area.setPrefWidth(Constants.PREF_WIDTH);
        area.setPrefHeight(Constants.PREF_HEIGHT);
        root.getChildren().add(area);
        tab.setContent(root);
        return tab;
    }

    public static Tab generateJavaDocTab(String url) {
        Tab tab = new Tab(JAVA_DOC);
        WebView webView = new WebView();
        webView.setPrefWidth(Constants.PREF_WIDTH);
        webView.setPrefHeight(Constants.PREF_HEIGHT);
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if(newValue == Worker.State.SUCCEEDED) {
                    tab.setText(tab.getText()+ " => "+ webEngine.getTitle());
                } else if(newValue == Worker.State.RUNNING) {
                    tab.setText(tab.getText()+ " => "+ " RUNNING");
                } else if(newValue == Worker.State.CANCELLED) {
                    tab.setText(tab.getText()+ " => "+ " CANCELLED");
                } else if(newValue == Worker.State.FAILED) {
                    tab.setText(tab.getText()+ " => "+ " FAILED");
                } else if(newValue == Worker.State.READY) {
                    tab.setText(tab.getText()+ " => "+ " READY");
                } else if(newValue == Worker.State.SCHEDULED) {
                    tab.setText(tab.getText()+ " => "+ " SCHEDULED");
                }
            }
        });

        tab.setContent(webView);

        return tab;
    }
}
