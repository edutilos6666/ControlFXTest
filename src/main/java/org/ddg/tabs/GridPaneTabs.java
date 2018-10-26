package org.ddg.tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.ddg.tabs.content.GridPaneResutTabContent;
import org.ddg.utils.CustomFileReader;

import java.util.Arrays;
import java.util.List;

import static org.ddg.tabs.TabNamesEnum.*;

public class GridPaneTabs implements ITabsGenerator{
    @Override
    public Tab getResultTab() {
        Tab tab = new Tab(RESULT);
        tab.setContent(new GridPaneResutTabContent().buildContent());
        return tab;
    }

    @Override
    public Tab getSourceCodeTab() {
        Tab tab = new Tab(TabNamesEnum.SOURCE_CODE);
        AnchorPane root = new AnchorPane();
        String fullPath = System.getProperty("user.dir") +"/src/main/java/org/ddg/tabs/content/GridPaneResutTabContent.java";
        TextArea area = new TextArea(CustomFileReader.readFromAbsoultePath(fullPath));
        root.getChildren().add(area);
        tab.setContent(root);
        return tab;
    }

    @Override
    public Tab getCSSTab() {
        return new Tab(CSS);
    }

    @Override
    public Tab getJavaDocTab() {
        return new Tab(JAVA_DOC);
    }

    @Override
    public List<Tab> getAllTabs() {
        return Arrays.asList(getResultTab(), getSourceCodeTab(), getCSSTab(), getJavaDocTab());
    }
}
