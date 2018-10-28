package org.ddg.tabs;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import org.ddg.tabs.content.TableViewResultTabContentWithTableFilter;
import org.ddg.utils.JavafxUtils;

import java.util.Arrays;
import java.util.List;

import static org.ddg.tabs.TabNamesEnum.RESULT;

public class TableViewTabsWithTableFilter implements ITabsGenerator{
    private TableViewResultTabContentWithTableFilter tableViewResultTabContent;
    private Tab tabResult, tabSourceCode, tabCss,tabJavaDoc;

    public TableViewTabsWithTableFilter() {
        tabResult = getResultTab();
        tabSourceCode = getSourceCodeTab();
        tabCss = getCSSTab();
        tabJavaDoc = getJavaDocTab();
    }

    @Override
    public Tab getResultTab() {
        Tab tab = new Tab(RESULT);
        tableViewResultTabContent = new TableViewResultTabContentWithTableFilter();
        tab.setContent(tableViewResultTabContent.buildContent());
        return tab;
    }

    public void setScene(Scene scene) {
        tableViewResultTabContent.setScene(scene);
    }

    @Override
    public Tab getSourceCodeTab() {
        String fullPath = System.getProperty("user.dir") +"/src/main/java/org/ddg/tabs/content/TableViewResultTabContent.java";
        return JavafxUtils.generateSourceCodeTab(fullPath);
    }

    @Override
    public Tab getCSSTab() {
//        String fullPath = System.getProperty("user.dir") +"/src/main/resources/org/ddg/tabs/content/gridPaneResultTabContent.css";
        return JavafxUtils.generateCssTab(null);
    }

    @Override
    public Tab getJavaDocTab() {
      return JavafxUtils.generateJavaDocTab("https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html");
    }

    @Override
    public List<Tab> getAllTabs() {
        return Arrays.asList(tabResult, tabSourceCode, tabCss, tabJavaDoc);
    }
}
